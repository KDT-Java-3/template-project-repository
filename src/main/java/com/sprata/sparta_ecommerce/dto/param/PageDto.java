package com.sprata.sparta_ecommerce.dto.param;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class PageDto {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    @Min(value = 1, message = "page는 1 이상이어야 합니다.")
    private int page = 1;

    @Builder.Default
    @Min(value = 1, message = "size는 1 이상이어야 합니다.")
    private int size = 5000;


    public long getOffset(){
        return (long) (Math.max(1,page) -1 ) * Math.min(size, MAX_SIZE);
    }

    public PageDto(int page, int size) {
        this.page = page;
        this.size = size;
    }
}