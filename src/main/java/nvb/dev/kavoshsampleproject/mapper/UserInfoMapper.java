package nvb.dev.kavoshsampleproject.mapper;

import nvb.dev.kavoshsampleproject.dto.UserInfoDto;
import nvb.dev.kavoshsampleproject.entity.UserInfo;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserInfoMapper {

    @Mapping(source = "userInfo.id", target = "id")
    @Mapping(source = "userInfo.fullName", target = "fullName")
    @Mapping(source = "userInfo.username", target = "username")
    @Mapping(source = "userInfo.email", target = "email")
    @Mapping(source = "userInfo.password", target = "password")
    UserInfoDto toUserInfoDto(UserInfo userInfo);

    @Mapping(source = "userInfoDto.id", target = "id")
    @Mapping(source = "userInfoDto.fullName", target = "fullName")
    @Mapping(source = "userInfoDto.username", target = "username")
    @Mapping(source = "userInfoDto.email", target = "email")
    @Mapping(source = "userInfoDto.password", target = "password")
    UserInfo toUserInfo(UserInfoDto userInfoDto);

}
