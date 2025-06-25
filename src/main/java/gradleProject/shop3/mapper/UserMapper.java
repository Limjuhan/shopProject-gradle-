package gradleProject.shop3.mapper;

import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);
}
