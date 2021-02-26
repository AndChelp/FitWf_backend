package info.andchelp.fitwf.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtHelper {
    @Value("${jwt.token.secret}")
    private String jwtSecret;

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer "))
            return authHeader.replace("Bearer ", "");
        return null;
    }

    /*public String generateJwtToken(JwtUser jwtUser) {
        return JWT.create()
                .withSubject(jwtUser.getUsername())
                //.withClaim("")
                .sign(Algorithm.HMAC512(jwtSecret));
    }*/

   /* public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return true;
    }*/
}
