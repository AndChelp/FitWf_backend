package info.andchelp.fitwf.model.enums;

import info.andchelp.fitwf.dictionary.MailMessageCode;
import info.andchelp.fitwf.dictionary.Templates;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailMessageType {
    SUCCESSFUL_REGISTRATION(Templates.SUCCESSFUL_REGISTRATION, MailMessageCode.SUCCESSFUL_REGISTRATION_TITLE),
    VERIFY_EMAIL(Templates.VERIFY_EMAIL, MailMessageCode.VERIFY_EMAIL_TITLE);

    private String template;
    private String title;
}
