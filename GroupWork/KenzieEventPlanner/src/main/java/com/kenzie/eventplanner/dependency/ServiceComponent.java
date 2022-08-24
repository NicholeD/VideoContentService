package com.kenzie.eventplanner.dependency;

import com.kenzie.eventplanner.activity.CancelEventActivity;
import com.kenzie.eventplanner.activity.CreateEventActivity;
import com.kenzie.eventplanner.activity.CreateInviteActivity;
import com.kenzie.eventplanner.activity.CreateMemberActivity;
import com.kenzie.eventplanner.activity.DeleteMemberActivity;
import com.kenzie.eventplanner.activity.GetAcceptedInvitesForEventActivity;
import com.kenzie.eventplanner.activity.GetAttendingEventsForMemberActivity;
import com.kenzie.eventplanner.activity.GetEventActivity;
import com.kenzie.eventplanner.activity.GetEventsForOrganizerActivity;
import com.kenzie.eventplanner.activity.GetInviteActivity;
import com.kenzie.eventplanner.activity.GetInvitesForEventActivity;
import com.kenzie.eventplanner.activity.GetInvitesForMemberActivity;
import com.kenzie.eventplanner.activity.GetMemberActivity;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Declares the dependency roots that Dagger will provide.
 */
@Singleton
@Component(modules = DaoModule.class)
public interface ServiceComponent {
    DeleteMemberActivity provideDeleteMemberActivity();
    CreateMemberActivity provideCreateMemberActivity();
    GetMemberActivity provideGetMemberActivity();

    GetInviteActivity provideGetInviteActivity();
    GetInvitesForMemberActivity provideGetInvitesForMemberActivity();
    GetAcceptedInvitesForEventActivity provideGetAcceptedInvitesForEventActivity();
    GetInvitesForEventActivity provideGetInvitesForEventActivity();
    CreateInviteActivity provideCreateInviteActivity();
    GetAttendingEventsForMemberActivity provideGetAttendingEventsForMemberActivity();

    GetEventActivity provideGetEventActivity();
    CreateEventActivity provideCreateEventActivity();
    CancelEventActivity provideCancelEventActivity();
    GetEventsForOrganizerActivity provideGetEventsForOrganizerActivity();
}
