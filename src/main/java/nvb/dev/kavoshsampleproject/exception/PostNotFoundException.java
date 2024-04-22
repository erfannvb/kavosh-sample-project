package nvb.dev.kavoshsampleproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Post with id '%d' does not exist";

    public PostNotFoundException() {
        super("There is no posts.");
    }

    public PostNotFoundException(long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
