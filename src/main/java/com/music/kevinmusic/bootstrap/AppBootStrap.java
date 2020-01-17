package com.music.kevinmusic.bootstrap;

import com.music.kevinmusic.domain.ActivationStatus;
import com.music.kevinmusic.domain.Role;
import com.music.kevinmusic.domain.User;
import com.music.kevinmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class AppBootStrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        List<User> list = new ArrayList<>();

        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setName("User");
        user.setPhone("2323232323");
        user.setEmail("user@kmmusic.com");
        user.setNote("user for testing");
        user.setActivationStatus(ActivationStatus.ACTIVE);
        user.setRoles(new HashSet<>(Arrays.asList(new Role("USER"))));

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setName("Admin");
        admin.setPhone("1212121212");
        admin.setEmail("admin@kmmusic.com");
        admin.setNote("Admin account for testing");
        admin.setActivationStatus(ActivationStatus.ACTIVE);
        admin.setRoles(new HashSet<>(Arrays.asList(new Role("ADMIN"))));

        list.add(user);
        list.add(admin);

        userService.saveAll(list);
    }
}
