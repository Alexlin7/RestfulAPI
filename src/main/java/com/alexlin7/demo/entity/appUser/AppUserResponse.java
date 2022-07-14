package com.alexlin7.demo.entity.appUser;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppUserResponse {

    private String id;
    private String emailAddress;
    private String name;
    private List<UserAuthority> authorities;

}
