package info.andchelp.fitwf.dictionary;

public interface ExceptionCode {
    String DEFAULT = "default";

    String DUPLICATED_VALUE = "duplicated.value";
    String USERNAME_EXISTS = "username.exists";
    String EMAIL_EXISTS = "email.exists";
    String EMAIL_AND_USERNAME_EXISTS = "email.and.username.exists";

    String USER_NOT_FOUND = "user.not.found";

    String USER_IS_VERIFIED = "user.is.verified";

    String JWT_TOKEN_EXCEPTION = "jwt.token.exception";

    String INVALID_USERNAME_OR_PASSWORD = "invalid.username.or.password";
    String INVALID_TOKEN = "invalid.token";
    String VALIDATION_ERROR = "validation.error";

    String INVALID_EMAIL = "{invalid.email}";
    String INVALID_USERNAME = "{invalid.username}";
    String INVALID_PASSWORD = "{invalid.password}";
}
