package com.music.kevinmusic.service.impl;

import com.google.gson.Gson;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.Feedback;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.TransactionHistory;
import com.music.kevinmusic.enums.EventAction;
import com.music.kevinmusic.exception.NotFoundException;
import com.music.kevinmusic.filter.QFeedback;
import com.music.kevinmusic.repository.FeedbackRepository;
import com.music.kevinmusic.repository.TransactionHistoryRepository;
import com.music.kevinmusic.request.FeedbackRequest;
import com.music.kevinmusic.request.Notification;
import com.music.kevinmusic.service.FeedbackService;
import com.music.kevinmusic.service.NotificationService;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository repository;
    private final TransactionHistoryRepository historyRepository;
    private final NotificationService notificationService;
    private Gson gson;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository repository, TransactionHistoryRepository historyRepository, NotificationService notificationService) {
        this.repository = repository;
        this.historyRepository = historyRepository;
        this.notificationService = notificationService;
        gson = new Gson();
    }

    @Override
    public Feedback getFeedbackById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found for id "+ id));
    }

    @Override
    public Page<Feedback> getFilter(FeedbackRequest feedbackRequest) {
        PageRequest pageable = CustomCommon.getPageable(feedbackRequest.getPage());
        List<BooleanExpression> filters = getQuery(feedbackRequest);

        if(filters.isEmpty()){
            return repository.findAll(pageable);
        }
        BooleanExpression filterExpression = filters.get(0);
        for (int i = 1; i <= filters.size() - 1; ++i) {
            filterExpression = filterExpression.and(filters.get(i));
        }
        return repository.findAll(filterExpression, pageable);
    }

    @Transactional
    @Override
    public Feedback saveOrUpdate(Feedback feedback, Information information) {

        TransactionHistory history = new TransactionHistory(gson.toJson(feedback), EventAction.CREATE_FEEDBACK);
        history.setInformation(information);

        try{
            notificationService.sendNotification(new Notification(feedback.getText(), feedback.getEmailOrphone()));
        }catch (Exception ex){
            log.error("Sending Mail error {} ", ex);
        }

        historyRepository.save(history);
        return repository.save(feedback);
    }

    private List<BooleanExpression> getQuery(FeedbackRequest request) {

        QFeedback qFeedback = QFeedback.feedbackEntity;
        List<BooleanExpression> filters = new ArrayList<>();
        if(request.getId() != null){
            filters.add(qFeedback.id.eq(request.getId()));
        }
        if (request.getFromDt() != null) {
            filters.add(qFeedback.createdAt.goe(request.getFromDt()));
        }
        if (request.getToDt() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(request.getToDt().toInstant(), ZoneOffset.UTC);
            Date midnightDayAfterEndDate = Date.from(localDateTime.plusDays(1L).toInstant(ZoneOffset.UTC));
            filters.add(qFeedback.createdAt.before(midnightDayAfterEndDate));
        }
        if(request.getText() != null){
            filters.add(qFeedback.text.equalsIgnoreCase(request.getText().toString()));
        }
        if(request.getEmailOrphone() != null){
            filters.add(qFeedback.emailOrphone.likeIgnoreCase(request.getEmailOrphone()));
        }

        return filters;
    }
}
