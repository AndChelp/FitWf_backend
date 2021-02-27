package info.andchelp.fitwf.service;

import info.andchelp.fitwf.dto.SignUpDto;
import info.andchelp.fitwf.exception.DuplicateException;
import info.andchelp.fitwf.mapper.user.SignUpMapper;
import info.andchelp.fitwf.model.Role;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.model.enums.RoleType;
import info.andchelp.fitwf.repository.RoleRepository;
import info.andchelp.fitwf.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final SignUpMapper signUpMapper;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public AuthService(SignUpMapper signUpMapper, UserRepository userRepo, RoleRepository roleRepo) {
        this.signUpMapper = signUpMapper;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String test = "Awfwaf";
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        return user;
    }

    public void signUp(SignUpDto signUpDto) {
        checkIfExists(signUpDto);
        Role role = roleRepo.findByType(RoleType.ROLE_USER).orElseThrow();
        userRepo.save(signUpMapper.toUserWithRole(signUpDto, role));
    }

    private void checkIfExists(SignUpDto signUpDto) {
        boolean existsByEmail = userRepo.existsByEmail(signUpDto.getEmail());
        boolean existsByUsername = userRepo.existsByUsername(signUpDto.getUsername());
        if (existsByEmail && existsByUsername) {
            throw DuplicateException.ofEmailAndUsername(signUpDto.getEmail(), signUpDto.getUsername());
        } else if (existsByEmail) {
            throw DuplicateException.ofEmail(signUpDto.getEmail());
        } else if (existsByUsername) {
            throw DuplicateException.ofUsername(signUpDto.getEmail());
        }
    }
}
