package nvb.dev.kavoshsampleproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDto {

    private Long id;

    @NotBlank(message = "title cannot be blank.")
    private String title;

}
