package nvb.dev.kavoshsampleproject.mapper;

import nvb.dev.kavoshsampleproject.dto.UserDto;
import nvb.dev.kavoshsampleproject.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.kavoshsampleproject.MotherObject.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserMapperImpl.class})
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void testToMemberDto() {
        UserDto userDto = userMapper.toUserDto(anyValidUser());
        assertEquals(anyValidUser().getId(), userDto.getId());
        assertEquals(anyValidUser().getFullName(), userDto.getFullName());
        assertEquals(anyValidUser().getUsername(), userDto.getUsername());
        assertEquals(anyValidUser().getPassword(), userDto.getPassword());
    }

    @Test
    void testToMemberDtoNullFields() {
        UserDto userDto = userMapper.toUserDto(anyInvalidUser());
        assertNull(userDto.getId());
        assertNull(userDto.getFullName());
        assertNull(userDto.getUsername());
        assertNull(userDto.getPassword());
    }

    @Test
    void testToUserDtoNull() {
        UserDto userDto = userMapper.toUserDto(null);
        assertNull(userDto);
    }

    @Test
    void testToUser() {
        User user = userMapper.toUser(anyValidUserDto());
        assertEquals(anyValidUserDto().getId(), user.getId());
        assertEquals(anyValidUserDto().getFullName(), user.getFullName());
        assertEquals(anyValidUserDto().getUsername(), user.getUsername());
        assertEquals(anyValidUserDto().getPassword(), user.getPassword());
    }

    @Test
    void testToUserNullFields() {
        User user = userMapper.toUser(anyInvalidUserDto());
        assertNull(user.getId());
        assertNull(user.getFullName());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    void testToUserNull() {
        User user = userMapper.toUser(null);
        assertNull(user);
    }

}