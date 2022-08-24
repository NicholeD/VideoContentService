package com.kenzie.eventplanner.activity;

import com.kenzie.eventplanner.dao.EventDao;
import com.kenzie.eventplanner.dao.InviteDao;
import com.kenzie.eventplanner.dao.models.Event;

import java.util.List;
import javax.inject.Inject;

/**
 * Handles requests to get an event by ID.
 */
public class GetAttendingEventsForMemberActivity {
    private EventDao eventDao;
    private InviteDao inviteDao;

    /**
     * Constructs an Activity with the given DAO.
     * @param eventDao The EventDao to use for fetching events
     * @param inviteDao The InviteDao to use for fetching accepted event IDs
     */
    @Inject
    public GetAttendingEventsForMemberActivity(EventDao eventDao, InviteDao inviteDao) {
        this.eventDao = eventDao;
        this.inviteDao = inviteDao;
    }

    /**
     * Gets all events a given member is attending.
     *
     * NOTE: A little deviation from usual.
     * Here we're using values directly in our arguments and return value,
     * whereas in a typical Coral service we'd have Request/Result objects
     * that would be generated from configuration via Coral. We haven't
     * created service infrastructure for this activity, so we're just
     * using the values directly.
     *
     * @param memberId The ID of the member who's events to get
     * @return List of events the given member is attending, empty if they are not attending any events
     */
    public List<Event> handleRequest(final String memberId) {
        List<String> attendingEventIds = inviteDao.getAttendingEventsForMember(memberId);
        return eventDao.getEvents(attendingEventIds);
    }
}
