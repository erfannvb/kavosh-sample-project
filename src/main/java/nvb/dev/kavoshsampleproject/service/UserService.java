package nvb.dev.kavoshsampleproject.service;

import nvb.dev.kavoshsampleproject.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

}
