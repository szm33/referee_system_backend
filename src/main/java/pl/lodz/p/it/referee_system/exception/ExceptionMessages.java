package pl.lodz.p.it.referee_system.exception;

import java.util.ArrayList;
import java.util.List;

public class ExceptionMessages {

    public static final String OPTIMISTIC_LOCK_PROBLEM = "error.optimistic.lock";
    public static final String VALIDATION_ERROR = "error.validation";
    public static final String PASSWORD_VALIDATION_ERROR = "error.password.validation";
    public static final String PASSWORDS_NOT_THE_SAME = "error.passwords.not.the.same";
    public static final String INCORRECT_PASSWORD = "error.incorrect.password";
    public static final String USERNAME_NOT_UNIQUE = "error.username.not.unique";
    public static final String TEAM_NAME_NOT_UNIQUE = "error.team.name.not.unique";
    public static final String MATCH_TEAMS_ERROR = "error.match.teams";
    public static final String MATCH_REFEREES_ERROR = "error.match.referees";
    public static final String CREDENTIAL_ERROR = "error.credential";
    public static final String OWN_REPLACE_ERROR = "error.own.replace";
    public static final String DATE_OF_MATCH_ERROR = "error.date.of.match";

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
        exceptionMessages.add(MATCH_TEAMS_ERROR);
        exceptionMessages.add(MATCH_REFEREES_ERROR);
        exceptionMessages.add(CREDENTIAL_ERROR);
        exceptionMessages.add(OWN_REPLACE_ERROR);
        exceptionMessages.add(DATE_OF_MATCH_ERROR);
    }
}
