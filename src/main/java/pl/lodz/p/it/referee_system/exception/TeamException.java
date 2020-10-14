package pl.lodz.p.it.referee_system.exception;

public class TeamException extends ApplicationException {

    public static final String NAME_NOT_UNIQUE_IN_LEAGUE = "error.name.not.unique.in.league";

    public TeamException(String message) {
        super(message);
    }

    public TeamException(String message, Throwable cause) {
        super(message, cause);
    }

    public static TeamException exceptionForNotUniqueNameInLeague(Throwable cause) {
        return new TeamException(NAME_NOT_UNIQUE_IN_LEAGUE, cause);
    }
}
