package com.kenzie.videocontentservice.controller;

import com.kenzie.videocontentservice.service.ContentService;
import com.kenzie.videocontentservice.service.model.CreateShowRequest;
import com.kenzie.videocontentservice.service.model.ShowInfo;
import com.kenzie.videocontentservice.service.model.ShowResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/show")
public class ShowController {

    private ContentService contentService;

    ShowController(ContentService contentService) {
        this.contentService = contentService;
    }

    // Create your endpoints here and add the necessary annotations

    @PostMapping
    public ResponseEntity addNewShow(@RequestBody CreateShowRequest createShowRequest) {
        ShowInfo showInfo = new ShowInfo();
        showInfo.setShowId(randomUUID().toString());
        showInfo.setGenre(createShowRequest.getGenre());
        showInfo.setTitle(createShowRequest.getTitle());
        showInfo.setParentalGuideline(createShowRequest.getParentalGuideLine());

        contentService.createShow(showInfo);

//        ShowResponse showResponse = createShowResponse(showInfo);

        return ResponseEntity.ok(200);
    }

//    private ShowResponse createShowResponse(ShowInfo showInfo) {
//        ShowResponse showResponse = new ShowResponse();
//        showResponse.setId(showInfo.getShowId());
//        showResponse.setTitle(showInfo.getTitle());
//        showResponse.setGenre(showInfo.getGenre());
//        showResponse.setParentalGuideLine(showInfo.getParentalGuideline());
//        showResponse.setEpisodeLength(showInfo.getEpisodeLength());
//        return showResponse;
//    }
}
