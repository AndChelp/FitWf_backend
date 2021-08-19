package info.andchelp.fitwf.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import info.andchelp.fitwf.dictionary.ClaimKey;
import info.andchelp.fitwf.dto.response.PairAuthTokens;
import info.andchelp.fitwf.error.exception.AccessDeniedException;
import info.andchelp.fitwf.error.exception.IllegalArgumentException;
import info.andchelp.fitwf.error.exception.NotFoundException;
import info.andchelp.fitwf.model.RevokedRefreshToken;
import info.andchelp.fitwf.model.User;
import info.andchelp.fitwf.repository.RevokedTokenRepository;
import info.andchelp.fitwf.repository.UserRepository;
import info.andchelp.fitwf.security.jwt.JwtWrapper;
import info.andchelp.fitwf.service.jwt.JwtTokenConfig;
import info.andchelp.fitwf.service.jwt.TokenConfigFactory;
import info.andchelp.fitwf.service.jwt.TokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    private final UserRepository userRepository;
    private final TokenConfigFactory tokenConfigFactory;
    private final RevokedTokenRepository revokedTokenRepository;

    @Value("${jwt.token.secret}")
    private String tokenSecret;

    public TokenService(UserRepository userRepository, TokenConfigFactory tokenConfigFactory, RevokedTokenRepository revokedTokenRepository) {
        this.userRepository = userRepository;
        this.tokenConfigFactory = tokenConfigFactory;
        this.revokedTokenRepository = revokedTokenRepository;
    }

    public PairAuthTokens refreshToken(String refreshToken) {
        JwtWrapper decodedJWT = validateJwt(refreshToken, TokenType.REFRESH);
        User user = userRepository.findById(decodedJWT.getUserId())
                .orElseThrow(NotFoundException::ofUser);
        if (revokedTokenRepository.existsByRefreshJtiAndUser(UUID.fromString(decodedJWT.getId()), user)) {
            throw AccessDeniedException.ofToken();
        }
        Date revokeAfter = decodedJWT.getRevokeAfter();
        if (revokeAfter.after(Date.from(Instant.now()))) {
            revokeToken(decodedJWT.getUUID(), user);
            return createPairAuthTokens(user);
        }
        return new PairAuthTokens(createAccessToken(user, decodedJWT.getId()), refreshToken);
    }

    public void revokeToken(UUID jti, User user) {
        revokedTokenRepository.save(new RevokedRefreshToken(jti, user));
    }

    public PairAuthTokens createPairAuthTokens(User user) {
        String jti = UUID.randomUUID().toString();
        return new PairAuthTokens(createAccessToken(user, jti), createRefreshToken(user, jti));
    }

    public String createRefreshToken(User user, String refreshTokenJTI) {
        JWTCreator.Builder refreshBuilder = createToken(user, TokenType.REFRESH)
                .withJWTId(refreshTokenJTI);
        Long expiration = JWT.decode(signToken(refreshBuilder)).getExpiresAt().getTime();
        Long now = System.currentTimeMillis();
        return signToken(refreshBuilder.withClaim(ClaimKey.REVOKE_AFTER, new Date((expiration + now) / 2)));
    }

    public String createAccessToken(User user, String refreshTokenJTI) {
        return signToken(createToken(user, TokenType.ACCESS)
                .withClaim(ClaimKey.REFRESH_JTI, refreshTokenJTI)
                .withClaim(ClaimKey.EMAIL, user.getEmail())
                .withClaim(ClaimKey.EMAIL_VERIFIED, user.isEmailVerified())
                .withClaim(ClaimKey.USERNAME, user.getUsername()));
    }

    public String signToken(JWTCreator.Builder builder) {
        return builder.sign(Algorithm.HMAC256(tokenSecret));
    }

    public JWTCreator.Builder createToken(User user, TokenType tokenType) {
        JwtTokenConfig config = tokenConfigFactory.getByType(tokenType);
        return JWT.create().withJWTId(UUID.randomUUID().toString())
                .withExpiresAt(Date.from(Instant.now().plus(config.getDuration(), config.getChronoUnit())))
                .withClaim(ClaimKey.TOKEN_TYPE, tokenType.toString())
                .withClaim(ClaimKey.USER_ID, user.getId());
    }

    public String createSignedToken(User user, TokenType tokenType) {
        return signToken(createToken(user, tokenType));
    }

    public JwtWrapper validateJwt(String token, TokenType expectedTokenType) {
        return new JwtWrapper(JWT.require(Algorithm.HMAC256(tokenSecret))
                .withClaim(ClaimKey.TOKEN_TYPE, expectedTokenType.toString())
                .build()
                .verify(token));
    }

    public void proceedVerifyEmailToken(String token) {
        JwtWrapper decodedJWT = validateJwt(token, TokenType.EMAIL_VERIFY);
        User user = userRepository
                .findById(decodedJWT.getUserId())
                .orElseThrow(NotFoundException::ofUser);
        if (user.isEmailVerified())
            throw IllegalArgumentException.ofMailVerify(user.getUsername());
        user.verifyEmail();
        userRepository.save(user);
    }

    public JwtWrapper extractBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasLength(authorization) && authorization.startsWith("Bearer")) {
            return validateJwt(authorization.substring(7), TokenType.ACCESS);
        }
        return null;
    }

}
