package com.kenzie.dynamodbindexdesign.littleleague.integration;

import com.kenzie.dynamodbindexdesign.littleleague.LittleLeagueService;
import com.kenzie.dynamodbindexdesign.littleleague.dependency.DaggerLittleLeagueServiceComponent;
import com.kenzie.dynamodbindexdesign.littleleague.dependency.LittleLeagueServiceComponent;

import com.kenzie.dynamodbindexdesign.littleleague.model.GetHomeMatchesRequest;
import com.kenzie.dynamodbindexdesign.littleleague.model.GetHomeMatchesResponse;
import com.kenzie.dynamodbindexdesign.littleleague.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.DATE_BEFORE_ALL_MATCHES;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.DATE_WAY_BEFORE_ALL_MATCHES;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.FIRST_MATCH_DATE;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.LAST_MATCH_DATE;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.TEAM;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.TEAM_NOT_IN_LEAGUE;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.TEAM_WITHOUT_HOME_GAMES;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.TEAM_WITH_ONE_HOME_GAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Phase1Test {
    private static final LittleLeagueServiceComponent DAGGER = DaggerLittleLeagueServiceComponent.create();

    private LittleLeagueService littleLeagueService;

    @BeforeEach
    public void setup() {
        littleLeagueService = DAGGER.provideLittleLeagueService();
    }

    @Test
    public void getHomeMatches_teamNotInLeague_returnEmptyList() {
        // GIVEN: a team that is not in the league
        GetHomeMatchesRequest request = GetHomeMatchesRequest.builder()
            .withTeamName(TEAM_NOT_IN_LEAGUE)
            .withStartDate(FIRST_MATCH_DATE)
            .withEndDate(LAST_MATCH_DATE)
            .build();

        // WHEN: Get the Home Matches for the team
        GetHomeMatchesResponse response =  littleLeagueService.getHomeMatches(request);

        // THEN: Expect an empty result
        assertTrue(response.getMatches().isEmpty(),
            String.format("Expected finding home games for %s to return no games.", TEAM_NOT_IN_LEAGUE));
    }

    @Test
    public void getHomeMatches_teamWithNoHomeGames_returnEmptyList() {
        // GIVEN
        GetHomeMatchesRequest request = GetHomeMatchesRequest.builder()
            .withTeamName(TEAM_WITHOUT_HOME_GAMES)
            .withStartDate(FIRST_MATCH_DATE)
            .withEndDate(LAST_MATCH_DATE)
            .build();

        // WHEN
        GetHomeMatchesResponse response =  littleLeagueService.getHomeMatches(request);

        // THEN
        assertTrue(response.getMatches().isEmpty(),
            String.format("Expected finding home games for %s to return no games.", TEAM_WITHOUT_HOME_GAMES));
    }

    @Test
    public void getHomeMatches_tooEarly_returnEmptyList() {
        // GIVEN
        GetHomeMatchesRequest request = GetHomeMatchesRequest.builder()
            .withTeamName(TEAM_WITH_ONE_HOME_GAME)
            .withStartDate(DATE_WAY_BEFORE_ALL_MATCHES)
            .withEndDate(DATE_BEFORE_ALL_MATCHES)
            .build();

        // WHEN
        GetHomeMatchesResponse response =  littleLeagueService.getHomeMatches(request);

        // THEN
        assertTrue(response.getMatches().isEmpty(),
            String.format("Expected finding home games for %s to return no games due to the too-early end date.",
                TEAM_WITH_ONE_HOME_GAME));
    }

    @Test
    public void getHomeMatches_tooLate_returnEmptyList() {
        // GIVEN
        GetHomeMatchesRequest request = GetHomeMatchesRequest.builder()
            .withTeamName(TEAM_WITH_ONE_HOME_GAME)
            .withStartDate("2020-10-09")
            .withEndDate(LAST_MATCH_DATE)
            .build();

        // WHEN
        GetHomeMatchesResponse response =  littleLeagueService.getHomeMatches(request);

        // THEN
        assertTrue(response.getMatches().isEmpty(),
            String.format("Expected finding home games for %s to return no games due to the too-late start date.",
                TEAM_WITH_ONE_HOME_GAME));
    }

    @Test
    public void getHomeMatches_teamWithOneHomeGame_returnsOneHomeGame() throws Exception {
        // GIVEN
        GetHomeMatchesRequest request = GetHomeMatchesRequest.builder()
            .withTeamName(TEAM_WITH_ONE_HOME_GAME)
            .withStartDate(FIRST_MATCH_DATE)
            .withEndDate(LAST_MATCH_DATE)
            .build();

        // WHEN
        GetHomeMatchesResponse response =  littleLeagueService.getHomeMatches(request);

        //THEN
        assertEquals(1, response.getMatches().size(),
            String.format("Expected finding home games for %s to return 1 games.", TEAM_WITH_ONE_HOME_GAME));

        Match expectedMatch = Match.builder()
            .withMatchTime("6")
            .withMatchDate("2020-10-07")
            .withAwayTeam("Red Sox")
            .withHomeTeam("Angels")
            .withHomeTeamScore("54")
            .withAwayTeamScore("100")
            .build();

        Match actualMatch = response.getMatches().get(0);

        assertEquals(expectedMatch, actualMatch,
            String.format("Expected to find match: %s. Got %s.", expectedMatch, actualMatch));
    }

    @Test
    public void getHomeMatches_teamWithMultipleMatches_onlyReturnsHomeGames() throws Exception {
        // GIVEN
        GetHomeMatchesRequest request = GetHomeMatchesRequest.builder()
            .withTeamName(TEAM)
            .withStartDate(FIRST_MATCH_DATE)
            .withEndDate(LAST_MATCH_DATE)
            .build();

        // WHEN
        GetHomeMatchesResponse response =  littleLeagueService.getHomeMatches(request);

        // THEN
        assertEquals(1, response.getMatches().size(),
            String.format("Expected finding all games for %s to return 1 games.", TEAM));

        List<Match> expectedMatches = new ArrayList<>();

        expectedMatches.add(Match.builder()
            .withMatchTime("6")
            .withMatchDate("2020-10-07")
            .withHomeTeam(TEAM)
            .withAwayTeam("Red Sox")
            .withHomeTeamScore("54")
            .withAwayTeamScore("100")
            .build());


        assertTrue(response.getMatches().containsAll(expectedMatches),
            String.format("Expected to find match: %s.", expectedMatches));
    }
}
