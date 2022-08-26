package com.kenzie.videocontentservice.service.model;

public class ShowResponse {

    //TODO: (ND) this class might not be necessary. Instructions didn't specify a ShowResponse
    private String showId;
    private String title;
    private ParentalGuideline parentalGuideLine;
    private int episodeLength = 0;
    private String genre;

    private double averageRating = 0;

    private int numberOfRatings = 0;

    private int numberOfSeasons = 0;

    public String getId() {return showId;}

    public void setId(String id) {this.showId = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

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

    public double getAverageRating() { return averageRating;}

    public void setAverageRating(int averageRating) { this.averageRating = averageRating; }

    public int getNumberOfRatings() { return numberOfRatings; }

    public void setNumberOfRatings(int numberOfRatings) { this.numberOfRatings = numberOfRatings; }

    public int getNumberOfSeasons() { return numberOfSeasons; }

    public void setNumberOfSeasons(int numberOfSeasons) { this.numberOfSeasons = numberOfSeasons; }
}
