package nvb.dev.kavoshsampleproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "fullName cannot be blank.")
    private String fullName;

    @NotBlank(message = "username cannot be blank.")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "password cannot be blank.")
    private String password;

}
