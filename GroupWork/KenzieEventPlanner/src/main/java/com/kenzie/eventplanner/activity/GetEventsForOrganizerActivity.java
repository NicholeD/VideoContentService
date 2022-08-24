package com.kenzie.eventplanner.activity;

import com.kenzie.eventplanner.dao.EventDao;
import com.kenzie.eventplanner.dao.models.Event;

import java.util.List;
import javax.inject.Inject;

/**
 * Handles requests to get an event by ID.
 */
public class GetEventsForOrganizerActivity {
    private EventDao eventDao;

    /**
     * Constructs an Activity with the given DAO.
     * @param eventDao The EventDao to use for fetching event
     */
    @Inject
    public GetEventsForOrganizerActivity(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    /**
     * Gets a list of events for an organizer.
     *
     * NOTE: A little deviation from usual.
     * Here we're using values directly in our arguments and return value,
     * whereas in a typical Coral service we'd have Request/Result objects
     * that would be generated from configuration via Coral. We haven't
     * created service infrastructure for this activity, so we're just
     * using the values directly.
     *
     * @param organizerId The ID of the organizer who's events to get
     * @return List of events organized by the given ID, empty if none are found
     */
    public List<Event> handleRequest(final String organizerId) {
        return eventDao.getEventsForOrganizer(organizerId);
    }
}
