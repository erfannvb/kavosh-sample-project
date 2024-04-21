package nvb.dev.kavoshsampleproject.security.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.kavoshsampleproject.config.UserInfoConfig;
import nvb.dev.kavoshsampleproject.dto.request.AuthRequest;
import nvb.dev.kavoshsampleproject.dto.response.JwtAuthResponse;
import nvb.dev.kavoshsampleproject.repository.UserRepository;
import nvb.dev.kavoshsampleproject.security.AuthService;
import nvb.dev.kavoshsampleproject.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public JwtAuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        UserInfoConfig userInfo = userRepository.findByUsername(authRequest.getUsername())
                .map(UserInfoConfig::new)
                .orElseThrow(() -> new UsernameNotFoundException(authRequest.getUsername()));

        String token = jwtService.generateToken(userInfo);

        return JwtAuthResponse.builder()
                .token(token)
                .build();
    }
}
