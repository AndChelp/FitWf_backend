package info.andchelp.fitwf.service;

import info.andchelp.fitwf.dto.request.LoginDto;
import info.andchelp.fitwf.dto.request.RegisterDto;
import info.andchelp.fitwf.dto.response.PairAuthTokens;
import info.andchelp.fitwf.error.exception.AccessDeniedException;
import info.andchelp.fitwf.error.exception.DuplicateException;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.model.enums.MailMessageType;
import info.andchelp.fitwf.repository.RevokedTokenRepository;
import info.andchelp.fitwf.repository.UserRepository;
import info.andchelp.fitwf.security.jwt.JwtAuthenticationToken;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final RevokedTokenRepository revokedTokenRepository;

    private final MailService mailService;
    private final TokenService tokenService;

    private final UserRepository userRepository;


    public AuthService(PasswordEncoder passwordEncoder, RevokedTokenRepository revokedTokenRepository, UserRepository userRepository, MailService mailService,
                       TokenService tokenService) {
        this.revokedTokenRepository = revokedTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.tokenService = tokenService;
    }

    @Transactional
    public PairAuthTokens register(RegisterDto registerDto) {
        checkIfExists(registerDto);
        User user = userRepository.save(User.builder()
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build());
        mailService.sendVerifyRegistrationLink(user, LocaleContextHolder.getLocale(), MailMessageType.SUCCESSFUL_REGISTRATION);
        return tokenService.createPairAuthTokens(user);
    }

    public PairAuthTokens login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .filter(u -> passwordEncoder.matches(loginDto.getPassword(), u.getPassword()))
                .orElseThrow(AccessDeniedException::ofUsernameOrPassword);
        return tokenService.createPairAuthTokens(user);
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

    public void logout() {
        JwtAuthenticationToken auth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        tokenService.revokeToken(auth.getCredentials().getRefreshJti(), auth.getPrincipal());
    }
}
