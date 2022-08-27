package com.kenzie.videocontentservice.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class CreateShowRequest {

    @NotEmpty
    @JsonProperty("title")
    private String title;

    @NotEmpty
    @JsonProperty("parentalGuideline")
    private String parentalGuideline;

    @NotEmpty
    @JsonProperty("episodeLength")
    private int episodeLength;

    @NotEmpty
    @JsonProperty("genre")
    private String genre;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentalGuideline() {
        return parentalGuideline;
    }

    public void setparentalGuideline(String parentalGuideline) {
        this.parentalGuideline = parentalGuideline;
    }

    public int getEpisodeLength() {
        return episodeLength;
    }

    public void setEpisodeLength(int episodeLength) {
        this.episodeLength = episodeLength;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
