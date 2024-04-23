package nvb.dev.kavoshsampleproject.service;

import nvb.dev.kavoshsampleproject.entity.User;
import nvb.dev.kavoshsampleproject.exception.UserNotFoundException;
import nvb.dev.kavoshsampleproject.exception.UsernameExistsException;
import nvb.dev.kavoshsampleproject.repository.UserRepository;
import nvb.dev.kavoshsampleproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static nvb.dev.kavoshsampleproject.MotherObject.anyValidUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testThatSaveUserCanCreateNewUser() {
        when(userRepository.save(any(User.class))).thenReturn(anyValidUser());
        when(passwordEncoder.encode(anyString())).thenReturn(anyValidUser().getPassword());

        User savedUser = userService.saveUser(anyValidUser());

        assertEquals("dummy", savedUser.getFullName());
        assertEquals("dummy", savedUser.getUsername());
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }

    @Test
    void testThatSaveUserCannotCreateNewUserWhenUsernameExists() {
        when(userRepository.save(any(User.class))).thenReturn(anyValidUser());
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(anyValidUser()));
        when(passwordEncoder.encode(anyString())).thenReturn(anyValidUser().getPassword());

        assertThrows(UsernameExistsException.class, () -> userService.saveUser(anyValidUser()));

        verify(userRepository, atLeastOnce()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testThatGetAllUsersCanReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(anyValidUser(), anyValidUser()));

        List<User> userList = userService.getAllUsers();

        assertEquals("dummy", userList.getFirst().getFullName());
        assertEquals("dummy", userList.getLast().getFullName());
        verify(userRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatGetAllUsersCannotReturnListOfUsersWhenTheListIsEmpty() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(UserNotFoundException.class, () -> userService.getAllUsers());
        verify(userRepository, atLeastOnce()).findAll();
    }

}