package nvb.dev.kavoshsampleproject.security;

import nvb.dev.kavoshsampleproject.dto.request.AuthRequest;
import nvb.dev.kavoshsampleproject.dto.response.JwtAuthResponse;

public interface AuthService {

    JwtAuthResponse authenticate(AuthRequest authRequest);

}
