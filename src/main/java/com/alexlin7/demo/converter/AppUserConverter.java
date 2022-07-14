package com.alexlin7.demo.converter;

import com.alexlin7.demo.entity.appUser.AppUser;
import com.alexlin7.demo.entity.appUser.AppUserRequest;
import com.alexlin7.demo.entity.appUser.AppUserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class AppUserConverter {
    private AppUserConverter() {

    }

    public static AppUser toAppUser(AppUserRequest request) {
        AppUser user = new AppUser();
        user.setEmailAddress(request.getEmailAddress());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setAuthorities(request.getAuthorities());

        return user;
    }

    public static AppUserResponse toAppUserResponse(AppUser user) {
        AppUserResponse response = new AppUserResponse();
        response.setId(user.getId());
        response.setEmailAddress(user.getEmailAddress());
        response.setName(user.getName());
        response.setAuthorities(user.getAuthorities());

        return response;
    }

    public static List<AppUserResponse> toAppUserResponses(List<AppUser> users) {
        return users.stream()
                .map(AppUserConverter::toAppUserResponse)
                .collect(Collectors.toList());
    }
}

