package com.kenzie.dynamodbindexdesign.littleleague.integration;

import com.kenzie.dynamodbindexdesign.littleleague.LittleLeagueService;
import com.kenzie.dynamodbindexdesign.littleleague.dependency.DaggerLittleLeagueServiceComponent;
import com.kenzie.dynamodbindexdesign.littleleague.dependency.LittleLeagueServiceComponent;

import com.kenzie.dynamodbindexdesign.littleleague.model.GetAllMatchesRequest;
import com.kenzie.dynamodbindexdesign.littleleague.model.GetAllMatchesResponse;
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
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.TEAM_WITH_ONE_AWAY_GAME;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.TEAM_WITH_ONE_GAME;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.TEAM_WITH_ONE_HOME_GAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Phase3Test {
    private static final LittleLeagueServiceComponent DAGGER = DaggerLittleLeagueServiceComponent.create();

    private LittleLeagueService littleLeagueService;

    @BeforeEach
    public void setup() {
        littleLeagueService = DAGGER.provideLittleLeagueService();
    }

    @Test
    public void getAllMatches_teamNotInLeague_returnEmptyList() {
        // GIVEN
        GetAllMatchesRequest request = GetAllMatchesRequest.builder()
            .withTeamName(TEAM_NOT_IN_LEAGUE)
            .withStartDate(FIRST_MATCH_DATE)
            .withEndDate(LAST_MATCH_DATE)
            .build();

        // WHEN
        GetAllMatchesResponse response =  littleLeagueService.getAllMatches(request);

        // THEN
        assertTrue(response.getMatches().isEmpty(),
            String.format("Expected finding all games for %s to return no games.", TEAM_NOT_IN_LEAGUE));
    }

    @Test
    public void getAllMatches_tooEarly_returnEmptyList() {
        // GIVEN
        GetAllMatchesRequest request = GetAllMatchesRequest.builder()
            .withTeamName(TEAM_WITH_ONE_HOME_GAME)
            .withStartDate(DATE_WAY_BEFORE_ALL_MATCHES)
            .withEndDate(DATE_BEFORE_ALL_MATCHES)
            .build();

        // WHEN
        GetAllMatchesResponse response =  littleLeagueService.getAllMatches(request);

        // THEN
        assertTrue(response.getMatches().isEmpty(),
            String.format("Expected finding all games for %s to return no games due to the too-early end date.",
                TEAM_WITH_ONE_HOME_GAME));
    }

    @Test
    public void getAllMatches_tooLate_returnEmptyList() {
        // GIVEN
        GetAllMatchesRequest request = GetAllMatchesRequest.builder()
            .withTeamName(TEAM_WITH_ONE_AWAY_GAME)
            .withStartDate("2020-10-24")
            .withEndDate(LAST_MATCH_DATE)
            .build();

        // WHEN
        GetAllMatchesResponse response =  littleLeagueService.getAllMatches(request);

        // THEN
        assertTrue(response.getMatches().isEmpty(),
            String.format("Expected finding all games for %s to return no games with too late start date.",
                TEAM_WITH_ONE_AWAY_GAME));
    }

    @Test
    public void getAllMatches_teamWithOneGame_returnsOneHomeGame() throws Exception {
        // GIVEN
        GetAllMatchesRequest request = GetAllMatchesRequest.builder()
            .withTeamName(TEAM_WITH_ONE_GAME)
            .withStartDate(FIRST_MATCH_DATE)
            .withEndDate(LAST_MATCH_DATE)
            .build();

        // WHEN
        GetAllMatchesResponse response =  littleLeagueService.getAllMatches(request);

        // THEN
        assertEquals(1, response.getMatches().size(),
            String.format("Expected finding all games for %s to return 1 games.", TEAM_WITH_ONE_GAME));

        Match expectedMatch = Match.builder()
            .withMatchTime("8")
            .withMatchDate("2020-10-23")
            .withAwayTeam("Yankees")
            .withHomeTeam("Giants")
            .withHomeTeamScore("34")
            .withAwayTeamScore("28")
            .build();

        Match actualMatch = response.getMatches().get(0);

        assertEquals(expectedMatch, actualMatch,
            String.format("Expected to find match: %s. Got %s.", expectedMatch, actualMatch));
    }

    @Test
    public void getAllMatches_teamWithMultipleMatches_returnsAllGames() throws Exception {
        // GIVEN
        GetAllMatchesRequest request = GetAllMatchesRequest.builder()
            .withTeamName(TEAM)
            .withStartDate(FIRST_MATCH_DATE)
            .withEndDate(LAST_MATCH_DATE)
            .build();

        // WHEN
        GetAllMatchesResponse response =  littleLeagueService.getAllMatches(request);

        // THEN
        assertEquals(3, response.getMatches().size(),
            String.format("Expected finding all games for %s to return 3 games.", TEAM));

        List<Match> expectedMatches = new ArrayList<>();

        expectedMatches.add(Match.builder()
            .withMatchTime("6")
            .withMatchDate("2020-10-07")
            .withHomeTeam(TEAM)
            .withAwayTeam("Red Sox")
            .withHomeTeamScore("54")
            .withAwayTeamScore("100")
            .build());

        expectedMatches.add(Match.builder()
            .withMatchTime("8")
            .withMatchDate("2020-10-14")
            .withHomeTeam("Dodgers")
            .withAwayTeam(TEAM)
            .withHomeTeamScore("73")
            .withAwayTeamScore("74")
            .build());

        expectedMatches.add(Match.builder()
            .withMatchTime("7")
            .withMatchDate("2020-10-21")
            .withHomeTeam("Blue Jays")
            .withAwayTeam(TEAM)
            .withHomeTeamScore("-")
            .withAwayTeamScore("-")
            .build());


        assertTrue(response.getMatches().containsAll(expectedMatches),
            String.format("Expected to find match: %s.", expectedMatches));
    }
}
