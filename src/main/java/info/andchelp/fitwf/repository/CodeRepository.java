package info.andchelp.fitwf.repository;

import info.andchelp.fitwf.model.Code;

import java.util.Optional;
import java.util.UUID;

public interface CodeRepository extends AbstractRepository<Code> {
    Optional<Code> findByCode(UUID code);
}
