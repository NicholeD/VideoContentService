package com.kenzie.eventplanner.dao;

import com.kenzie.eventplanner.dao.models.Invite;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class InviteDaoTest {
    @InjectMocks
    private InviteDao inviteDao;

    @Mock
    private DynamoDBMapper mapper;

    @Mock
    private PaginatedQueryList<Invite> inviteList;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    void deleteInvite_deleteConditionsNotViolated_returnsTrue() {
        // GIVEN
        // WHEN
        boolean result = inviteDao.deleteInvite("EVENTID", "MEMBERID");

        // THEN
        assertTrue(result, "Expected deleteInvite() to return true when delete condition check not violated");
    }

    @Test
    void deleteInvite_deleteConditionsViolated_returnsFalse() {
        // GIVEN
        doThrow(ConditionalCheckFailedException.class).when(mapper).delete(any(Invite.class), any(DynamoDBDeleteExpression.class));

        // WHEN
        boolean result = inviteDao.deleteInvite("EVENTID", "MEMBERID");

        // THEN
        assertFalse(result, "Expected deleteInvite() to return false when delete condition check violated");
    }

    @Test
    public void getAcceptedInvites_eventHasAcceptedInvites_returnsInvites() {
        // GIVEN
        when(mapper.query(eq(Invite.class), any(DynamoDBQueryExpression.class))).thenReturn(inviteList);
        ArgumentCaptor<DynamoDBQueryExpression<Invite>> captor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);

        // WHEN
        List<Invite> result = inviteDao.getAcceptedInvitesForEvent("EVENTID");

        // THEN
        assertEquals(inviteList, result, "Expected list of invites to be what was returned from dynamodb");

        verify(mapper).query(eq(Invite.class), captor.capture());
        Invite queriedInvite = captor.getValue().getHashKeyValues();
        assertEquals("EVENTID", queriedInvite.getEventId(), "Expected query expression to query for " +
            "partition key: EVENTID");
    }
}
