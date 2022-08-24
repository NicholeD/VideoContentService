package com.kenzie.eventplanner.integration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Phase2Test {
    private static final String TABLE_NAME_PREFIX = "DynamoDBIndexes-";
    private static final String EVENTS_TABLE_NAME = TABLE_NAME_PREFIX + "Events";

    public static final String GSI_PARTITION_KEY = "organizerId";
    public static final String GSI_SORT_KEY = "time";

    private final DynamoDB client = new DynamoDB(AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.US_EAST_1).build());

    @Test
    void eventsTable_hasExpectedGSI() {
        Table table = client.getTable(EVENTS_TABLE_NAME);
        assertEquals(1, table.describe().getGlobalSecondaryIndexes().size(),
            String.format("Found multiple GSIs on table %s, expected only one!", EVENTS_TABLE_NAME));

        GlobalSecondaryIndexDescription gsiDescription = table.getDescription().getGlobalSecondaryIndexes().get(0);
        assertEquals(2, gsiDescription.getKeySchema().size(),
            "Found only a partition key on GSI, expected a sort key!");

        List<KeySchemaElement> s = gsiDescription.getKeySchema();

        KeySchemaElement partitionKey = gsiDescription.getKeySchema().get(0);
        assertEquals(GSI_PARTITION_KEY, partitionKey.getAttributeName(),
            String.format("Found partition key '%s', expected '%s'", partitionKey.getAttributeName(), GSI_PARTITION_KEY));

        KeySchemaElement sortKey = gsiDescription.getKeySchema().get(1);
        assertEquals(GSI_SORT_KEY, sortKey.getAttributeName(),
            String.format("Found sort key '%s', expected '%s'", sortKey.getAttributeName(), GSI_SORT_KEY));
    }
}
