package nvb.dev.kavoshsampleproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.kavoshsampleproject.entity.User;
import nvb.dev.kavoshsampleproject.exception.UserNotFoundException;
import nvb.dev.kavoshsampleproject.exception.UsernameExistsException;
import nvb.dev.kavoshsampleproject.repository.UserRepository;
import nvb.dev.kavoshsampleproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        if (usernameExists(user.getUsername())) throw new UsernameExistsException(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) throw new UserNotFoundException();
        return userList;
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
