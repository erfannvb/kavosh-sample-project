package nvb.dev.kavoshsampleproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.kavoshsampleproject.dto.UserDto;
import nvb.dev.kavoshsampleproject.entity.User;
import nvb.dev.kavoshsampleproject.mapper.UserMapper;
import nvb.dev.kavoshsampleproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(path = "/user/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(userMapper.toUserDto(savedUser), HttpStatus.CREATED);
    }

    @GetMapping(path = "/user/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        List<UserDto> userDtoList = userList.stream().map(userMapper::toUserDto).toList();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

}
