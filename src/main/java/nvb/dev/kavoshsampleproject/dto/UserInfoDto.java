package nvb.dev.kavoshsampleproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto {

    private Long id;

    @NotBlank(message = "fullName cannot be blank.")
    private String fullName;

    @NotBlank(message = "username cannot be blank.")
    private String username;

    @NotBlank(message = "password cannot be blank.")
    private String password;

}
