package info.andchelp.fitwf.service;

import info.andchelp.fitwf.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String test = "Awfwaf";
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        return user;
    }
}
