package com.alexlin7.demo.auth;

import com.alexlin7.demo.entity.appUser.AppUser;
import com.alexlin7.demo.exception.NotFoundException;
import com.alexlin7.demo.service.AppUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SpringUserService implements UserDetailsService {
    private final AppUserService appUserService;

    public SpringUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AppUser appUser = appUserService.getUserByEmail(username);
            return new SpringUser(appUser);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException("Username is wrong.");
        }
    }
}
