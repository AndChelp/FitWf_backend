package info.andchelp.fitwf.repository;

import info.andchelp.fitwf.model.Role;
import info.andchelp.fitwf.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends AbstractRepository<Role> {
    Optional<Role> findByType(RoleType type);
}
