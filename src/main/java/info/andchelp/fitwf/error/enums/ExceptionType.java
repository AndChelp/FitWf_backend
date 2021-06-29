package info.andchelp.fitwf.error.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    DEFAULT("default"),

    DUPLICATED_VALUE("duplicated.value"),
    USERNAME_EXISTS("username.exists"),
    EMAIL_EXISTS("email.exists"),
    EMAIL_AND_USERNAME_EXISTS("email.and.username.exists"),

    USER_NOT_FOUND("user.not.found"),
    CODE_NOT_FOUND("code.not.found"),

    USER_IS_VERIFIED("user.is.verified"),

    INVALID_USERNAME_OR_PASSWORD("invalid.username.or.password"),
    INVALID_TOKEN("invalid.token"),
    ;
    private final String dictionaryCode;

    public String translateException() {
        return "";
    }
}
