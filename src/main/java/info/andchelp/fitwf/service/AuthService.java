package info.andchelp.fitwf.service;

import info.andchelp.fitwf.dto.request.SignInDto;
import info.andchelp.fitwf.dto.request.SignUpDto;
import info.andchelp.fitwf.dto.response.Tokens;
import info.andchelp.fitwf.exception.AccessDeniedException;
import info.andchelp.fitwf.exception.DuplicateException;
import info.andchelp.fitwf.model.Code;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.model.enums.RoleType;
import info.andchelp.fitwf.repository.RoleRepository;
import info.andchelp.fitwf.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {


    final PasswordEncoder passwordEncoder;

    final MailService mailService;
    final CodeService codeService;
    final JwtService jwtService;

    final UserRepository userRepository;
    final RoleRepository roleRepository;


    public AuthService(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository,
                       RoleRepository roleRepository, MailService mailService, CodeService codeService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.codeService = codeService;
        this.jwtService = jwtService;
    }

    public Tokens signUp(SignUpDto signUpDto) {
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
                        "http://localhost:8080/api/public/verify?code=" + code.getCode() +
                        "\n-----------------------------------------------------------------------------\n" +
                        "Если вы получили это сообщение, вероятно это мой косяк. Можете спокойно удалить письмо.");
        return new Tokens(jwtService.generateAccessToken(user), UUID.randomUUID());
    }

    public Tokens signIn(SignInDto signInDto) {
        User user = userRepository.findByUsername(signInDto.getUsername())
                .filter(u -> passwordEncoder.matches(signInDto.getPassword(), u.getPassword()))
                .orElseThrow(AccessDeniedException::ofUsernameOrPassword);
        return new Tokens(jwtService.generateAccessToken(user), UUID.randomUUID());
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
}
