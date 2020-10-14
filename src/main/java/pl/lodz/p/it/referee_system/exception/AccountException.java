package pl.lodz.p.it.referee_system.exception;

public class AccountException extends ApplicationException {

    public static final String PASSWORDS_NOT_THE_SAME = "error.passwords.not.the.same";
    public static final String INCORRECT_PASSWORD = "error.incorect.password";


    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AccountException exceptionForNotMatchingPasswords() {
        return new AccountException(PASSWORDS_NOT_THE_SAME);
    }

    public static AccountException exceptionForIncorectPassword() {
        return new AccountException(INCORRECT_PASSWORD);
    }
}
