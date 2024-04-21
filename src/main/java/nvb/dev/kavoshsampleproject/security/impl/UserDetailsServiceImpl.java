package nvb.dev.kavoshsampleproject.security.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.kavoshsampleproject.config.UserConfig;
import nvb.dev.kavoshsampleproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserConfig::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
