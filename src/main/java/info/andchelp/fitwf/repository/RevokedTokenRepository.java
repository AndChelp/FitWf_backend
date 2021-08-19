package info.andchelp.fitwf.repository;

import info.andchelp.fitwf.model.RevokedRefreshToken;
import info.andchelp.fitwf.model.User;

import java.util.UUID;

public interface RevokedTokenRepository extends AbstractRepository<RevokedRefreshToken> {

    boolean existsByRefreshJtiAndUser(UUID refreshJti, User user);

}
