package com.kenzie.dynamodbindexdesign.littleleague.dependency;

import com.kenzie.dynamodbindexdesign.littleleague.LittleLeagueService;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = DaoModule.class)
public interface LittleLeagueServiceComponent {
    LittleLeagueService provideLittleLeagueService();
}
