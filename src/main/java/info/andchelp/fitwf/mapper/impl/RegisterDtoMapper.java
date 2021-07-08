package info.andchelp.fitwf.mapper.impl;

import info.andchelp.fitwf.dto.request.RegisterDto;
import info.andchelp.fitwf.mapper.ObjectMapper;
import info.andchelp.fitwf.mapper.config.GlobalMapperConfig;
import info.andchelp.fitwf.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public abstract class RegisterDtoMapper implements ObjectMapper<User, RegisterDto> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Override
    public abstract User map(RegisterDto dto);

}
