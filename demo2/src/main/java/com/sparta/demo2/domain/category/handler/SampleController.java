package com.sparta.demo2.domain.category.handler;

import com.sparta.demo2.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 샘플 REST 컨트롤러
 */
@RestController
@RequestMapping("/api/sample")
@RequiredArgsConstructor
public class SampleController {

    private final ProductService productService;

}
