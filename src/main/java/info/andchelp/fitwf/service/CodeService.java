package info.andchelp.fitwf.service;

import info.andchelp.fitwf.error.exception.IllegalArgumentException;
import info.andchelp.fitwf.error.exception.NotFoundException;
import info.andchelp.fitwf.model.Code;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.model.enums.CodeType;
import info.andchelp.fitwf.repository.CodeRepository;
import info.andchelp.fitwf.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class CodeService {

    private final CodeRepository codeRepository;
    private final UserRepository userRepository;

    public CodeService(CodeRepository codeRepository, UserRepository userRepository) {
        this.codeRepository = codeRepository;
        this.userRepository = userRepository;
    }

    public void verifyCode(UUID code) {
        Code retrievedCode = codeRepository.findByCode(code).orElseThrow(NotFoundException::ofCode);
        User user = retrievedCode.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);
        codeRepository.delete(retrievedCode);
    }

    public Code emailVerificationCode(User user) {
        if (user.isEmailVerified()) {
            throw IllegalArgumentException.ofMailVerify(user.getUsername());
        }
        return codeFor(user, CodeType.EMAIL_VERIFY, Duration.ofDays(5));
    }

    public Code codeFor(User user, CodeType codeType, Duration duration) {
        return codeRepository.save(Code.builder()
                .user(user)
                .code(UUID.randomUUID())
                .type(codeType)
                .build());
    }

}
