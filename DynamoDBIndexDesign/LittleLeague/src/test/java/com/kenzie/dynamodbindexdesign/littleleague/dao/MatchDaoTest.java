package com.kenzie.dynamodbindexdesign.littleleague.dao;

import com.kenzie.dynamodbindexdesign.littleleague.model.Match;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.defaultanswers.ForwardsInvocations;

import java.util.Collections;
import java.util.List;

import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.AWAY_TEAM_INDEX;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.FIRST_MATCH_DATE;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.HOME_TEAM_INDEX;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.LAST_MATCH_DATE;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.TEAM;
import static com.kenzie.dynamodbindexdesign.littleleague.helper.TestUtil.verifyQuery;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;
import static org.mockito.MockitoAnnotations.initMocks;

public class MatchDaoTest {

    @InjectMocks
    private MatchDao matchDao;

    @Mock
    private DynamoDBMapper mapper;

    @Mock
    private PaginatedQueryList<Match> paginatedQueryList;

    @Captor
    private ArgumentCaptor<DynamoDBQueryExpression<Match>> queryCaptor;

    @BeforeEach
    public void setup() {
        initMocks(this);
        when(mapper.query(eq(Match.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);
    }

    @Test
    public void getHomeMatchesForTeam_withTeamWithHomeGames_returnsDDBResult() {
        List<Match> results = matchDao.getHomeMatchesForTeam(TEAM, FIRST_MATCH_DATE, LAST_MATCH_DATE);

        // Then
        assertEquals(paginatedQueryList, results);

        verify(mapper, times(1)).query(eq(Match.class), queryCaptor.capture());

        DynamoDBQueryExpression<Match> query = queryCaptor.getValue();
        verifyQuery(query, TEAM, HOME_TEAM_INDEX, FIRST_MATCH_DATE, LAST_MATCH_DATE);
    }

    @Test
    public void getAwayMatchesForTeam_withTeamWithAwayGames_returnsDDBResult() {
        List<Match> results = matchDao.getAwayMatchesForTeam(TEAM, FIRST_MATCH_DATE, LAST_MATCH_DATE);

        // Then
        assertEquals(paginatedQueryList, results);

        verify(mapper, times(1)).query(eq(Match.class), queryCaptor.capture());

        DynamoDBQueryExpression<Match> query = queryCaptor.getValue();
        verifyQuery(query, TEAM, AWAY_TEAM_INDEX, FIRST_MATCH_DATE, LAST_MATCH_DATE);
    }

    @Test
    public void getAllMatchesForTeam_withTeam_callsQueryTwice() {
        when(mapper.query(eq(Match.class), any(DynamoDBQueryExpression.class)))
                .thenReturn(mock(
                        PaginatedQueryList.class,
                        withSettings().defaultAnswer(new ForwardsInvocations(Collections.emptyList()))));

        matchDao.getAllMatchesForTeam(TEAM, FIRST_MATCH_DATE, LAST_MATCH_DATE);

        verify(mapper, times(2)).query(eq(Match.class), any(DynamoDBQueryExpression.class));
    }

}

