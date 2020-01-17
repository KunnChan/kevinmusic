package com.music.kevinmusic.service.impl;

import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.TransactionHistory;
import com.music.kevinmusic.exception.NotFoundException;
import com.music.kevinmusic.filter.QTransactionHistory;
import com.music.kevinmusic.repository.TransactionHistoryRepository;
import com.music.kevinmusic.request.TransactionHistoryRequest;
import com.music.kevinmusic.service.TransactionHistoryService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository repository;

    @Autowired
    public TransactionHistoryServiceImpl(TransactionHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionHistory getHistoryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction History not found for id "+ id));
    }

    @Override
    public Page<TransactionHistory> getFilter(TransactionHistoryRequest historyRequest) {

        PageRequest pageable = CustomCommon.getPageable(historyRequest.getPage());
        List<BooleanExpression> filters = getQuery(historyRequest);

        if(filters.isEmpty()){
            return repository.findAll(pageable);
        }
        BooleanExpression filterExpression = filters.get(0);
        for (int i = 1; i <= filters.size() - 1; ++i) {
            filterExpression = filterExpression.and(filters.get(i));
        }
        return repository.findAll(filterExpression, pageable);
    }

    private List<BooleanExpression> getQuery(TransactionHistoryRequest request) {

        QTransactionHistory historyQuery = QTransactionHistory.historyEntity;
        List<BooleanExpression> filters = new ArrayList<>();
        if(request.getId() != null){
            filters.add(historyQuery.id.equalsIgnoreCase(request.getId().toString()));
        }
        if (request.getFromDt() != null) {
            filters.add(historyQuery.transactionDate.goe(request.getFromDt()));
        }
        if (request.getToDt() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(request.getToDt().toInstant(), ZoneOffset.UTC);
            Date midnightDayAfterEndDate = Date.from(localDateTime.plusDays(1L).toInstant(ZoneOffset.UTC));
            filters.add(historyQuery.transactionDate.before(midnightDayAfterEndDate));
        }
        if(request.getEventAction() != null){
            filters.add(historyQuery.eventAction.equalsIgnoreCase(request.getEventAction().toString()));
        }
        if(request.getPayload() != null){
            filters.add(historyQuery.payload.likeIgnoreCase(request.getPayload()));
        }
        if(request.getInformation() != null){
            filters.add(historyQuery.information.likeIgnoreCase(request.getInformation()));
        }
        return filters;
    }
}
