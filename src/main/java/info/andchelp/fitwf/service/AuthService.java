package info.andchelp.fitwf.service;

import info.andchelp.fitwf.dto.request.SignInDto;
import info.andchelp.fitwf.dto.request.SignUpDto;
import info.andchelp.fitwf.dto.response.TokensDto;
import info.andchelp.fitwf.error.exception.AccessDeniedException;
import info.andchelp.fitwf.error.exception.DuplicateException;
import info.andchelp.fitwf.model.Code;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.model.enums.RoleType;
import info.andchelp.fitwf.repository.RoleRepository;
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
    private final RoleRepository roleRepository;


    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                       RoleRepository roleRepository, MailService mailService, CodeService codeService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.codeService = codeService;
        this.jwtService = jwtService;
    }

    public TokensDto signUp(SignUpDto signUpDto) {
        checkIfExists(signUpDto);
        User user = userRepository.save(User.builder()
                .email(signUpDto.getEmail())
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .authority(roleRepository.findByType(RoleType.ROLE_USER).orElseThrow())
                .build());

        Code code = codeService.emailVerificationCode(user);

        mailService.sendSimpleMessage(user.getEmail(), "Verify your email",
                "Click link below to verify your email and get all capabilities of FitWF!\n" +
                        "http://localhost:8080/api/public/code/verify?code=" + code.getCode() +
                        "\n-----------------------------------------------------------------------------\n" +
                        "Если вы получили это сообщение, вероятно это мой косяк. Можете спокойно удалить письмо.");
        return new TokensDto(jwtService.generateToken(user), UUID.randomUUID());
    }

    public TokensDto signIn(SignInDto signInDto) {
        User user = userRepository.findByUsername(signInDto.getUsername())
                .filter(u -> passwordEncoder.matches(signInDto.getPassword(), u.getPassword()))
                .orElseThrow(AccessDeniedException::ofUsernameOrPassword);
        return new TokensDto(jwtService.generateToken(user), UUID.randomUUID());
    }

    private void checkIfExists(SignUpDto signUpDto) {
        boolean existsByEmail = userRepository.existsByEmail(signUpDto.getEmail());
        boolean existsByUsername = userRepository.existsByUsername(signUpDto.getUsername());
        if (existsByEmail && existsByUsername) {
            throw DuplicateException.ofEmailAndUsername(signUpDto.getEmail(), signUpDto.getUsername());
        } else if (existsByEmail) {
            throw DuplicateException.ofEmail(signUpDto.getEmail());
        } else if (existsByUsername) {
            throw DuplicateException.ofUsername(signUpDto.getEmail());
        }
    }

    public void signOut() {
        //FitWfSecurityContext.getUser()
    }
}
