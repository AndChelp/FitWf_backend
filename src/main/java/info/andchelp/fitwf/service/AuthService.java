package info.andchelp.fitwf.service;

import info.andchelp.fitwf.dto.request.LoginDto;
import info.andchelp.fitwf.dto.request.RegisterDto;
import info.andchelp.fitwf.dto.response.TokensDto;
import info.andchelp.fitwf.error.exception.AccessDeniedException;
import info.andchelp.fitwf.error.exception.DuplicateException;
import info.andchelp.fitwf.mapper.impl.RegisterDtoMapper;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.repository.UserRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {


    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;
    private final CodeService codeService;
    private final JwtService jwtService;
    private final RegisterDtoMapper registerDtoMapper;


    private final UserRepository userRepository;


    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, MailService mailService,
                       CodeService codeService, JwtService jwtService, RegisterDtoMapper registerDtoMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.codeService = codeService;
        this.jwtService = jwtService;
        this.registerDtoMapper = registerDtoMapper;
    }

    @Transactional
    public TokensDto register(RegisterDto registerDto) {
        checkIfExists(registerDto);
        User savedUser = userRepository.save(registerDtoMapper.map(registerDto));
        mailService.sendRegistrationCode(savedUser.getEmail(), LocaleContextHolder.getLocale());
        return null;
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
