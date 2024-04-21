package nvb.dev.kavoshsampleproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user_infos")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "fullName cannot be blank.")
    private String fullName;

    @NotBlank(message = "username cannot be blank.")
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = "email cannot be blank.")
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "password cannot be blank.")
    private String password;

}
