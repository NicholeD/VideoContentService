package com.kenzie.dynamodbindexdesign.littleleague;

import com.kenzie.dynamodbindexdesign.littleleague.dao.MatchDao;
import com.kenzie.dynamodbindexdesign.littleleague.model.*;

import java.util.List;
import javax.inject.Inject;

public class LittleLeagueService {
    private MatchDao matchDao;

    @Inject
    public LittleLeagueService(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    public GetAllMatchesResponse getAllMatches(GetAllMatchesRequest request) {

        List<Match> matches = matchDao.getAllMatchesForTeam(request.getTeamName(),
            request.getStartDate(), request.getEndDate());

        return GetAllMatchesResponse.builder()
            .withMatches(matches)
            .build();
    }

    public GetHomeMatchesResponse getHomeMatches(GetHomeMatchesRequest request) {

        List<Match> matches = matchDao.getHomeMatchesForTeam(request.getTeamName(),
            request.getStartDate(), request.getEndDate());

        return GetHomeMatchesResponse.builder()
            .withMatches(matches)
            .build();
    }

    public GetAwayMatchesResponse getAwayMatches(GetAwayMatchesRequest request) {

        List<Match> matches = matchDao.getAwayMatchesForTeam(request.getTeamName(),
            request.getStartDate(), request.getEndDate());

        return GetAwayMatchesResponse.builder()
            .withMatches(matches)
            .build();
    }


}
