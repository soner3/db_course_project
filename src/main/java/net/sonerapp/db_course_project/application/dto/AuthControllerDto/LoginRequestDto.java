package net.sonerapp.db_course_project.application.dto.AuthControllerDto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
                @NotBlank(message = "The Username Field can not be blank") String username,
                @NotBlank(message = "The Password Field can not be blank") String password) {

}
