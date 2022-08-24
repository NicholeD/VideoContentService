package com.kenzie.dynamodbtabledesign.icecreamparlor.integration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.google.common.collect.ImmutableList;
import com.kenzie.dynamodbtabledesign.icecreamparlor.IceCreamParlorService;
import com.kenzie.dynamodbtabledesign.icecreamparlor.dependency.IceCreamParlorServiceComponent;
import com.kenzie.dynamodbtabledesign.icecreamparlor.helpers.DynamoDbClientProvider;
import com.kenzie.dynamodbtabledesign.icecreamparlor.model.Carton;
import com.kenzie.dynamodbtabledesign.icecreamparlor.model.Receipt;
import com.kenzie.dynamodbtabledesign.icecreamparlor.model.Sundae;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class Phase1Test {
    private static final String CARTONS_TABLE_NAME = "Cartons";

    private final AmazonDynamoDB client = DynamoDbClientProvider.getDynamoDBClient(Regions.US_EAST_1);

    @ParameterizedTest
    @ValueSource(strings = {CARTONS_TABLE_NAME})
    void expectedTable_exists(String tableName) {
        assertTableExists(tableName);
    }

    private void assertTableExists(String tableName) {
        for (String existingTableName : client.listTables().getTableNames()) {
            if (existingTableName.equals(tableName)) {
                return;
            }
        }
        fail(String.format("Did not find expected table, '%s'", tableName));
    }
}
