package com.alexlin7.demo.entity.appUser;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("users")
public class AppUser {

    private String id;
    private String emailAddress;
    private String password;
    private String name;
    private List<UserAuthority> authorities;

}
