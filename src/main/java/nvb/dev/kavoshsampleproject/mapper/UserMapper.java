package nvb.dev.kavoshsampleproject.mapper;

import nvb.dev.kavoshsampleproject.dto.UserDto;
import nvb.dev.kavoshsampleproject.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.password", target = "password")
    UserDto toUserDto(User user);

    @Mapping(source = "userDto.id", target = "id")
    @Mapping(source = "userDto.fullName", target = "fullName")
    @Mapping(source = "userDto.username", target = "username")
    @Mapping(source = "userDto.password", target = "password")
    User toUser(UserDto userDto);

}
