package com.kenzie.eventplanner.activity;

import com.kenzie.eventplanner.dao.InviteDao;
import com.kenzie.eventplanner.dao.models.Invite;

import java.util.List;
import javax.inject.Inject;

/**
 * Handles requests to get all accepted invites for a given event.
 */
public class GetAcceptedInvitesForEventActivity {
    private InviteDao inviteDao;

    /**
     * Constructs the Activity with the given DAO object.
     * @param inviteDao The InviteDao to use for querying invites
     */
    @Inject
    public GetAcceptedInvitesForEventActivity(InviteDao inviteDao) {
        this.inviteDao = inviteDao;
    }

    /**
     * Fetches all invites that are marked isAttending = true for a
     * given event. If none are found, returns empty list.
     * @param eventId The ID of the event to query invites for
     * @return List of accepted Invite objects for the event.
     */
    public List<Invite> handleRequest(final String eventId) {
        return inviteDao.getAcceptedInvitesForEvent(eventId);
    }
}
