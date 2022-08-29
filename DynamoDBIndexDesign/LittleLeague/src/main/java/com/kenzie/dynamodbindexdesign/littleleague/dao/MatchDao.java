package com.kenzie.dynamodbindexdesign.littleleague.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.kenzie.dynamodbindexdesign.littleleague.model.Match;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.*;
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
        DynamoDBQueryExpression<Match> queryExpression = getQueryExpression("homeTeam", team, startDate, endDate);
        queryExpression.withIndexName("HomeTeamMatchesIndex");
        return mapper.query(Match.class, queryExpression);
    }

    /**
     * Retrieves all away matches for the given team between startDate and endDate, inclusive.
     * @param team The name of the team to fetch matches for
     * @param startDate The first date to return matches for
     * @param endDate The last date to return matches for
     * @return A list of the matches for the given team between the given dates
     */
    public List<Match> getAwayMatchesForTeam(String team, String startDate, String endDate) {
        DynamoDBQueryExpression<Match> queryExpression = getQueryExpression("awayTeam", team, startDate, endDate);
        queryExpression.withIndexName("AwayTeamMatchesIndex");
        return mapper.query(Match.class, queryExpression);
    }

    /**
     * Retrieves all matches for the given team between startDate and endDate, inclusive.
     * @param team The name of the team to fetch matches for
     * @param startDate The first date to return matches for
     * @param endDate The last date to return matches for
     * @return A list of the matches for the given team between the given dates
     */
    public List<Match> getAllMatchesForTeam(String team, String startDate, String endDate) {
        DynamoDBQueryExpression<Match> homeQueryExpression = getQueryExpression("homeTeam", team, startDate, endDate);
        homeQueryExpression.withIndexName("HomeTeamMatchesIndex");
        List<Match> homeMatches = mapper.query(Match.class, homeQueryExpression);
        DynamoDBQueryExpression<Match> awayQueryExpression = getQueryExpression("awayTeam", team, startDate, endDate);
        awayQueryExpression.withIndexName("AwayTeamMatchesIndex");
        List<Match> awayMatches = mapper.query(Match.class, awayQueryExpression);
        List<Match> allMatches = new ArrayList<>();

        for (Match match : awayMatches) {
            allMatches.add(match);
        }

        for (Match match : homeMatches) {
            allMatches.add(match);
        }
        return allMatches;
    }

    private DynamoDBQueryExpression<Match> getQueryExpression(String whichTeam, String team, String startDate, String endDate) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put((":" + whichTeam), new AttributeValue().withS(team));
        valueMap.put(":startDate", new AttributeValue().withS(startDate));
        valueMap.put(":endDate", new AttributeValue().withS(endDate));

        DynamoDBQueryExpression<Match> queryExpression = new DynamoDBQueryExpression<Match>()
                .withConsistentRead(false)
                .withKeyConditionExpression(whichTeam + " = :" + whichTeam + " and matchDate between :startDate and :endDate")
                .withExpressionAttributeValues(valueMap);
        return queryExpression;
    }

}
