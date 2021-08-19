package info.andchelp.fitwf.config;

import info.andchelp.fitwf.repository.RevokedTokenRepository;
import info.andchelp.fitwf.repository.UserRepository;
import info.andchelp.fitwf.security.jwt.JwtAuthenticationTokenFilter;
import info.andchelp.fitwf.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final RevokedTokenRepository revokedTokenRepository;

    public SecurityConfig(TokenService tokenService, UserRepository userRepository, RevokedTokenRepository revokedTokenRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.revokedTokenRepository = revokedTokenRepository;
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter(tokenService, userRepository, revokedTokenRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/token/**", "/api/auth/**").permitAll()
                .antMatchers("/api/user/**").authenticated()

                .and()
                .addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
