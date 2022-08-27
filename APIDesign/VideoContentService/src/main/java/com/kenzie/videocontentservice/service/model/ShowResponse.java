package com.kenzie.videocontentservice.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowResponse {

    @JsonProperty("showId")
    private String showId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("parentalGuideline")
    private String parentalGuideline;

    @JsonProperty("episodeLength")
    private Integer episodeLength;

    @JsonProperty("genre")
    private String genre;

    @JsonProperty("averageRating")
    private Double averageRating;

    @JsonProperty("numberOfRatings")
    private Integer numberOfRatings;

    @JsonProperty("numberOfSeasons")
    private Integer numberOfSeasons;

    public String getId() {return showId;}

    public void setId(String id) {this.showId = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getParentalGuideline() {
        return parentalGuideline;
    }

    public void setParentalGuideLine(String parentalGuideline) {
        this.parentalGuideline = parentalGuideline;
    }

    public Integer getEpisodeLength() {
        return episodeLength;
    }

    public void setEpisodeLength(Integer episodeLength) {
        this.episodeLength = episodeLength;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getAverageRating() { return averageRating;}

    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public Integer getNumberOfRatings() { return numberOfRatings; }

    public void setNumberOfRatings(Integer numberOfRatings) { this.numberOfRatings = numberOfRatings; }

    public Integer getNumberOfSeasons() { return numberOfSeasons; }

    public void setNumberOfSeasons(Integer numberOfSeasons) { this.numberOfSeasons = numberOfSeasons; }
}
