package com.kenzie.eventplanner.dao.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Date;
import java.util.Objects;

/**
 * Represents an invite to an event sent to specific member.
 */
@DynamoDBTable(tableName = "DynamoDBIndexes-Invites")
public class Invite {
    public static final String MEMBER_ID_INDEX = "MemberIdIndex";
    private String eventId;
    private String memberId;
    private Boolean isAttending;
    private Boolean isCanceled;
    private Date timeReceived;

    @DynamoDBHashKey(attributeName = "eventId")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = MEMBER_ID_INDEX)
    @DynamoDBRangeKey(attributeName = "memberId")
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @DynamoDBAttribute(attributeName = "isAttending")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    public Boolean isAttending() {
        return isAttending;
    }

    public void setAttending(Boolean attending) {
        isAttending = attending;
    }

    @DynamoDBAttribute(attributeName = "isCanceled")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    public Boolean isCanceled() {
        return null != isCanceled && isCanceled;
    }

    public void setCanceled(Boolean canceled) {
        isCanceled = canceled;
    }

    @DynamoDBAttribute(attributeName = "timeReceived")
    public Date getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(Date timeReceived) {
        this.timeReceived = timeReceived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invite invite = (Invite) o;
        return getEventId().equals(invite.getEventId()) &&
            getMemberId().equals(invite.getMemberId()) &&
            Objects.equals(isAttending, invite.isAttending) &&
            Objects.equals(isCanceled, invite.isCanceled) &&
            Objects.equals(timeReceived, invite.timeReceived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventId(), getMemberId(), isAttending, isCanceled, timeReceived);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
            "eventId='" + eventId + '\'' +
            ", memberId='" + memberId + '\'' +
            ", isAttending=" + isAttending +
            ", isCanceled=" + isCanceled +
            ", timeReceived=" + timeReceived +
            '}';
    }
}
