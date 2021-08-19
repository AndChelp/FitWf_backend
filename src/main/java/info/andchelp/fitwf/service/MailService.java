package info.andchelp.fitwf.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import info.andchelp.fitwf.dictionary.MessageSourceUtil;
import info.andchelp.fitwf.model.MailMessage;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.model.enums.MailMessageType;
import info.andchelp.fitwf.repository.MailMessageRepository;
import info.andchelp.fitwf.service.jwt.TokenType;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Map;

@Service
public class MailService {

    private static final String TOKEN_ENDPOINT = "http://localhost:8080/api/token";
    private static final String MAIL_VERIFY_ENDPOINT = "/email/verify";
    private final Configuration configuration;
    private final MessageSourceUtil messageSourceUtil;
    private final JavaMailSender emailSender;
    private final MailMessageRepository mailMessageRepository;
    private final TokenService tokenService;

    private final int MAX_REGISTRATION_MESSAGES = 5;
    private final Instant LAST_DAY = Instant.now().minus(1, ChronoUnit.DAYS);

    @Value("${spring.mail.username}")
    private String sender;

    public MailService(Configuration configuration, MessageSourceUtil messageSourceUtil, JavaMailSender emailSender,
                       MailMessageRepository mailMessageRepository, TokenService tokenService) {
        this.configuration = configuration;
        this.messageSourceUtil = messageSourceUtil;
        this.emailSender = emailSender;
        this.mailMessageRepository = mailMessageRepository;
        this.tokenService = tokenService;
    }

    @Async
    public void sendVerifyRegistrationLink(User user, Locale locale, MailMessageType type) {
        if (checkMaxMessagesAfter(user, MAX_REGISTRATION_MESSAGES, LAST_DAY)) {
            System.out.println("Превышено максимальное кол-во сообщений за период");//todo: logger
            return;
        }
        String token = tokenService.createSignedToken(user, TokenType.EMAIL_VERIFY);
        String link = TOKEN_ENDPOINT + MAIL_VERIFY_ENDPOINT + "?token=" + token;
        Map<String, String> params = Map.of(
                "username", user.getUsername(),
                "buttonLink", link);
        sendToUser(user, type, params, locale);
    }

    @SneakyThrows
    private void sendToUser(User user, MailMessageType type, Object params, Locale locale) {
        Template template = configuration.getTemplate(type.getTemplate(), locale);
        String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        String subject = messageSourceUtil.getMessage(type.getTitle(), locale);
        emailSender.send(prepareMessage(user.getEmail(), subject, body));
        mailMessageRepository.save(new MailMessage(user, body, type));
    }

    private boolean checkMaxMessagesAfter(User user, int maxMessages, Instant after) {
        return mailMessageRepository.countByUserAndCreatedAtAfter(user, new Date(after.getEpochSecond())) >= maxMessages;
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