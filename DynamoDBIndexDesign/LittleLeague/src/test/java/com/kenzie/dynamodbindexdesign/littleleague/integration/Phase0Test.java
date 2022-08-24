package com.kenzie.dynamodbindexdesign.littleleague.integration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.fail;

public class Phase0Test {
    private static final String MATCHES_TABLE_NAME = "DynamoDBIndexes-LeagueMatches";

    private final DynamoDB client = new DynamoDB(AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.US_EAST_1).build());

    @Test
    public void matchesTable_exists() {
        for (Table table : client.listTables()) {
            if (table.getTableName().equals(MATCHES_TABLE_NAME)) {
                return;
            }
        }
        fail(String.format("Did not find expected table, '%s'", MATCHES_TABLE_NAME));
    }
}
