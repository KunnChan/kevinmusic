package com.music.kevinmusic.controller;

import com.music.kevinmusic.domain.TransactionHistory;
import com.music.kevinmusic.request.TransactionHistoryRequest;
import com.music.kevinmusic.service.TransactionHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history")
@Slf4j
public class TransactionHistoryController {

    private final TransactionHistoryService service;

    @Autowired
    public TransactionHistoryController(TransactionHistoryService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public TransactionHistory get(@PathVariable Long id){
        log.info("history id " + id);
        return service.getHistoryById(id);
    }

    @PostMapping("/query")
    public Page<TransactionHistory> get(@RequestBody TransactionHistoryRequest historyRequest){

        log.info("history advance query => {}", historyRequest);
        return service.getFilter(historyRequest);
    }
}
