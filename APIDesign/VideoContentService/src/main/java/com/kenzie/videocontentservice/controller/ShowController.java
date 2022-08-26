package com.kenzie.videocontentservice.controller;

import com.kenzie.videocontentservice.service.ContentService;
import com.kenzie.videocontentservice.service.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{showId}")
    public ResponseEntity<ShowResponse> searchShowById(@PathVariable("showId") String showId) {
        ShowInfo show = contentService.getShow(showId);
        //If there are no shows, then return a 204
        if (show == null) {
            return ResponseEntity.notFound().build();
        }
        //Otherwise, convert into a ShowResponse
        ShowResponse showResponse = createShowResponse(show);
        return ResponseEntity.ok(showResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShowResponse>> getAllShows() {
        List<ShowInfo> shows = contentService.getAllShows();
        //If there aren't any shows, then return a 204
        if (shows == null || shows.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise, convert list of shows into list of ShowResponses and return
        List<ShowResponse> response = new ArrayList<>();
        for (ShowInfo show : shows) {
            response.add(this.createShowResponse(show));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{showId}/season/{seasonNumber}/episode")
    public ResponseEntity addNewEpisode(@RequestBody CreateNewEpisodeRequest createNewEpisodeRequest) {
        EpisodeInfo episode = new EpisodeInfo();
        episode.setShowId(createNewEpisodeRequest.getShowId());
        episode.setSeasonNumber(0);
        episode.setEpisodeNumber(0);
        episode.setTitle(createNewEpisodeRequest.getTitle());
        episode.setDescription(createNewEpisodeRequest.getDescription());

        contentService.addEpisode(episode);

        return ResponseEntity.ok(200);
    }

    private ShowResponse createShowResponse(ShowInfo showInfo) {
        ShowResponse showResponse = new ShowResponse();
        showResponse.setId(showInfo.getShowId());
        showResponse.setTitle(showInfo.getTitle());
        showResponse.setGenre(showInfo.getGenre());
        showResponse.setParentalGuideLine(showInfo.getParentalGuideline());
        showResponse.setEpisodeLength(0);
        showResponse.setAverageRating(0);
        showResponse.setNumberOfRatings(0);
        showResponse.setNumberOfSeasons(0);
        return showResponse;
    }
}
