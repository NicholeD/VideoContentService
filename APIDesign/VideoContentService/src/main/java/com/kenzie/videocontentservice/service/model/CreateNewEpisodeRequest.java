package com.kenzie.videocontentservice.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class CreateNewEpisodeRequest {

    @NotEmpty
    @JsonProperty("showId")
    private String showId;


    @NotEmpty
    @JsonProperty("seasonNumber")
    private int seasonNumber;


    @NotEmpty
    @JsonProperty("episodeNumber")
    private int episodeNumber;


    @NotEmpty
    @JsonProperty("title")
    private String title;


    @NotEmpty
    @JsonProperty("description")
    private String description;

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
