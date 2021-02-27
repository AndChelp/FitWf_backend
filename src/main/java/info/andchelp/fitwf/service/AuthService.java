package info.andchelp.fitwf.service;

import info.andchelp.fitwf.dto.SignUpDto;
import info.andchelp.fitwf.exception.DuplicateException;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.model.enums.RoleType;
import info.andchelp.fitwf.repository.RoleRepository;
import info.andchelp.fitwf.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return null;
    }

    public void signUp(SignUpDto signUpDto) {
        checkIfExists(signUpDto);
        userRepository.save(User.builder()
                .email(signUpDto.getEmail())
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .authority(roleRepository.findByType(RoleType.ROLE_USER).orElseThrow())
                .build());
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
