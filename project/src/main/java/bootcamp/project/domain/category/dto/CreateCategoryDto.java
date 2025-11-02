package bootcamp.project.domain.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDto {
    @NotNull(message = "카테고리명을 입력하세요.")
    private String name;

    private Long parent_id;
}
