package com.alexlin7.demo.unit;

import com.alexlin7.demo.auth.SpringUser;
import com.alexlin7.demo.auth.SpringUserService;
import com.alexlin7.demo.entity.appUser.AppUser;
import com.alexlin7.demo.exception.NotFoundException;
import com.alexlin7.demo.service.AppUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private SpringUserService springUserService;

    @Test
    public void testLoadSpringUser() {
        String email = "ali97008@yahoo.com.tw";
        AppUser appUser = new AppUser();
        appUser.setId("123");
        appUser.setEmailAddress(email);
        appUser.setName("Alexlin7");

        when(appUserService.getUserByEmail(email))
                .thenReturn(appUser);

        SpringUser springUser = (SpringUser) springUserService.loadUserByUsername(email);

        Assert.assertEquals(appUser.getId(), springUser.getId());
        Assert.assertEquals(appUser.getName(), springUser.getName());
        Assert.assertEquals(appUser.getEmailAddress(), springUser.getUsername());

    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadSpringUserButNotFound() {
        when(appUserService.getUserByEmail(anyString()))
                .thenThrow(new NotFoundException());

        springUserService.loadUserByUsername("ali97008@yahoo.com.tw");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAuthoritiesAsNull() {
        AppUser user = mock(AppUser.class);
        doThrow(new IllegalArgumentException())
                .when(user).setAuthorities(isNull());

        user.setAuthorities(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAuthoritiesAsEmpty() {
        AppUser user = mock(AppUser.class);
        doThrow(new IllegalArgumentException())
                .when(user).setAuthorities(Collections.emptyList());

        user.setAuthorities(new ArrayList<>());
    }

}
