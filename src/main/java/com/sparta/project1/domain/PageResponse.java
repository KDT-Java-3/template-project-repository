package com.sparta.project1.domain;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
        List<T> contents,
        int page,
        int size,
        int totalPages,
        Long totalCount,
        boolean hasPrev,
        boolean hasNext
) {

    public static <T, D> PageResponse<D> of(Page<T> pages, List<D> contents) {
        return new PageResponse<D>(
                contents,
                pages.getNumber() + 1,
                pages.getSize(),
                pages.getTotalPages(),
                pages.getTotalElements(),
                pages.hasPrevious(),
                pages.hasNext());
    }
}
