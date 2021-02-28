package info.andchelp.fitwf;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import info.andchelp.fitwf.model.Role;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.model.enums.RoleType;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

public class Test {
    static String secret = "secret";

    public static void main(String[] args) {
        Role role = new Role();
        role.setType(RoleType.ROLE_USER);
        User user = User.builder()
                .email("email1")
                .username("username1")
                .emailVerified(true)
                .authority(role)
                .build();
        String jwtToken = generateJwtToken(user);

        System.out.println(validateJwtToken(jwtToken));
    }public static DecodedJWT validateJwtToken(String authToken) {
        return JWT.require(Algorithm.HMAC512(secret)).build()
                .verify(authToken);
    }

    public static String generateJwtToken(User user) {
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("authorities", user.getAuthorities().stream()
                        .map(role -> role.getType().name())
                        .collect(Collectors.toList()))
                .withExpiresAt(Date.from(Instant.now().plus(Duration.ofMinutes(30))))
                .sign(Algorithm.HMAC512(secret));
    }
}
