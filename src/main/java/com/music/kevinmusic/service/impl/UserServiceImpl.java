package com.music.kevinmusic.service.impl;

import com.google.gson.Gson;
import com.music.kevinmusic.command.UserCommand;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.*;
import com.music.kevinmusic.exception.NotFoundException;
import com.music.kevinmusic.repository.TransactionHistoryRepository;
import com.music.kevinmusic.repository.UserRepository;
import com.music.kevinmusic.request.UserRequest;
import com.music.kevinmusic.service.UserService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TransactionHistoryRepository historyRepo;
    private Gson gson;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder, TransactionHistoryRepository historyRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.historyRepo = historyRepo;
        gson = new Gson();
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    @Transactional
    public User getUserById(Long id, Information information) {
        TransactionHistory history = new TransactionHistory("User Id : " + id, EventAction.SEARCH_USER_BY_ID);
        history.setInformation(information);
        historyRepo.save(history);

        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User is not found for id "+ id));
    }

    @Override
    @Transactional
    public User getUserByUsername(String username, Information information) {
        TransactionHistory history = new TransactionHistory("Username : " + username, EventAction.SEARCH_USER_BY_USERNAME);
        history.setInformation(information);
        historyRepo.save(history);

        return userRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Username is not found for "+ username));
    }

    @Override
    @Transactional
    public Page<User> getFilter(UserRequest userRequest) {

        PageRequest pageable = CustomCommon.getPageable(userRequest.getPage());
        List<BooleanExpression> filters = getQuery(userRequest);

        TransactionHistory history = new TransactionHistory(gson.toJson(userRequest), EventAction.SEARCH_USER_ADVANCE);
        history.setInformation(userRequest.getInformation());
        historyRepo.save(history);

        if(filters.isEmpty()){
            return userRepo.findAll(pageable);
        }
        BooleanExpression filterExpression = filters.get(0);
        for (int i = 1; i <= filters.size() - 1; ++i) {
            filterExpression = filterExpression.and(filters.get(i));
        }
        return userRepo.findAll(filterExpression, pageable);
    }

    @Override
    @Transactional
    public User saveOrUpdate(UserCommand userCommand, Information information) {
        return null;
    }

    private List<BooleanExpression> getQuery(UserRequest userRequest) {

        QUser qUser = QUser.user;
        List<BooleanExpression> filters = new ArrayList<>();
//        if(userRequest.getName()!= null){
//            filters.add(qUser.name.equalsIgnoreCase(userRequest.getName()));
//        }
//        if(userRequest.getUsername() != null){
//            filters.add(qUser..equalsIgnoreCase(userRequest.getGenre()));
//        }
//        if(userRequest.getArtist() != null){
//            filters.add(songQuery.artist.equalsIgnoreCase(userRequest.getArtist()));
//        }
//        if(userRequest.getAlbum() != null){
//            filters.add(songQuery.album.equalsIgnoreCase(userRequest.getAlbum()));
//        }
//        if(userRequest.getLanguage() != null){
//            filters.add(songQuery.language.equalsIgnoreCase(userRequest.getLanguage()));
//        }
//        if(userRequest.getInfo() != null){
//            filters.add(songQuery.information.in(userRequest.getInfo()));
//        }
        return filters;
    }

}
