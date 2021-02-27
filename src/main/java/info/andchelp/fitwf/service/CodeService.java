package info.andchelp.fitwf.service;

import info.andchelp.fitwf.exception.IllegalArgumentException;
import info.andchelp.fitwf.model.Code;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.model.enums.CodeType;
import info.andchelp.fitwf.repository.CodeRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class CodeService {

    final CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Code emailVerifyCode(User user) {
        if (user.isActivated()) {
            throw IllegalArgumentException.ofMailVerify(user.getUsername());
        }
        return codeFor(user, CodeType.EMAIL_VERIFY, Duration.ofDays(5));
    }

    public Code codeFor(User user, CodeType codeType, Duration duration) {
        return codeRepository.save(Code.builder()
                .user(user)
                .code(UUID.randomUUID())
                .type(codeType)
                .expires_at(new Timestamp(Instant.now().plus(duration).toEpochMilli()))
                .build());
    }

}
