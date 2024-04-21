package nvb.dev.kavoshsampleproject.controller;

import lombok.RequiredArgsConstructor;
import nvb.dev.kavoshsampleproject.dto.request.AuthRequest;
import nvb.dev.kavoshsampleproject.dto.response.JwtAuthResponse;
import nvb.dev.kavoshsampleproject.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/auth")
    public ResponseEntity<JwtAuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.authenticate(authRequest), HttpStatus.OK);
    }

}
