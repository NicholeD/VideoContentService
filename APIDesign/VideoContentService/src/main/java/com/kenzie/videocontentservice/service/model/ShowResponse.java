package com.kenzie.videocontentservice.service.model;

public class ShowResponse {

    private String id;
    private String title;
    private ParentalGuideline parentalGuideLine;
    private int episodeLength;
    private String genre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ParentalGuideline getParentalGuideLine() {
        return parentalGuideLine;
    }

    public void setParentalGuideLine(ParentalGuideline parentalGuideLine) {
        this.parentalGuideLine = parentalGuideLine;
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
