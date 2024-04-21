package nvb.dev.kavoshsampleproject.service;

import nvb.dev.kavoshsampleproject.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    UserInfo saveUser(UserInfo userInfo);

    List<UserInfo> getAllUsers();

}
