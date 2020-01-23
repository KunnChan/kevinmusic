package com.music.kevinmusic.controller;

import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.Feedback;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.request.FeedbackRequest;
import com.music.kevinmusic.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class FeedbackController {

    private final FeedbackService service;

    @Autowired
    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @PostMapping("/feedback/save")
    public Feedback saveOrUpdate(@RequestBody Feedback feedback,
                             @RequestHeader MultiValueMap<String, String> headers){

        log.info("feedback saveOrUpdate => {}", feedback);
        Information information = CustomCommon.getBrowserInformation(headers);
        return service.saveOrUpdate(feedback, information);
    }

    @GetMapping("/zone/feedback/{id}")
    public Feedback get(@PathVariable Long id){
        log.info("feedback id " + id);
        return service.getFeedbackById(id);
    }

    @PostMapping("/zone/feedback/query")
    public Page<Feedback> get(@RequestBody FeedbackRequest request){

        log.info("feedback advance query => {}", request);
        return service.getFilter(request);
    }
}
