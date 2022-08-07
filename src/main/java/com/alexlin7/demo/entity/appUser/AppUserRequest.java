package com.alexlin7.demo.entity.appUser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class AppUserRequest {

    @Schema(description = "The email address of user.", example = "test1234@test.com")
    @NotBlank
    private String emailAddress;
    @Schema(description = "The passowrd of user.")
    @NotBlank
    private String password;
    @Schema(description = "The full name of user.")
    @NotBlank
    private String name;
    @Schema(description = "The authority of user.")
    @NotEmpty
    private List<UserAuthority> authorities;

}
