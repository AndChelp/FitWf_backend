package info.andchelp.fitwf.service;

import info.andchelp.fitwf.api.dto.SignUpDto;
import info.andchelp.fitwf.api.mapper.user.SignUpMapper;
import info.andchelp.fitwf.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthService implements UserDetailsService {

    private final SignUpMapper signUpMapper;

    public AuthService(SignUpMapper signUpMapper) {
        this.signUpMapper = signUpMapper;
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
        User user = signUpMapper.toEntity(signUpDto);

    }
}
