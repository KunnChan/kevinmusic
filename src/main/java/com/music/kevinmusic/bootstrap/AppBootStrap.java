package com.music.kevinmusic.bootstrap;

import com.music.kevinmusic.domain.security.Authority;
import com.music.kevinmusic.domain.security.Role;
import com.music.kevinmusic.domain.security.User;
import com.music.kevinmusic.enums.ActivationStatus;
import com.music.kevinmusic.repository.security.AuthorityRepository;
import com.music.kevinmusic.repository.security.RoleRepository;
import com.music.kevinmusic.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

@RequiredArgsConstructor
@Component
public class AppBootStrap implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if(authorityRepository.count() == 0){
            loadSecurityData();
        }
    }

    private void loadSecurityData() {

        // beer auth
        Authority songCreate = authorityRepository.save(Authority.builder().permission("song.create").build());
        Authority songRead = authorityRepository.save(Authority.builder().permission("song.read").build());
        Authority songUpdate = authorityRepository.save(Authority.builder().permission("song.update").build());
        Authority songDelete = authorityRepository.save(Authority.builder().permission("song.delete").build());

        // customer auth
        Authority albumCreate = authorityRepository.save(Authority.builder().permission("album.create").build());
        Authority albumRead = authorityRepository.save(Authority.builder().permission("album.read").build());
        Authority albumUpdate = authorityRepository.save(Authority.builder().permission("album.update").build());
        Authority albumDelete = authorityRepository.save(Authority.builder().permission("album.delete").build());

        // point auth
        Authority pointCreate = authorityRepository.save(Authority.builder().permission("point.create").build());
        Authority pointRead = authorityRepository.save(Authority.builder().permission("point.read").build());
        Authority pointUpdate = authorityRepository.save(Authority.builder().permission("point.update").build());
        Authority pointDelete = authorityRepository.save(Authority.builder().permission("point.delete").build());

        //beer order
        Authority orderCreate = authorityRepository.save(Authority.builder().permission("order.create").build());
        Authority orderRead = authorityRepository.save(Authority.builder().permission("order.read").build());
        Authority orderUpdate = authorityRepository.save(Authority.builder().permission("order.update").build());
        Authority orderDelete = authorityRepository.save(Authority.builder().permission("order.delete").build());

        Authority customerOrderCreate = authorityRepository.save(Authority.builder().permission("customer.order.create").build());
        Authority customerOrderRead = authorityRepository.save(Authority.builder().permission("customer.order.read").build());
        Authority customerOrderUpdate = authorityRepository.save(Authority.builder().permission("customer.order.update").build());
        Authority customerOrderDelete = authorityRepository.save(Authority.builder().permission("customer.order.delete").build());


        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());
        Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());

        adminRole.setAuthorities(new HashSet<>(
                Arrays.asList(songCreate, songRead, songUpdate, songDelete,
                        albumCreate, albumRead, albumUpdate, albumDelete,
                        pointCreate, pointRead, pointUpdate, pointDelete,
                        orderCreate, orderRead, orderUpdate, orderDelete)));

        userRole.setAuthorities(new HashSet<>(Arrays.asList(songRead, albumRead, pointRead)));

        customerRole.setAuthorities(new HashSet<>(Arrays.asList(songRead, albumRead,
                customerOrderCreate, customerOrderUpdate, customerOrderDelete, customerOrderRead)));

        roleRepository.saveAll(Arrays.asList(adminRole, userRole, customerRole));

        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .name("Admin")
                .phone("985849493")
                .email("admin@gmail.com")
                .note("from app start account")
                .role(adminRole)
                .build();
        admin.setActivationStatus(ActivationStatus.ACTIVE);

        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .name("User 1")
                .phone("985849494")
                .email("user@gmail.com")
                .note("from app start account")
                .role(userRole)
                .build();
        user.setActivationStatus(ActivationStatus.ACTIVE);

        User customer = User.builder()
                .username("customer")
                .password(passwordEncoder.encode("customer"))
                .name("Customer 1")
                .phone("985849495")
                .email("customer@gmail.com")
                .note("from app start account")
                .role(customerRole)
                .build();
        customer.setActivationStatus(ActivationStatus.ACTIVE);

        userRepository.saveAll(Arrays.asList(admin, user, customer));
    }


}
