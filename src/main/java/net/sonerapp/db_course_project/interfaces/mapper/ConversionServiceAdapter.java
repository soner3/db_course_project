package net.sonerapp.db_course_project.interfaces.mapper;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

import net.sonerapp.db_course_project.application.dto.UserControllerDto.UserDto;
import net.sonerapp.db_course_project.application.dto.UserTokenControllerDto.UserTokenDto;
import net.sonerapp.db_course_project.core.model.User;
import net.sonerapp.db_course_project.core.model.UserToken;

@Service
public class ConversionServiceAdapter {

    private final ConversionService conversionService;

    public ConversionServiceAdapter(@Lazy final ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public UserTokenDto mapUserTokenToUserTokenDto(final UserToken source) {
        return (UserTokenDto) conversionService.convert(source, TypeDescriptor.valueOf(UserToken.class),
                TypeDescriptor.valueOf(UserTokenDto.class));
    }

    public UserDto mapUserToUserDto(final User source) {
        return (UserDto) conversionService.convert(source, TypeDescriptor.valueOf(User.class),
                TypeDescriptor.valueOf(UserDto.class));
    }

}