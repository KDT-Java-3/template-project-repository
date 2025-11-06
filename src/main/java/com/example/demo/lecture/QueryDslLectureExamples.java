package com.example.demo.lecture;

import com.example.demo.controller.dto.ProductResponseDto;
import com.example.demo.controller.dto.ProductSummaryResponseDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.Refund;
import com.example.demo.entity.User;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.CategoryQueryRepository;
import com.example.demo.repository.ProductQueryRepository;
import com.example.demo.repository.PurchaseQueryRepository;
import com.example.demo.repository.RefundQueryRepository;
import com.example.demo.repository.UserQueryRepository;
import com.example.demo.repository.projection.CategoryStatsDto;
import com.example.demo.repository.projection.ProductSalesSummaryDto;
import com.example.demo.repository.projection.PurchaseDailyReportDto;
import com.example.demo.repository.projection.PurchaseDetailDto;
import com.example.demo.repository.projection.PurchaseStatusCountDto;
import com.example.demo.repository.projection.RefundDetailDto;
import com.example.demo.repository.projection.RefundStatusCountDto;
import com.example.demo.repository.projection.UserPurchaseSummaryDto;
import com.example.demo.repository.projection.UserRecentActivityDto;
import com.example.demo.service.dto.ProductSearchCondition;
import com.example.demo.service.dto.PurchaseSearchCondition;
import com.example.demo.service.dto.RefundSearchCondition;
import com.example.demo.service.dto.UserSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * QueryDSL 예시만을 모아둔 강의 지원 클래스.
 * <p>
 * 각 메서드는 Repository 별로 다양한 패턴을 보여주며,
 * Service 레이어에서 어떻게 활용할 수 있는지 참고용 예제를 제공합니다.
 */
@Component
@RequiredArgsConstructor
public class QueryDslLectureExamples {

    private final ProductQueryRepository productQueryRepository;
    private final CategoryQueryRepository categoryQueryRepository;
    private final PurchaseQueryRepository purchaseQueryRepository;
    private final RefundQueryRepository refundQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final ProductMapper productMapper;

    /**
     * [상품] 기본, 페이징, DTO Projection 예제들을 한눈에 확인.
     */
    public ProductLectureResult productExamples(ProductSearchCondition condition) {
        ProductSearchCondition safeCondition = condition != null ? condition : ProductSearchCondition.builder().build();

        List<Product> basicSearch = productQueryRepository.findByCondition(safeCondition);
        Page<Product> pagedSearch = productQueryRepository.findPageByCondition(safeCondition, PageRequest.of(0, 10));
        List<ProductSummaryResponseDto> summaries = productMapper.toSummaryResponseList(
                productQueryRepository.findSummariesByCategoryId(safeCondition.getCategoryId())
        );
        List<ProductSalesSummaryDto> salesSummaries = productQueryRepository.findProductSalesSummary();

        return new ProductLectureResult(
                productMapper.toResponseList(basicSearch),
                pagedSearch.map(productMapper::toResponse),
                summaries,
                salesSummaries
        );
    }

    /**
     * [카테고리] 계층형 구조와 통계 조회 예제.
     */
    public CategoryLectureResult categoryExamples() {
        List<Category> roots = categoryQueryRepository.findRootCategories();
        Category withChildren = roots.isEmpty() ? null : categoryQueryRepository.findCategoryWithChildren(roots.get(0).getId());
        List<CategoryStatsDto> stats = categoryQueryRepository.fetchCategoryStats();
        return new CategoryLectureResult(roots, withChildren, stats);
    }

    /**
     * [주문] 동적 검색, 페이징, 상태별 카운트, 일자별 통계 예제.
     */
    public PurchaseLectureResult purchaseExamples(PurchaseSearchCondition condition) {
        PurchaseSearchCondition safeCondition = condition != null ? condition : PurchaseSearchCondition.builder().build();
        List<Purchase> fetchJoinResult = purchaseQueryRepository.findAllWithUserAndProduct();
        List<PurchaseDetailDto> detailDtos = purchaseQueryRepository.searchPurchaseDetails(safeCondition);
        Page<PurchaseDetailDto> pagedDetails = purchaseQueryRepository.searchPurchaseDetails(safeCondition, PageRequest.of(0, 10));
        List<PurchaseStatusCountDto> statusCounts = purchaseQueryRepository.countByStatus(safeCondition);
        List<PurchaseDailyReportDto> dailyReports = purchaseQueryRepository.summarizeDaily(
                LocalDate.now().minusDays(7),
                LocalDate.now()
        );
        return new PurchaseLectureResult(fetchJoinResult, detailDtos, pagedDetails, statusCounts, dailyReports);
    }

    /**
     * [환불] 연관 엔티티 fetch join + 상세 조회 예제.
     */
    public RefundLectureResult refundExamples(RefundSearchCondition condition) {
        RefundSearchCondition safeCondition = condition != null ? condition : RefundSearchCondition.builder().build();
        List<Refund> refunds = refundQueryRepository.findAllWithRelations();
        List<RefundDetailDto> detailDtos = refundQueryRepository.searchRefundDetails(safeCondition);
        List<RefundStatusCountDto> statusCounts = refundQueryRepository.countByStatus(safeCondition);
        return new RefundLectureResult(refunds, detailDtos, statusCounts);
    }

    /**
     * [사용자] 검색, 집계, 최근 활동 조회 예제.
     */
    public UserLectureResult userExamples(UserSearchCondition condition) {
        UserSearchCondition safeCondition = condition != null ? condition : UserSearchCondition.builder().build();
        List<User> users = userQueryRepository.findByCondition(safeCondition);
        Page<User> userPage = userQueryRepository.findByCondition(safeCondition, PageRequest.of(0, 10));
        List<UserPurchaseSummaryDto> summaries = userQueryRepository.summarizePurchases();
        List<UserRecentActivityDto> recent = userQueryRepository.findActiveUsers(LocalDateTime.now().minusDays(30), 5);
        return new UserLectureResult(users, userPage, summaries, recent);
    }

    // =============================
    // 결과 묶음을 위한 record 정의
    // =============================

    public record ProductLectureResult(
            List<ProductResponseDto> basicSearch,
            Page<ProductResponseDto> pagedSearch,
            List<ProductSummaryResponseDto> categorySummaries,
            List<ProductSalesSummaryDto> salesSummaries
    ) {
    }

    public record CategoryLectureResult(
            List<Category> rootCategories,
            Category sampleWithChildren,
            List<CategoryStatsDto> stats
    ) {
    }

    public record PurchaseLectureResult(
            List<Purchase> fetchJoinResult,
            List<PurchaseDetailDto> detailDtos,
            Page<PurchaseDetailDto> pagedDetails,
            List<PurchaseStatusCountDto> statusCounts,
            List<PurchaseDailyReportDto> dailyReports
    ) {
    }

    public record RefundLectureResult(
            List<Refund> refunds,
            List<RefundDetailDto> detailDtos,
            List<RefundStatusCountDto> statusCounts
    ) {
    }

    public record UserLectureResult(
            List<User> users,
            Page<User> pagedUsers,
            List<UserPurchaseSummaryDto> purchaseSummaries,
            List<UserRecentActivityDto> recentActivity
    ) {
    }
}
