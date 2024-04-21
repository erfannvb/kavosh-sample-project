package nvb.dev.kavoshsampleproject.security.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.kavoshsampleproject.config.UserConfig;
import nvb.dev.kavoshsampleproject.exception.UserNotFoundException;
import nvb.dev.kavoshsampleproject.repository.UserRepository;
import nvb.dev.kavoshsampleproject.security.AuthService;
import nvb.dev.kavoshsampleproject.security.JwtService;
import nvb.dev.kavoshsampleproject.security.request.AuthRequest;
import nvb.dev.kavoshsampleproject.security.response.JwtAuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        UserConfig userConfig = userRepository.findByUsername(authRequest.getUsername())
                .map(UserConfig::new)
                .orElseThrow(UserNotFoundException::new);

        String token = jwtService.generateToken(userConfig);

        return JwtAuthResponse.builder()
                .token(token)
                .build();
    }
}
