package com.kenzie.dynamodbindexdesign.littleleague.helper;

import com.kenzie.dynamodbindexdesign.littleleague.model.Match;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtil {
    public static final String FIRST_MATCH_DATE = "2020-10-07";
    public static final String LAST_MATCH_DATE = "2020-10-25";
    public static final String DATE_BEFORE_ALL_MATCHES = "2020-01-01";
    public static final String DATE_WAY_BEFORE_ALL_MATCHES = "2017-01-21";
    public static final String TEAM_NOT_IN_LEAGUE = "Padres";
    public static final String TEAM_WITHOUT_HOME_GAMES = "Yankees";
    public static final String TEAM_WITHOUT_AWAY_GAMES = "Blue Jays";
    public static final String TEAM_WITH_ONE_HOME_GAME = "Angels";
    public static final String TEAM_WITH_ONE_AWAY_GAME = "Yankees";
    public static final String TEAM_WITH_ONE_GAME = "Yankees";
    public static final String TEAM = "Angels";
    public static final String AWAY_TEAM_INDEX = "AwayTeamMatchesIndex";
    public static final String HOME_TEAM_INDEX = "HomeTeamMatchesIndex";

    private TestUtil() {};

    public static void verifyQuery(DynamoDBQueryExpression<Match> query,
                             String team, String index, String startDate, String endDate) {

        assertNotNull(query, "Expected the query to contain a query expression.");

        assertEquals(index, query.getIndexName(),
            String.format("Expected Query Index to be %s, was %s.", index, query.getIndexName()));

        String keyConditionExpression = query.getKeyConditionExpression();

        assertTrue(keyConditionExpression.contains("matchDate"),
            "Expected the key condition to check against the match date.");

        if (AWAY_TEAM_INDEX.equals(index)) {
            assertTrue(keyConditionExpression.contains("awayTeam"),
                "Expected the key condition to check against the awayTeam field.");
        }

        if (HOME_TEAM_INDEX.equals(index)) {
            assertTrue(keyConditionExpression.contains("homeTeam"),
                "Expected the key condition to check against the homeTeam field.");
        }

        Map<String, AttributeValue> queryAttributes = query.getExpressionAttributeValues();
        assertNotNull(queryAttributes, "Expected the query to have a Map of attribute values");

        Collection<AttributeValue> queryAttributeValues = queryAttributes.values();
        AttributeValue teamValue = new AttributeValue().withS(team);
        AttributeValue startDateValue = new AttributeValue().withS(startDate);
        AttributeValue endDateValue = new AttributeValue().withS(endDate);

        assertTrue(queryAttributeValues.contains(teamValue),
            "Expected Query Attributes map to include the team name.");
        assertTrue(queryAttributeValues.contains(startDateValue),
            "Expected Query Attributes map to include the start date.");
        assertTrue(queryAttributeValues.contains(endDateValue),
            "Expected Query Attributes map to include the end date.");
    }
}
