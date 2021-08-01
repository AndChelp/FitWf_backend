package info.andchelp.fitwf.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import info.andchelp.fitwf.dictionary.MailMessageCode;
import info.andchelp.fitwf.dictionary.MessageSourceUtil;
import info.andchelp.fitwf.dictionary.Templates;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;

@Service
public class MailService {

    private final Configuration configuration;
    private final MessageSourceUtil messageSourceUtil;
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public MailService(Configuration configuration, MessageSourceUtil messageSourceUtil, JavaMailSender emailSender) {
        this.configuration = configuration;
        this.messageSourceUtil = messageSourceUtil;
        this.emailSender = emailSender;
    }

    @Async
    @SneakyThrows
    public void sendRegistrationCode(String email, String username, Locale locale) {
        Template template = configuration.getTemplate(Templates.SUCCESSFUL_REGISTRATION, locale);
        String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, Map.of("username", username, "buttonUrl", "fitwf.com"));
        String subject = messageSourceUtil.getMessage(MailMessageCode.SUCCESSFUL_REGISTRATION_TITLE, locale);
        emailSender.send(prepareMessage(email, subject, body));
    }

    private MimeMessage prepareMessage(String recipient, String subject, String htmlBody) throws MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(emailSender.createMimeMessage(), true);
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(recipient);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlBody, true);
        return mimeMessageHelper.getMimeMessage();
    }
}