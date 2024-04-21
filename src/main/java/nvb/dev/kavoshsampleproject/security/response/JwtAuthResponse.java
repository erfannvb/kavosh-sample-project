package nvb.dev.kavoshsampleproject.security.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponse {
    private String token;
}
