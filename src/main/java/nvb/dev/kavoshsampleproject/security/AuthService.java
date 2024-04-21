package nvb.dev.kavoshsampleproject.security;

import nvb.dev.kavoshsampleproject.security.request.AuthRequest;
import nvb.dev.kavoshsampleproject.security.response.JwtAuthResponse;

public interface AuthService {

    JwtAuthResponse authenticate(AuthRequest authRequest);

}
