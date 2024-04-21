package nvb.dev.kavoshsampleproject;

import nvb.dev.kavoshsampleproject.dto.UserDto;
import nvb.dev.kavoshsampleproject.entity.User;
import nvb.dev.kavoshsampleproject.security.response.JwtAuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

public class MotherObject {

    public static final Long ANY_ID = 123L;
    public static final String ANY_STRING = "dummy";

    public static User anyValidUser() {
        return User.builder()
                .id(ANY_ID)
                .fullName(ANY_STRING)
                .username(ANY_STRING)
                .password(ANY_STRING)
                .build();
    }

    public static User anyInvalidUser() {
        return User.builder()
                .id(null)
                .fullName(null)
                .username(null)
                .password(null)
                .build();
    }

    public static UserDto anyValidUserDto() {
        return UserDto.builder()
                .id(ANY_ID)
                .fullName(ANY_STRING)
                .username(ANY_STRING)
                .password(ANY_STRING)
                .build();
    }

    public static UserDto anyInvalidUserDto() {
        return UserDto.builder()
                .id(null)
                .fullName(null)
                .username(null)
                .password(null)
                .build();
    }

    public static UserDetails anyValidUserDetails() {
        return org.springframework.security.core.userdetails.User.builder()
                .username(ANY_STRING)
                .password(ANY_STRING)
                .authorities("ROLE_USER")
                .build();
    }

    public static JwtAuthResponse anyValidJwtAuthResponse() {
        return JwtAuthResponse.builder()
                .token(ANY_STRING)
                .build();
    }

}
