package com.kenzie.dynamodbtabledesign.icecreamparlor.integration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.BillingModeSummary;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.kenzie.dynamodbtabledesign.icecreamparlor.helpers.DynamoDbClientProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class Phase3Test {
    private static final String RECEIPTS_TABLE_NAME = "Receipts";

    private final AmazonDynamoDB client = DynamoDbClientProvider.getDynamoDBClient(Regions.US_EAST_1);

    @ParameterizedTest
    @ValueSource(strings = {RECEIPTS_TABLE_NAME})
    void expectedTable_exists(String tableName) {
        assertTableExists(tableName);

        TableDescription tableDescription = client.describeTable(tableName).getTable();

        assertEquals(2, tableDescription.getKeySchema().size(), "Your Table should have both a HASH and a RANGE key");
        assertEquals(2, tableDescription.getAttributeDefinitions().size(), "Your Table should have two attributes defined");
        assertEquals("PAY_PER_REQUEST", tableDescription.getBillingModeSummary().getBillingMode(), "The Billing mode should be 'PAY_PER_REQUEST'");
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
