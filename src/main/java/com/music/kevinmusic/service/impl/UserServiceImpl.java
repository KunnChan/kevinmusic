package com.music.kevinmusic.service.impl;

import com.google.gson.Gson;
import com.music.kevinmusic.command.PointCommand;
import com.music.kevinmusic.command.UserCommand;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.Role;
import com.music.kevinmusic.domain.TransactionHistory;
import com.music.kevinmusic.domain.User;
import com.music.kevinmusic.enums.EventAction;
import com.music.kevinmusic.exception.NotFoundException;
import com.music.kevinmusic.filter.QUser;
import com.music.kevinmusic.repository.TransactionHistoryRepository;
import com.music.kevinmusic.repository.UserRepository;
import com.music.kevinmusic.request.UserRequest;
import com.music.kevinmusic.service.UserService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TransactionHistoryRepository historyRepo;
    private ModelMapper modelMapper;
    private Gson gson;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder, TransactionHistoryRepository historyRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.historyRepo = historyRepo;
        modelMapper = new ModelMapper();
        gson = new Gson();
    }

    @Transactional
    @Override
    public void saveAll(List<User> users) {

        List<User> modifyUser = new ArrayList<>();
        for (User user : users) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            modifyUser.add(user);
        }
        userRepo.saveAll(modifyUser);
    }

    @Override
    @Transactional
    public User getUserById(Long id, Information information) {
    //    TransactionHistory history = new TransactionHistory("User Id : " + id, EventAction.SEARCH_USER_BY_ID);
     //   history.setInformation(information);
     //   historyRepo.save(history);

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

        User user = modelMapper.map(userCommand, User.class);
        TransactionHistory history = new TransactionHistory(gson.toJson(userCommand));
        history.setInformation(information);
        if(user.getId() == null){
            // insert
         //   user.setCreatedAt(new Date());
         //   user.setCreatedBy("u1");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            if(userCommand.getRoles().isEmpty()){
                user.setRoles(new HashSet<>(Arrays.asList(new Role("USER"))));
            }else{
                user.setRoles(userCommand.getRoles());
            }

            history.setEventAction(EventAction.CREATE_USER);
        }else{
            // update
            User userForUpdate = userRepo.findById(user.getId())
                    .orElseThrow(() -> new NotFoundException("User id not found for update : " + user.getId()));

            if(userCommand.getRoles().isEmpty()){
                user.setRoles(userForUpdate.getRoles());
            }else{
                user.setRoles(userCommand.getRoles());
            }
            user.setPassword(userForUpdate.getPassword());
        //    user.setCreatedAt(userForUpdate.getCreatedAt());
        //    user.setCreatedBy(userForUpdate.getCreatedBy());

            history.setEventAction(EventAction.UPDATE_USER);
        }
    //    user.setUpdatedAt(new Date());
     //   user.setUpdatedBy("u1");

        historyRepo.save(history);
        return userRepo.save(user);

    }

    @Transactional
    @Override
    public User addPoint(PointCommand pointCommand, Information information) {
        User user = userRepo.findById(pointCommand.getUserId())
                .orElseThrow(() -> new NotFoundException("User is not found for id " + pointCommand.getUserId()));
        Double sum = user.getPoint() + pointCommand.getPoint();
        user.setPoint(sum);

        pointCommand.setPointAfterModify(sum);

        TransactionHistory history = new TransactionHistory(gson.toJson(pointCommand), EventAction.POINT_ADD);
        history.setInformation(information);
        historyRepo.save(history);
        return userRepo.save(user);
    }

    @Transactional
    @Override
    public User subPoint(PointCommand pointCommand, Information information) {
        User user = userRepo.findById(pointCommand.getUserId())
                .orElseThrow(() -> new NotFoundException("User is not found for id " + pointCommand.getUserId()));
        Double sum = user.getPoint() - pointCommand.getPoint();

        if(sum < 0 ){
            throw new NotFoundException("Point not enough. existing point : "+ user.getPoint() + ", requesting point :" + pointCommand.getPoint());
        }

        user.setPoint(sum);
        pointCommand.setPointAfterModify(sum);

        TransactionHistory history = new TransactionHistory(gson.toJson(pointCommand), EventAction.POINT_ADD);
        history.setInformation(information);
        historyRepo.save(history);
        return userRepo.save(user);
    }

    private List<BooleanExpression> getQuery(UserRequest userRequest) {

        QUser qUser = QUser.userEntity;
        List<BooleanExpression> filters = new ArrayList<>();
        if(userRequest.getId()!= null){
            filters.add(qUser.id.eq(userRequest.getId()));
        }
        if(userRequest.getName()!= null){
            filters.add(qUser.name.equalsIgnoreCase(userRequest.getName()));
        }
        if(userRequest.getUsername() != null){
            filters.add(qUser.username.equalsIgnoreCase(userRequest.getUsername()));
        }
        if(userRequest.getPhone() != null){
            filters.add(qUser.phone.equalsIgnoreCase(userRequest.getPhone()));
        }
        if(userRequest.getEmail() != null){
            filters.add(qUser.email.equalsIgnoreCase(userRequest.getEmail()));
        }
        if(userRequest.getActivationStatus() != null){
            filters.add(qUser.userStatus.equalsIgnoreCase(userRequest.getActivationStatus().toString()));
        }
        if(userRequest.getNote() != null){
            filters.add(qUser.note.likeIgnoreCase(userRequest.getNote()));
        }

        return filters;
    }

}
