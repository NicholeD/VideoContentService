package com.kenzie.dynamodbindexdesign.littleleague.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

// PARTICIPANTS: Add annotations as necessary to map this POJO to the
// LeagueMatches table

@DynamoDBTable(tableName = "DynamoDBIndexes-LeagueMatches")
public class Match {
    public static final String HOME_MATCHES_INDEX = "HomeTeamMatchesIndex";
    public static final String AWAY_MATCHES_INDEX = "AwayTeamMatchesIndex";
    private String matchDate;
    private String matchTime;
    private String homeTeamScore;
    private String awayTeamScore;
    private String homeTeam;
    private String awayTeam;

    public Match(){}

    public Match(String matchDate, String matchTime, String homeTeamScore, String awayTeamScore, String homeTeam, String awayTeam) {
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    @DynamoDBHashKey()
    @DynamoDBIndexRangeKey(globalSecondaryIndexNames = {HOME_MATCHES_INDEX, AWAY_MATCHES_INDEX}, attributeName = "matchDate")
    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    @DynamoDBRangeKey(attributeName = "matchTime")
    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(String homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public String getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(String awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = HOME_MATCHES_INDEX, attributeName = "homeTeam")
    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = AWAY_MATCHES_INDEX, attributeName = "awayTeam")
    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Match match = (Match) o;
        return Objects.equals(matchDate, match.matchDate) &&
                Objects.equals(matchTime, match.matchTime) &&
                Objects.equals(homeTeamScore, match.homeTeamScore) &&
                Objects.equals(awayTeamScore, match.awayTeamScore) &&
                Objects.equals(homeTeam, match.homeTeam) &&
                Objects.equals(awayTeam, match.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchDate, matchTime, homeTeamScore, awayTeamScore, homeTeam, awayTeam);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String matchDate;
        private String matchTime;
        private String homeTeamScore;
        private String awayTeamScore;
        private String homeTeam;
        private String awayTeam;

        public Builder withMatchDate(String matchDate) {
            this.matchDate = matchDate;
            return this;
        }

        public Builder withMatchTime(String matchTime) {
            this.matchTime = matchTime;
            return this;
        }

        public Builder withHomeTeamScore(String homeTeamScore) {
            this.homeTeamScore = homeTeamScore;
            return this;
        }

        public Builder withAwayTeamScore(String awayTeamScore) {
            this.awayTeamScore = awayTeamScore;
            return this;
        }

        public Builder withHomeTeam(String homeTeam) {
            this.homeTeam = homeTeam;
            return this;
        }

        public Builder withAwayTeam(String awayTeam) {
            this.awayTeam = awayTeam;
            return this;
        }

        public Match build() {
            return new Match(matchDate, matchTime, homeTeamScore, awayTeamScore, homeTeam, awayTeam);
        }
    }
}
