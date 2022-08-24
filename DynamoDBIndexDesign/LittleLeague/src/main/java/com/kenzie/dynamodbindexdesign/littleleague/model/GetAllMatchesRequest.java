package com.kenzie.dynamodbindexdesign.littleleague.model;

public class GetAllMatchesRequest {
    private final String teamName;
    private final String startDate;
    private final String endDate;

    private GetAllMatchesRequest(String teamName, String startDate, String endDate) {
        this.teamName = teamName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String teamName;
        private String startDate;
        private String endDate;

        public Builder withTeamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Builder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public GetAllMatchesRequest build() {
            return new GetAllMatchesRequest(teamName, startDate, endDate);
        }
    }
}
