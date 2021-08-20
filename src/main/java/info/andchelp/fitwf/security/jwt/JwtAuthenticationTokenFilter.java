package info.andchelp.fitwf.security.jwt;

import info.andchelp.fitwf.error.exception.AccessDeniedException;
import info.andchelp.fitwf.error.exception.NotFoundException;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.repository.RevokedTokenRepository;
import info.andchelp.fitwf.repository.UserRepository;
import info.andchelp.fitwf.security.SecurityContextUtils;
import info.andchelp.fitwf.service.TokenService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final RevokedTokenRepository revokedTokenRepository;

    public JwtAuthenticationTokenFilter(TokenService tokenService, UserRepository userRepository, RevokedTokenRepository revokedTokenRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.revokedTokenRepository = revokedTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtWrapper decodedJWT = tokenService.extractBearerToken(request);
        if (Objects.nonNull(decodedJWT)) {
            UUID tokenJti = decodedJWT.getRefreshJti();
            User user = userRepository.findById(decodedJWT.getUserId())
                    .orElseThrow(NotFoundException::ofUser);
            if (revokedTokenRepository.existsByRefreshJtiAndUser(tokenJti, user)) {
                throw AccessDeniedException.ofToken();
            }
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(user, decodedJWT);
            authentication.setAuthenticated(true);
            SecurityContextUtils.setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}