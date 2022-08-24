package com.kenzie.eventplanner.integration.helper;

import com.kenzie.eventplanner.activity.CreateEventActivity;
import com.kenzie.eventplanner.activity.CreateInviteActivity;
import com.kenzie.eventplanner.activity.CreateMemberActivity;
import com.kenzie.eventplanner.dao.models.Event;
import com.kenzie.eventplanner.dao.models.Invite;
import com.kenzie.eventplanner.dao.models.Member;

import java.sql.Date;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Helper class to provide test data to facilitate integration tests.
 */
public class TestDataProvider {
    private final CreateEventActivity createEventActivity = ActivityProvider.provideCreateEventActivity();
    private final CreateMemberActivity createMemberActivity = ActivityProvider.provideCreateMemberActivity();
    private final CreateInviteActivity createInviteActivity = ActivityProvider.provideCreateInviteActivity();
    private int idCounter = 1;

    public Event createEvent() {
        int eventNumber = idCounter++;
        Event event = new Event();
        event.setName("An event " + eventNumber);
        event.setCanceled(false);
        event.setDescription("A longer description of the same event " + eventNumber);
        event.setTime(ZonedDateTime.now().plusDays(7));
        event.setOrganizerId(createMember().getId());
        return createEvent(event);
    }

    public Event createEvent(Event event) {
        return createEventActivity.handleRequest(event);
    }

    public Member createMember() {
        Member member = new Member();
        member.setName("Irma Member " + idCounter++);
        member.setId(String.format("%03d", idCounter) + "." + UUID.randomUUID().toString());
        return createMember(member);
    }

    public Member createMember(Member member) {
        return createMemberActivity.handleRequest(member);
    }

    public Invite createInvite() {
        return createRejectedInviteForEvent(createEvent());
    }

    public Invite createRejectedInviteForEvent(Event event) {
        Invite invite = new Invite();
        invite.setEventId(event.getId());
        invite.setMemberId(createMember().getId());
        invite.setAttending(false);
        invite.setCanceled(false);
        invite.setTimeReceived(Date.from(Instant.now()));
        return createInvite(invite);
    }

    public Invite createAcceptedInviteForEvent(Event event) {
        Invite invite = new Invite();
        invite.setEventId(event.getId());
        invite.setMemberId(createMember().getId());
        invite.setAttending(true);
        invite.setCanceled(false);
        invite.setTimeReceived(Date.from(Instant.now()));
        return createInvite(invite);
    }

    public Invite createAcceptedInviteForEvent(Event event, String memberId) {
        Invite invite = new Invite();
        invite.setEventId(event.getId());
        invite.setMemberId(memberId);
        invite.setAttending(true);
        invite.setCanceled(false);
        invite.setTimeReceived(Date.from(Instant.now()));
        return createInvite(invite);
    }

    public Invite createRejectedInviteForEvent(Event event, String memberId) {
        Invite invite = new Invite();
        invite.setEventId(event.getId());
        invite.setMemberId(memberId);
        invite.setAttending(false);
        invite.setCanceled(false);
        invite.setTimeReceived(Date.from(Instant.now()));
        return createInvite(invite);
    }

    public Invite createInvite(Invite invite) {
        return createInviteActivity.handleRequest(invite);
    }
}
