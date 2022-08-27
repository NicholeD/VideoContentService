package com.kenzie.videocontentservice.controller;

import com.kenzie.videocontentservice.service.ContentService;
import com.kenzie.videocontentservice.service.model.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Date;
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

    @PostMapping
    public ResponseEntity<ShowResponse> addNewShow(@RequestBody CreateShowRequest createShowRequest) {
        ShowInfo showInfo = new ShowInfo();
        showInfo.setShowId(randomUUID().toString());
        showInfo.setTitle(createShowRequest.getTitle());
        showInfo.setParentalGuideline(ParentalGuideline.valueOf(createShowRequest.getParentalGuideline()));
        showInfo.setEpisodeLength(createShowRequest.getEpisodeLength());
        showInfo.setGenre(createShowRequest.getGenre());
        contentService.createShow(showInfo);
        ShowResponse response = createShowResponse(showInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ShowResponse> searchShowById(@PathVariable String showId) {
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
    public ResponseEntity addNewEpisode(@PathVariable String showId,
                                        @PathVariable int seasonNumber,
                                        @RequestBody CreateNewEpisodeRequest createNewEpisodeRequest) {
        EpisodeInfo episode = new EpisodeInfo();
        episode.setShowId(showId);
        episode.setSeasonNumber(seasonNumber);
        episode.setEpisodeNumber(createNewEpisodeRequest.getEpisodeNumber());
        episode.setTitle(createNewEpisodeRequest.getTitle());
        episode.setDescription(createNewEpisodeRequest.getDescription());
        contentService.addEpisode(episode);
        return ResponseEntity.ok(200);
    }

    @GetMapping("/{showId}/season/{seasonNumber}/episode/{episodeNumber}")
    public ResponseEntity<EpisodeResponse> getEpisode(@PathVariable String showId,
                                                  @PathVariable String seasonNumber,
                                                  @PathVariable String episodeNumber) {
        EpisodeInfo episode = contentService.getEpisode(showId, seasonNumber, episodeNumber);
        EpisodeResponse response = createEpisodeResponse(episode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{showId}/season/{seasonNumber}/episode/all")
    public ResponseEntity<List<EpisodeResponse>> getAllEpisodesForShow(@PathVariable String showId,
                                                                @PathVariable int seasonNumber) {
        List<EpisodeInfo> episodes = contentService.getAllEpisodesForShow(showId, seasonNumber);
        //If there aren't any episodes or show doesn't exist, then return a 204
        if (episodes == null || episodes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise, convert list of episodes to list of EpisodeResponses and return
        List<EpisodeResponse> response = new ArrayList<>();
        for (EpisodeInfo episode : episodes) {
            response.add(this.createEpisodeResponse(episode));
        }
        return ResponseEntity.ok(response);
    }

    private ShowResponse createShowResponse(ShowInfo showInfo) {
        ShowResponse showResponse = new ShowResponse();
        showResponse.setId(showInfo.getShowId());
        showResponse.setTitle(showInfo.getTitle());
        showResponse.setParentalGuideLine(showInfo.getParentalGuideline().getName());
        showResponse.setEpisodeLength(showInfo.getEpisodeLength());
        showResponse.setGenre(showInfo.getGenre());
        showResponse.setAverageRating(showInfo.getAverageRating());
        showResponse.setNumberOfRatings(showInfo.getNumberOfRatings());
        showResponse.setNumberOfSeasons(showInfo.getNumberOfSeasons());
        return showResponse;
    }

    private EpisodeResponse createEpisodeResponse(EpisodeInfo episodeInfo) {
        EpisodeResponse episode = new EpisodeResponse();
        episode.setShowId(episodeInfo.getShowId());
        episode.setSeasonNumber(episodeInfo.getSeasonNumber());
        episode.setEpisodeNumber(episodeInfo.getEpisodeNumber());
        episode.setTitle(episodeInfo.getTitle());
        episode.setAverageRating(episodeInfo.getAverageRating());
        episode.setNumberOfRatings(episodeInfo.getNumberOfRatings());
        episode.setAired(episodeInfo.getAired());
        episode.setDescription(episodeInfo.getDescription());
        return episode;
    }
}
