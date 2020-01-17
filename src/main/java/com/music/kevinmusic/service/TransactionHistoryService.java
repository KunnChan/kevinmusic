package com.music.kevinmusic.service;

import com.music.kevinmusic.domain.TransactionHistory;
import com.music.kevinmusic.request.TransactionHistoryRequest;
import org.springframework.data.domain.Page;

public interface TransactionHistoryService {

    TransactionHistory getHistoryById(Long id);

    Page<TransactionHistory> getFilter(TransactionHistoryRequest historyRequest);

}
