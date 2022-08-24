package com.kenzie.eventplanner.integration;

import com.kenzie.eventplanner.activity.GetEventsForOrganizerActivity;
import com.kenzie.eventplanner.converter.ZonedDateTimeConverter;
import com.kenzie.eventplanner.dao.models.Event;
import com.kenzie.eventplanner.integration.helper.ActivityProvider;
import com.kenzie.eventplanner.integration.helper.TestDataProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Phase3Test {
    private final ZonedDateTimeConverter zonedDateTimeConverter = new ZonedDateTimeConverter();
    private GetEventsForOrganizerActivity getEventsForOrganizerActivity;
    private TestDataProvider testDataProvider;

    private static final String ORGANIZER_ID_WITH_EVENTS = "18BD88D4-C256-41F8-80F4-0F69FF61E352";
    private static final String ORGANIZER_ID_WITHOUT_EVENTS = "eventless-organizer-id";

    @BeforeEach
    public void setup() {
        getEventsForOrganizerActivity = ActivityProvider.provideGetEventsForOrganizerActivity();
        testDataProvider = new TestDataProvider();
    }

    @Test
    void getInvitesForOrganizerActivity_noEvents_returnsEmptyLIst() {
        // GIVEN + WHEN
        List<Event> result = getEventsForOrganizerActivity.handleRequest(ORGANIZER_ID_WITHOUT_EVENTS);

        // THEN
        assertTrue(
            result.isEmpty(),
            String.format("Expected no events to be returned but received: %s", result.size())
        );
    }

    @Test
    void getInvitesForOrganizerActivity_hasEvents_returnsEvents() {
        // GIVEN
        Event expectedEvent1 = createEvent("DA9B6265-18D9-43CB-874C-567A05DC1F01",
            "Dino's Pizza in Capitol Hill. First round of drinks are on June. Please RSVP by Wednesday.",
            "Dinner Club",
            "18BD88D4-C256-41F8-80F4-0F69FF61E352",
            zonedDateTimeConverter.unconvert("2020-04-11T12:36:11.643-07:00[America/Los_Angeles]"));
        Event expectedEvent2 = createEvent("C7800B60-874C-18D9-4DBF-DA9B567A1E9D",
            "Jai Thai in Capitol Hill. Please RSVP by Wednesday.",
            "Dinner Club",
            "18BD88D4-C256-41F8-80F4-0F69FF61E352",
            zonedDateTimeConverter.unconvert("2020-04-18T12:36:11.643-07:00[America/Los_Angeles]"));

        // GIVEN + WHEN
        List<Event> result = getEventsForOrganizerActivity.handleRequest(ORGANIZER_ID_WITH_EVENTS);

        // THEN
        assertEquals(2, result.size(),
            "Expected to receive two events, but received: " + result
        );
        assertTrue(result.contains(expectedEvent1),
            String.format("Expected event (%s) to be conatined in the restuls but received: %s", expectedEvent1, result)
        );
        assertTrue(result.contains(expectedEvent2),
            String.format("Expected event (%s) to be conatined in the restuls but received: %s", expectedEvent2, result)
        );
    }

    private Event createEvent(String eventId, String description, String name, String organizerId, ZonedDateTime time) {
        Event event = new Event();
        event.setId(eventId);
        event.setDescription(description);
        event.setName(name);
        event.setOrganizerId(organizerId);
        event.setTime(time);

        return event;
    }
}
