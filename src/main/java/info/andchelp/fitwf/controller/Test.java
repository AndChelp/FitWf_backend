package info.andchelp.fitwf.controller;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class Test {
    @GetMapping("/user")
    public String testUser() {

        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return "USer";
    }

    @GetMapping("/public")
    public String testpub() {
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication());
        return "Public";
    }

    @GetMapping("/public/auth")
    public String auth() {
        AbstractAuthenticationToken a = new AbstractAuthenticationToken(Collections.singleton(new SimpleGrantedAuthority("ROLE_"))) {
            @Override
            public Object getCredentials() {
                return "null1";
            }

            @Override
            public Object getPrincipal() {
                return null;
            }
        };
        a.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(a);
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication());
        System.out.println(context.getAuthentication().getCredentials());
        return "authed";
    }
}
