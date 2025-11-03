package com.sparta.proejct1101.domain.product.service;

import com.sparta.proejct1101.domain.category.entity.Category;
import com.sparta.proejct1101.domain.category.repository.CategoryRespository;
import com.sparta.proejct1101.domain.product.dto.request.ProductReq;
import com.sparta.proejct1101.domain.product.dto.request.ProductSearchReq;
import com.sparta.proejct1101.domain.product.dto.response.ProductRes;
import com.sparta.proejct1101.domain.product.entity.Product;
import com.sparta.proejct1101.domain.product.repository.ProductRespository;
import com.sparta.proejct1101.domain.product.repository.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRespository productRespository;
    private final CategoryRespository categoryRespository;


    @Override
    public ProductRes saveProduct(ProductReq req) {
        Category category = categoryRespository.findById(req.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("not exist Category"));

        Product product = Product.builder()
                .prodName(req.prodName())
                .price(req.price())
                .stock(req.stock())
                .description(req.description())
                .category(category)
                .build();

        productRespository.save(product);
        return ProductRes.from(product);
    }

    @Override
    @Transactional
    public ProductRes updateProduct(Long id, ProductReq req) {
        Product product = productRespository.findById(id).orElseThrow();
        Category category = null;
        if(req.categoryId()!=null){
            category = categoryRespository.findById(req.categoryId()).orElse(null);
        }
        product.update(req.prodName(), req.price(), req.stock(), req.description(), category);

        return ProductRes.from(product);
    }

    @Override
    public ProductRes getProduct(Long id) {
        return ProductRes.from(productRespository.findById(id).orElseThrow());
    }

    @Override
    public List<ProductRes> getProducts() {
        List<Product> products = productRespository.findAll();
        return products.stream().map(ProductRes::from).toList();
    }

    @Override
    public List<ProductRes> searchProducts(ProductSearchReq searchReq) {
        Specification<Product> spec = ProductSpecification.searchWithFilters(searchReq);
        List<Product> products = productRespository.findAll(spec);
        return products.stream().map(ProductRes::from).toList();
    }
}
