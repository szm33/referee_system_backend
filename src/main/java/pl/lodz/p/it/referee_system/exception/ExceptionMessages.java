package pl.lodz.p.it.referee_system.exception;

import java.util.ArrayList;
import java.util.List;

public class ExceptionMessages {

    public static final String OPTIMISTIC_LOCK_PROBLEM = "error.optimistic.lock";
    public static final String VALIDATION_ERROR = "error.validation";
    public static final String PASSWORD_VALIDATION_ERROR = "error.password.validation";
    public static final String PASSWORDS_NOT_THE_SAME = "error.passwords.not.the.same";
    public static final String INCORRECT_PASSWORD = "error.incorrect.password";
    public static final String USERNAME_NOT_UNIQUE= "error.username.not.unique";
    public static final String TEAM_NAME_NOT_UNIQUE= "error.team.name.not.unique";

    public static final List<String> exceptionMessages;

    static {
        exceptionMessages = new ArrayList<>();
        exceptionMessages.add(OPTIMISTIC_LOCK_PROBLEM);
        exceptionMessages.add(VALIDATION_ERROR);
        exceptionMessages.add(PASSWORDS_NOT_THE_SAME);
        exceptionMessages.add(INCORRECT_PASSWORD);
        exceptionMessages.add(PASSWORD_VALIDATION_ERROR);
        exceptionMessages.add(USERNAME_NOT_UNIQUE);
        exceptionMessages.add(TEAM_NAME_NOT_UNIQUE);
    }
}
