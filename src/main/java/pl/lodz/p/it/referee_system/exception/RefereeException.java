package pl.lodz.p.it.referee_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

public class RefereeException extends ApplicationException {

    public static final String USERNAME_NOT_UNIQUE = "error.username.not.unique";

    public RefereeException(String message) {
        super(message);
    }

    public RefereeException(String message, Throwable cause) {
        super(message, cause);
    }

    public static RefereeException exceptionForNotUniqueUsername(Exception cause) {
        return new RefereeException(USERNAME_NOT_UNIQUE, cause);
    }
}
