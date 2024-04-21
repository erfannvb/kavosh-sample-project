package nvb.dev.kavoshsampleproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UsernameExistsException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Username '%s' already exists!";

    public UsernameExistsException(String username) {
        super(ERROR_MESSAGE.formatted(username));
    }
}
