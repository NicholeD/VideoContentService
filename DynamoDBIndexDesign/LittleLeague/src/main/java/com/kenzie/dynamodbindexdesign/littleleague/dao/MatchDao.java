package com.kenzie.dynamodbindexdesign.littleleague.dao;

import com.kenzie.dynamodbindexdesign.littleleague.model.Match;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class MatchDao {
    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     * @param mapper Access to DynamoDB
     */
    @Inject
    public MatchDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves all home matches for the given team between startDate and endDate, inclusive.
     * @param team The name of the team to fetch matches for
     * @param startDate The first date to return matches for
     * @param endDate The last date to return matches for
     * @return A list of the matches for the given team between the given dates
     */
    public List<Match> getHomeMatchesForTeam(String team, String startDate, String endDate) {
        // PARTICIPANTS: Implement.
        //               use DynamoDBMapper's query method to retrieve all home games
        //               for the given team in the given date range.

        return Collections.emptyList();
    }

    /**
     * Retrieves all away matches for the given team between startDate and endDate, inclusive.
     * @param team The name of the team to fetch matches for
     * @param startDate The first date to return matches for
     * @param endDate The last date to return matches for
     * @return A list of the matches for the given team between the given dates
     */
    public List<Match> getAwayMatchesForTeam(String team, String startDate, String endDate) {
        // PARTICIPANTS: Implement.
        //               use DynamoDBMapper's query method to retrieve all away games
        //               for the given team in the given date range.

        return Collections.emptyList();
    }

    /**
     * Retrieves all matches for the given team between startDate and endDate, inclusive.
     * @param team The name of the team to fetch matches for
     * @param startDate The first date to return matches for
     * @param endDate The last date to return matches for
     * @return A list of the matches for the given team between the given dates
     */
    public List<Match> getAllMatchesForTeam(String team, String startDate, String endDate) {
        // PARTICIPANTS: Implement.
        //               use DynamoDBMapper's query method to retrieve all away games
        //               and home games for the given team in the given date range.
        //               Then combine the two into one list.

        return Collections.emptyList();
    }
}
