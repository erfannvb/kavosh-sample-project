package nvb.dev.kavoshsampleproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.kavoshsampleproject.dto.UserInfoDto;
import nvb.dev.kavoshsampleproject.entity.UserInfo;
import nvb.dev.kavoshsampleproject.mapper.UserInfoMapper;
import nvb.dev.kavoshsampleproject.service.UserInfoService;
import nvb.dev.kavoshsampleproject.service.impl.UserInfoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final UserInfoMapper userInfoMapper;
    private final UserInfoServiceImpl userInfoServiceImpl;

    @PostMapping(path = "/save")
    public ResponseEntity<UserInfoDto> saveUser(@RequestBody @Valid UserInfoDto userInfoDto) {
        UserInfo userInfo = userInfoMapper.toUserInfo(userInfoDto);
        UserInfo savedUser = userInfoService.saveUser(userInfo);
        return new ResponseEntity<>(userInfoMapper.toUserInfoDto(savedUser), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<UserInfoDto>> getAllUsers() {
        List<UserInfo> allUsers = userInfoServiceImpl.getAllUsers();
        List<UserInfoDto> userInfoDtoList = allUsers.stream().map(userInfoMapper::toUserInfoDto).toList();
        return new ResponseEntity<>(userInfoDtoList, HttpStatus.OK);
    }

}
