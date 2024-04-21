package nvb.dev.kavoshsampleproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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