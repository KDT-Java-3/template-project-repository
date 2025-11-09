package com.sparta.demo1.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.demo1.domain.category.entity.CategoryEntity;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import com.sparta.demo1.domain.product.entity.QProductEntity;
import com.sparta.demo1.domain.product.enums.ProductOrderBy;
import com.sparta.demo1.domain.product.enums.ProductStockFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

import static com.sparta.demo1.domain.product.enums.ProductStockFilter.*;

@RequiredArgsConstructor
@Repository
public class ProductQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProductEntity qProductEntity = QProductEntity.productEntity;

    public Long registerProduct(String name, String description, BigDecimal price, Integer stock, CategoryEntity category){
        jpaQueryFactory
                .insert(qProductEntity)
                    .set(qProductEntity.name, name)
                    .set(qProductEntity.description, description)
                    .set(qProductEntity.price, price)
                    .set(qProductEntity.stock, stock)
                    .set(qProductEntity.category, category)
                .execute();

        return jpaQueryFactory
                .select(qProductEntity.id)
                .from(qProductEntity)
                .where(qProductEntity.name.eq(name)
                        .and(qProductEntity.description.eq(description))
                        .and(qProductEntity.price.eq(price))
                        .and(qProductEntity.stock.eq(stock))
                        .and(qProductEntity.category.eq(category))
                )
                .fetchOne();
    }

    public Page<ProductEntity> findProductOfFilter(
            List<Long> filterCategoryIdList,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String nameKeyWord,
            ProductStockFilter stockFilter,
            Pageable pageable,
            List<ProductOrderBy> orderByList
    ){

        List<OrderSpecifier<?>> orderSpecifier = getOrderSpecifiers(orderByList);

        List<ProductEntity> productEntityList = jpaQueryFactory
                .selectFrom(qProductEntity)
                .where(dynamicProductBuilder(filterCategoryIdList, minPrice, maxPrice, nameKeyWord, stockFilter))
                .orderBy(orderSpecifier.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qProductEntity)
                .where(
                        dynamicProductBuilder(filterCategoryIdList, minPrice, maxPrice, nameKeyWord, stockFilter)
                )
                .stream().count();
        return new PageImpl<>(productEntityList, pageable, total);
    }

    private BooleanBuilder dynamicProductBuilder(
            List<Long> filterCategoryIdList,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String nameKeyWord,
            ProductStockFilter stockFilter
    ) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (!filterCategoryIdList.isEmpty()) {
            booleanBuilder.and(qProductEntity.category.id.in(filterCategoryIdList));
        }

        if(minPrice != null){
            booleanBuilder.and(qProductEntity.price.goe(minPrice));
        }

        if(maxPrice != null){
            booleanBuilder.and(qProductEntity.price.loe(maxPrice));
        }

        if(!nameKeyWord.isBlank()){
            booleanBuilder.and(qProductEntity.name.contains(nameKeyWord));
        }

        if (stockFilter != null) {
            switch (stockFilter) {
                case STOCK_LOW:
                    booleanBuilder.and(qProductEntity.stock.loe(10).and(qProductEntity.stock.gt(0)));
                    break;
                case STOCK_EMPTY:
                    booleanBuilder.and(qProductEntity.stock.eq(0));
                    break;
                case STOCK_AVAILABLE:
                    booleanBuilder.and(qProductEntity.stock.gt(0));
                    break;
                default:
                    break; // ALL이면 아무 조건 X
            }
        }

        return booleanBuilder;
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(List<ProductOrderBy> orderByList) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (orderByList == null || orderByList.isEmpty()) {
            orders.add(qProductEntity.createdAt.desc()); // 기본값: 최신순
            return orders;
        }

        for (ProductOrderBy orderBy : orderByList) {
            switch (orderBy) {
                case PRICE_ASC:
                    orders.add(qProductEntity.price.asc());
                    break;
                case PRICE_DESC:
                    orders.add(qProductEntity.price.desc());
                    break;
                case CREATED_AT_ASC:
                    orders.add(qProductEntity.createdAt.asc());
                    break;
                case CREATED_AT_DESC:
                    orders.add(qProductEntity.createdAt.desc());
                    break;
                case NAME_ASC:
                    orders.add(qProductEntity.name.asc());
                    break;
                case NAME_DESC:
                    orders.add(qProductEntity.name.desc());
                    break;
//                case STOCK_ASC:
//                    orders.add(qProductEntity.stock.asc());
//                    break;
                case STOCK_DESC:
                    orders.add(qProductEntity.stock.desc());
                    break;
            }
        }

        return orders;
    }


//    public CommentEntity findComment(Long id) throws CustomException {
//        return Optional.ofNullable(
//                        jpaQueryFactory
//                                .selectFrom(qCommentEntity)
//                                .leftJoin(qCommentEntity.user, qUserEntity).fetchJoin()
//                                .where(qCommentEntity.id.eq(id))
//                                .fetchOne()
//
//                )
//                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, String.format("Comment [%s] is null", id)));
//    }
//
//    public List<CommentEntity> findComments(List<Long> ids) throws CustomException {
//        return
//                jpaQueryFactory
//                        .selectFrom(qCommentEntity)
//                        .leftJoin(qCommentEntity.user, qUserEntity).fetchJoin()
//                        .where(qCommentEntity.id.in(ids))
//                        .fetch();
//    }
//
//    public List<CommentModel.CommentDto> findCommentsByContent(Long contentId, String userId) {
//        QCommentEntity qCommentEntity = QCommentEntity.commentEntity;
//        QCommentEntity qChildCommentEntity = new QCommentEntity("childComment");
//
//        QUserEntity qUserEntity2 = new QUserEntity("UserEntity2");
//
//        QUserEntity qLikeUserEntity = new QUserEntity("qLikeUserEntity");
//
//        List<CommentEntity> parentCommentEntityList = jpaQueryFactory
//                .selectFrom(qCommentEntity)
//                .leftJoin(qCommentEntity.user, qUserEntity).fetchJoin()
//                .leftJoin(qCommentEntity.commentLikes, qCommentLikeEntity).fetchJoin()
//                .leftJoin(qCommentLikeEntity.user, qLikeUserEntity).fetchJoin()
//                .where(qCommentEntity.content.id.eq(contentId)
//                        .and(qCommentEntity.parent.isNull()) // 부모 댓글만 조회 // 삭제되지 않은 댓글만 조회
//                )
//                .orderBy(qCommentEntity.createdAt.asc())
//                .fetch();
//
//        List<CommentModel.CommentDto> parentComments = new ArrayList<>();
//
//        List<String> parentUserIds = parentCommentEntityList.stream()
//                .map(commentEntity -> commentEntity.getUser().getId())
//                .toList();
//
//        Map<String, String> parentUserFileUrlList = fileDao.findFileUrlByUserIds(parentUserIds);
//
//        // 부모 댓글 ID 리스트 생성
//        List<Long> parentCommentIds = new ArrayList<>();
//        for (CommentEntity commentEntity : parentCommentEntityList) {
//            parentCommentIds.add(commentEntity.getId());
//            parentComments.add(convertToDto(commentEntity, userId, parentUserFileUrlList));
//        }
//
//        List<CommentEntity> childrenCommentEntityList = jpaQueryFactory
//                .selectFrom(qChildCommentEntity)
//                .leftJoin(qChildCommentEntity.parent, qCommentEntity).fetchJoin()
//                .leftJoin(qChildCommentEntity.user, qUserEntity).fetchJoin()
//                .leftJoin(qChildCommentEntity.sendUser, qUserEntity2).fetchJoin()
//                .leftJoin(qChildCommentEntity.commentLikes, qCommentLikeEntity).fetchJoin()
//                .leftJoin(qCommentLikeEntity.user, qLikeUserEntity).fetchJoin()
//                .where(qChildCommentEntity.content.id.eq(contentId)
//                        .and(qChildCommentEntity.parent.id.in(parentCommentIds)) // 부모 댓글만 조회 // 삭제되지 않은 댓글만 조회
//                )
//                .orderBy(qChildCommentEntity.createdAt.asc())
//                .fetch();
//
//        // 4. 자식 댓글을 부모 댓글 ID로 그룹화
//        Map<Long, List<CommentModel.CommentDto>> childrenCommentsMap = new HashMap<>();
//
//        List<String> childrenUserIds = childrenCommentEntityList.stream()
//                .map(commentEntity -> commentEntity.getUser().getId())
//                .toList();
//
//        Map<String, String> childrenUserFileUrlList = fileDao.findFileUrlByUserIds(childrenUserIds);
//
//        for (CommentEntity commentEntity : childrenCommentEntityList) {
//            Long parentId = commentEntity.getParent().getId();
//            // parentId에 해당하는 리스트를 가져오거나, 없으면 새로운 리스트 생성
//            List<CommentModel.CommentDto> commentList = childrenCommentsMap.computeIfAbsent(parentId, k -> new ArrayList<>());
//
//            // 새로운 CommentDto 객체를 리스트에 추가
//            CommentModel.CommentDto commentDto = convertToDto(commentEntity, userId, childrenUserFileUrlList);
//            commentDto.setParentCommentId(commentEntity.getParent().getId());
//            if(commentEntity.getSendUser() != null){
//                commentDto.setSendUserNickName(commentEntity.getSendUser().getNickName());
//            }
//            commentList.add(commentDto);
//        }
//
//        // 5. 부모 댓글에 자식 댓글을 매핑
//        parentComments.forEach(parentComment -> {
//            List<CommentModel.CommentDto> children = childrenCommentsMap.getOrDefault(parentComment.getId(), new ArrayList<>());
//            parentComment.getChildren().addAll(children);
//        });
//
//        return parentComments;
//    }
//
//    private CommentModel.CommentDto convertToDto(CommentEntity commentEntity, String userId, Map<String, String> parentUserFileUrlList) {
//        List<String> likeUserIds = new ArrayList<>();
//        for (CommentLikeEntity commentLike : commentEntity.getCommentLikes()) {
//            likeUserIds.add(commentLike.getUser().getId());
//        }
//
//        return CommentModel.CommentDto.builder()
//                .id(commentEntity.getId())
//                .comment(commentEntity.getComment())
//                .delYn(commentEntity.getDelYn())
//                .userNickName(commentEntity.getUser().getNickName())
//                .commentAuthorImgUrl(parentUserFileUrlList.getOrDefault(commentEntity.getUser().getId(), null))
//                .createdAt(commentEntity.getCreatedAt())
//                .updatedAt(commentEntity.getUpdatedAt())
//                .like((long) commentEntity.getCommentLikes().size())
//                .likeUserIds(likeUserIds)
//                .isContentAuthor(commentEntity.getIsContentAuthor())
//                .isWriteUser(userId != null ? commentEntity.getUser().getId().equals(userId) : false)
//                .children(new ArrayList<>())
//                .build();
//    }
//
//    public CommentEntity findSimpleComment(Long id) throws CustomException {
//        return Optional.ofNullable(
//                        jpaQueryFactory
//                                .selectFrom(qCommentEntity)
//                                .where(qCommentEntity.id.eq(id)
//                                        .and(qCommentEntity.delYn.eq(BooleanFlag.N)))
//                                .fetchOne()
//
//                )
//                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, String.format("해당 코멘트([%s])는 삭제되었거나, 없는 코멘트 입니다.", id)));
//    }
//
////    public long updateComment(Long commentId, String comment) {
////        return jpaQueryFactory
////                .update(qCommentEntity)
////                .set(qCommentEntity.comment, comment)
////                .where(qCommentEntity.id.eq(commentId))
////                .execute();
////    }
//
//    public Page<CommentModel.UserCommentDto> findUserCommentsPagingByUserId(String userId, Pageable pageable) throws CustomException {
//        try{
//            List<CommentModel.UserCommentDto> commentListDtoList = jpaQueryFactory
//                    .select(
//                            Projections.constructor(CommentModel.UserCommentDto.class,
//                                    qCommentEntity.id,
//                                    qCommentEntity.content.id,
//                                    qCommentEntity.content.title,
//                                    qCommentEntity.comment,
//                                    qCommentEntity.createdAt,
//                                    qCommentEntity.updatedAt,
//                                    // 서브쿼리를 사용하여 해당 content의 좋아요 수를 계산
//                                    JPAExpressions
//                                            .select(qCommentLikeEntity.count())
//                                            .from(qCommentLikeEntity)
//                                            .where(qCommentLikeEntity.comment.id.eq(qCommentEntity.id))
//                            )
//
//                    )
//                    .from(qCommentEntity)
//                    .where(
//                            qCommentEntity.delYn.eq(BooleanFlag.N)
//                                    .and(qCommentEntity.user.id.eq(userId))
//                    )
//                    .offset(pageable.getOffset())
//                    .limit(pageable.getPageSize())
//                    .orderBy(qCommentEntity.createdAt.desc())
//                    .fetch();
//
//            long total = jpaQueryFactory
//                    .selectFrom(qCommentEntity)
//                    .where(
//                            qCommentEntity.delYn.eq(BooleanFlag.N)
//                                    .and(qCommentEntity.user.id.eq(userId))
//                    )
//                    .stream().count();
//            return new PageImpl<>(commentListDtoList, pageable, total);
//        } catch(Exception e){
//            log.error(e.getMessage());
//            throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR, e.getMessage());
//        }
//    }
}
