package info.andchelp.fitwf.mapper.user;

import info.andchelp.fitwf.dto.SignUpDto;
import info.andchelp.fitwf.mapper.config.GlobalMapperConfig;
import info.andchelp.fitwf.model.Role;
import info.andchelp.fitwf.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(uses = GlobalMapperConfig.class)
public interface SignUpMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "activated", ignore = true)
    @Mapping(target = "authorities", source = "role")
    User toUserWithRole(SignUpDto signUpDto, Role role);

    default Set<Role> map(Role role) {
        return Set.of(role);
    }

}
