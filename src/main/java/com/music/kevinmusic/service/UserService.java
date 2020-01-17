package com.music.kevinmusic.service;

import com.music.kevinmusic.command.UserCommand;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.User;
import com.music.kevinmusic.request.UserRequest;
import org.springframework.data.domain.Page;

public interface UserService {

    void save(User user);

    User getUserById(Long id, Information information);

    User getUserByUsername(String username, Information information);

    Page<User> getFilter(UserRequest userRequest);

    User saveOrUpdate(UserCommand userCommand, Information information);


}
