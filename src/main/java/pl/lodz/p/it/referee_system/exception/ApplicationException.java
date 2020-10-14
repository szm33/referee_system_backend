package pl.lodz.p.it.referee_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ApplicationException extends RuntimeException {

    static final public String KEY_DATABASE_CONNECTION_PROBLEM = "error.database.connection.problem";
    public static final String OPTIMISTIC_LOCK_PROBLEM = "error.optimistic.lock";
    public static final String PERSISTENCE_EXCEPTION_PROBLEM = "error.persistence.exception";
    public static final String KEY_EMAIL_SENDER_PROBLEM = "error.email.sender.problem";
    public static final String KEY_NO_RESULT_PROBLEM = "error.no.result.problem";

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ApplicationException exceptionForOptimisticLock(Throwable e) {
        return new ApplicationException(OPTIMISTIC_LOCK_PROBLEM, e);
    }
}
