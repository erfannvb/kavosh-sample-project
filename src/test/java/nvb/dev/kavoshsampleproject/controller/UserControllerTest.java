package nvb.dev.kavoshsampleproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.kavoshsampleproject.dto.UserDto;
import nvb.dev.kavoshsampleproject.entity.User;
import nvb.dev.kavoshsampleproject.mapper.UserMapper;
import nvb.dev.kavoshsampleproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static nvb.dev.kavoshsampleproject.MotherObject.anyValidUser;
import static nvb.dev.kavoshsampleproject.MotherObject.anyValidUserDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    UserMapper userMapper;

    @MockBean
    UserService userService;

    @Test
    void testThatSaveUserSuccessfullyReturnsHttpStatusCode201Created() throws Exception {
        when(userService.saveUser(any(nvb.dev.kavoshsampleproject.entity.User.class))).thenReturn(anyValidUser());
        when(userMapper.toUser(anyValidUserDto())).thenReturn(anyValidUser());
        when(userMapper.toUserDto(anyValidUser())).thenReturn(anyValidUserDto());

        String userJson = objectMapper.writeValueAsString(anyValidUserDto());

        mockMvc.perform(post("/api/v1/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenFullNameIsBlank() throws Exception {
        User user = anyValidUser();
        user.setFullName("");

        UserDto userDto = anyValidUserDto();
        userDto.setFullName("");

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        when(userMapper.toUser(userDto)).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/v1/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenFullNameIsNull() throws Exception {
        User user = anyValidUser();
        user.setFullName(null);

        UserDto userDto = anyValidUserDto();
        userDto.setFullName(null);

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        when(userMapper.toUser(userDto)).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/v1/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenUsernameIsBlank() throws Exception {
        User user = anyValidUser();
        user.setUsername("");

        UserDto userDto = anyValidUserDto();
        userDto.setUsername("");

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        when(userMapper.toUser(userDto)).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/v1/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenUsernameIsNull() throws Exception {
        User user = anyValidUser();
        user.setUsername(null);

        UserDto userDto = anyValidUserDto();
        userDto.setUsername(null);

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        when(userMapper.toUser(userDto)).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/v1/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenPasswordIsBlank() throws Exception {
        User user = anyValidUser();
        user.setPassword("");

        UserDto userDto = anyValidUserDto();
        userDto.setPassword("");

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        when(userMapper.toUser(userDto)).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/v1/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenPasswordIsNull() throws Exception {
        User user = anyValidUser();
        user.setPassword(null);

        UserDto userDto = anyValidUserDto();
        userDto.setPassword(null);

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        when(userMapper.toUser(userDto)).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/v1/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatGetAllUsersSuccessfullyReturnsHttpStatusCode200Ok() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(anyValidUser()));
        when(userMapper.toUser(anyValidUserDto())).thenReturn(anyValidUser());
        when(userMapper.toUserDto(anyValidUser())).thenReturn(anyValidUserDto());

        mockMvc.perform(get("/api/v1/user/all")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

}