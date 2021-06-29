package info.andchelp.fitwf.service;

import info.andchelp.fitwf.dto.request.LoginDto;
import info.andchelp.fitwf.dto.request.RegisterDto;
import info.andchelp.fitwf.dto.response.TokensDto;
import info.andchelp.fitwf.error.exception.AccessDeniedException;
import info.andchelp.fitwf.error.exception.DuplicateException;
import info.andchelp.fitwf.model.Code;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {


    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;
    private final CodeService codeService;
    private final JwtService jwtService;

    private final UserRepository userRepository;


    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, MailService mailService,
                       CodeService codeService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.codeService = codeService;
        this.jwtService = jwtService;
    }

    public TokensDto register(RegisterDto registerDto) {
        checkIfExists(registerDto);
        User user = userRepository.save(User.builder()
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build());

        Code code = codeService.emailVerificationCode(user);

        mailService.sendSimpleMessage(user.getEmail(), "Verify your email",
                "Click link below to verify your email and get all capabilities of FitWF!\n" +
                        "http://localhost:8080/api/public/code/verify?code=" + code.getCode() +
                        "\n-----------------------------------------------------------------------------\n" +
                        "Если вы получили это сообщение, вероятно это мой косяк. Можете спокойно удалить письмо.");
        return new TokensDto(jwtService.generateToken(user), UUID.randomUUID());
    }

    public TokensDto login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .filter(u -> passwordEncoder.matches(loginDto.getPassword(), u.getPassword()))
                .orElseThrow(AccessDeniedException::ofUsernameOrPassword);
        return new TokensDto(jwtService.generateToken(user), UUID.randomUUID());
    }

    private void checkIfExists(RegisterDto registerDto) {
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        boolean existsByUsername = userRepository.existsByUsername(registerDto.getUsername());
        if (existsByEmail && existsByUsername) {
            throw DuplicateException.ofEmailAndUsername(registerDto.getEmail(), registerDto.getUsername());
        } else if (existsByEmail) {
            throw DuplicateException.ofEmail(registerDto.getEmail());
        } else if (existsByUsername) {
            throw DuplicateException.ofUsername(registerDto.getEmail());
        }
    }

    public void signOut() {
        //FitWfSecurityContext.getUser()
    }
}
