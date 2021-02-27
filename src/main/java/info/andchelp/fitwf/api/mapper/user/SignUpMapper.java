package info.andchelp.fitwf.api.mapper.user;

import info.andchelp.fitwf.api.dto.SignUpDto;
import info.andchelp.fitwf.api.mapper.config.GlobalMapperConfig;
import info.andchelp.fitwf.api.mapper.ToEntityMapper;
import info.andchelp.fitwf.model.User;
import org.mapstruct.Mapper;


@Mapper(uses = GlobalMapperConfig.class)
public interface SignUpMapper extends ToEntityMapper<User, SignUpDto> {
}
