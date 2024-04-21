package nvb.dev.kavoshsampleproject;

import nvb.dev.kavoshsampleproject.dto.UserDto;
import nvb.dev.kavoshsampleproject.entity.User;

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

}
