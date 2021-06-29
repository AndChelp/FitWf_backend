package info.andchelp.fitwf.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import info.andchelp.fitwf.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${jwt.token.secret}")
    private String jwtSecret;

    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer "))
            return authHeader.replace("Bearer ", "");
        return null;
    }

    public String generateToken(User user) {
        return "Bearer " + JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("emailVerified", user.isEmailVerified())
                .withClaim("authorities", user.getAuthorities().stream()
                        .map(role -> role.getType().name())
                        .collect(Collectors.toList()))
                .withClaim("refreshToken", UUID.randomUUID().toString())
                .withExpiresAt(Date.from(Instant.now().plus(Duration.ofMinutes(30))))
                .sign(Algorithm.HMAC512(jwtSecret));
    }

    public DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC512(jwtSecret)).build()
                .verify(token);
    }
}
