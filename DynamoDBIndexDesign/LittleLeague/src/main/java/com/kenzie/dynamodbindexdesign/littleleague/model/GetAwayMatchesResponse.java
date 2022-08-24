package com.kenzie.dynamodbindexdesign.littleleague.model;

import java.util.List;

public class GetAwayMatchesResponse {
    private final List<Match> matches;

    private GetAwayMatchesResponse(List<Match> matches) {
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Match> matches;

        public Builder withMatches(List<Match> matches) {
            this.matches = matches;
            return this;
        }

        public GetAwayMatchesResponse build() {
            return new GetAwayMatchesResponse(matches);
        }
    }
}
