package nvb.dev.kavoshsampleproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.kavoshsampleproject.entity.UserInfo;
import nvb.dev.kavoshsampleproject.exception.UserNotFoundException;
import nvb.dev.kavoshsampleproject.exception.UsernameExistsException;
import nvb.dev.kavoshsampleproject.repository.UserInfoRepository;
import nvb.dev.kavoshsampleproject.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserInfo saveUser(UserInfo userInfo) {
        if (usernameExists(userInfo.getUsername())) throw new UsernameExistsException(userInfo.getUsername());
        return userInfoRepository.save(userInfo);
    }

    @Override
    public List<UserInfo> getAllUsers() {
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        if (userInfoList.isEmpty()) throw new UserNotFoundException();
        return userInfoList;
    }

    private boolean usernameExists(String username) {
        return userInfoRepository.findByUsername(username).isPresent();
    }
}
