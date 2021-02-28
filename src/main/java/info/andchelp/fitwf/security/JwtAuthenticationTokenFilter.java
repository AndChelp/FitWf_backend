package info.andchelp.fitwf.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import info.andchelp.fitwf.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    final JwtService jwtService;

    public JwtAuthenticationTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtService.extractToken(request);
        if (StringUtils.hasLength(token)) {
            try {
                DecodedJWT decodedJWT = jwtService.validateToken(token);
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(decodedJWT);
                authentication.setAuthenticated(true);
                FitWfSecurityContext.setAuthentication(authentication);

            } catch (TokenExpiredException expiredException) {

            } finally {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
