package com.music.kevinmusic.service;

import com.music.kevinmusic.domain.Feedback;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.request.FeedbackRequest;
import org.springframework.data.domain.Page;

public interface FeedbackService {

    Feedback getFeedbackById(Long id);

    Page<Feedback> getFilter(FeedbackRequest feedbackRequest);

    Feedback saveOrUpdate(Feedback feedback, Information information);
}
