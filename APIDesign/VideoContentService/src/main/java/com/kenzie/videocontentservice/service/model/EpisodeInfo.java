package com.kenzie.videocontentservice.service.model;

import java.util.Date;
import java.util.Objects;

public class EpisodeInfo {

    private String showId;
    private Integer seasonNumber;
    private Integer episodeNumber;
    private String title;
    private Double averageRating;
    private Integer numberOfRatings;
    private Date aired;
    private String description;
    private String databaseId;

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public Date getAired() {
        return aired;
    }

    public void setAired(Date aired) {
        this.aired = aired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EpisodeInfo that = (EpisodeInfo) o;
        return showId.equals(that.showId) && Objects.equals(seasonNumber, that.seasonNumber) && Objects.equals(episodeNumber, that.episodeNumber) && Objects.equals(title, that.title) && Objects.equals(aired, that.aired);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showId, seasonNumber, episodeNumber, title, aired);
    }
}
