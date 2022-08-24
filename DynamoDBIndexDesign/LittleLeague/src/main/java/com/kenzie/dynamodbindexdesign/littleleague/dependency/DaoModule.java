package com.kenzie.dynamodbindexdesign.littleleague.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.kenzie.dynamodbindexdesign.littleleague.utilities.DynamoDbClientProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DaoModule {

    @Provides
    @Singleton
    public DynamoDBMapper providesDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
    }
}
