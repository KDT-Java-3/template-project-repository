package bootcamp.project.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDto {

    @NotNull(message = "카테고리 ID를 입력하세요.")
    private Long categoryId;

    @NotBlank(message = "카테고리명을 입력하세요.")
    private String name;

    private Long parentId;

}
