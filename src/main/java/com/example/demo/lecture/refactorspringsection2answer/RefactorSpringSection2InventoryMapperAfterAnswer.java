package com.example.demo.lecture.refactorspringsection2answer;

import com.example.demo.lecture.refactorspringsection2.RefactorSpringSection2InventoryRecord;
import com.example.demo.lecture.refactorspringsection2.RefactorSpringSection2InventoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefactorSpringSection2InventoryMapperAfterAnswer {

    RefactorSpringSection2InventoryResponse toResponse(RefactorSpringSection2InventoryRecord record);
}
