package com.music.kevinmusic.service;

import com.music.kevinmusic.command.PointCommand;
import com.music.kevinmusic.command.UserCommand;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.User;
import com.music.kevinmusic.request.UserRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    void saveAll(List<User> list);

    User getUserById(Long id);

    User getUserByUsername(String username);

    Page<User> getFilter(UserRequest userRequest);

    User saveOrUpdate(UserCommand userCommand, Information information);


    User addPoint(PointCommand pointCommand, Information information);

    User subPoint(PointCommand pointCommand, Information information);
}
