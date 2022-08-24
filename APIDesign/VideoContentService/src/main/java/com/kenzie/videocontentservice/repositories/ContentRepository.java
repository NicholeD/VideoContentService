package com.kenzie.videocontentservice.repositories;


import com.kenzie.videocontentservice.service.model.EpisodeInfo;
import com.kenzie.videocontentservice.service.model.ParentalGuideline;
import com.kenzie.videocontentservice.service.model.ShowInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is a Mock Repository intended to function as a replacement for an actual Database.
 * DO NOT MODIFY THIS FILE
 */

@Service
public class ContentRepository {

    List<ShowInfo> shows;
    List<EpisodeInfo> episodes;

    public ContentRepository() {
        ShowInfo showInfo = new ShowInfo();
        showInfo.setShowId("fdsa879fd8s99");
        showInfo.setTitle("The Fake Show!");
        showInfo.setParentalGuideline(ParentalGuideline.TV_G);
        showInfo.setEpisodeLength(60);
        showInfo.setGenre("Fake");
        showInfo.setAverageRating(5.0);
        showInfo.setNumberOfRatings(10);
        showInfo.setNumberOfSeasons(2);
        showInfo.setBillingId("259012592");
        showInfo.setDatabaseId("542vdjs3289gja9332");

        shows = new ArrayList<>();
        shows.add(showInfo);

        EpisodeInfo episodeInfo = new EpisodeInfo();
        episodeInfo.setShowId("fdsa879fd8s99");
        episodeInfo.setTitle("Fake Episode");
        episodeInfo.setSeasonNumber(1);
        episodeInfo.setEpisodeNumber(2);
        episodeInfo.setAired(new Date());
        episodeInfo.setAverageRating(3.5);
        episodeInfo.setNumberOfRatings(20);
        episodeInfo.setDescription("Exciting drama happens in this episode of The Fake Show!");
        episodeInfo.setDatabaseId("jfdsa83jgfes38552891");

        episodes = new ArrayList<>();
        episodes.add(episodeInfo);
    }


    public void createShow(ShowInfo showInfo) {
        // Fake Update Database
    }

    public List<ShowInfo> getShows() {
        // Fake Database Query
        return shows;
    }

    public ShowInfo getShowById(String showId) {
        // Fake Database Query
        return shows.get(0);
    }

    public void addEpisode(EpisodeInfo episode) {
        // Fake Update Database
    }

    public EpisodeInfo getEpisode(String showId, Integer seasonNumber, Integer episodeNumber) {
        // Fake Database Query
        return episodes.get(0);
    }

    public List<EpisodeInfo> getEpisodes(String showId, Integer seasonNumber) {
        // Fake Database Query
        return episodes;
    }
}