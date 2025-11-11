package com.example.demo.lecture.refactorspringsection3answer;

import com.example.demo.lecture.refactorspringsection3.RefactorSpringSection3Product;
import com.example.demo.lecture.refactorspringsection3.RefactorSpringSection3ProductRequest;
import com.example.demo.lecture.refactorspringsection3.RefactorSpringSection3ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RefactorSpringSection3ProductMapperAfterAnswer {

    RefactorSpringSection3Product toEntity(RefactorSpringSection3ProductRequest request);

    void updateEntityFromRequest(RefactorSpringSection3ProductRequest request,
                                 @MappingTarget RefactorSpringSection3Product product);

    RefactorSpringSection3ProductResponse toResponse(RefactorSpringSection3Product product);
}
