package com.kenzie.eventplanner.integration.helper;

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
import com.kenzie.eventplanner.dependency.DaggerServiceComponent;
import com.kenzie.eventplanner.dependency.ServiceComponent;

public final class ActivityProvider {
    private static final ServiceComponent DAGGER = DaggerServiceComponent.create();

    private ActivityProvider() {
    }

    public static DeleteMemberActivity provideDeleteMemberActivity() {
        return DAGGER.provideDeleteMemberActivity();
    }
    public static CreateMemberActivity provideCreateMemberActivity() {
        return DAGGER.provideCreateMemberActivity();
    }
    public static GetMemberActivity provideGetMemberActivity() {
        return DAGGER.provideGetMemberActivity();
    }

    public static GetInviteActivity provideGetInviteActivity() {
        return DAGGER.provideGetInviteActivity();
    }
    public static GetInvitesForMemberActivity provideGetInvitesForMemberActivity() {
        return DAGGER.provideGetInvitesForMemberActivity();
    }
    public static GetAcceptedInvitesForEventActivity provideGetAcceptedInvitesForEventActivity() {
        return DAGGER.provideGetAcceptedInvitesForEventActivity();
    }
    public static GetInvitesForEventActivity provideGetInvitesForEventActivity() {
        return DAGGER.provideGetInvitesForEventActivity();
    }
    public static CreateInviteActivity provideCreateInviteActivity() {
        return DAGGER.provideCreateInviteActivity();
    }
    public static GetAttendingEventsForMemberActivity provideGetAttendingEventsForMemberActivity() {
        return DAGGER.provideGetAttendingEventsForMemberActivity();
    }

    public static GetEventActivity provideGetEventActivity() {
        return DAGGER.provideGetEventActivity();
    }
    public static CreateEventActivity provideCreateEventActivity() {
        return DAGGER.provideCreateEventActivity();
    }
    public static CancelEventActivity provideCancelEventActivity() {
        return DAGGER.provideCancelEventActivity();
    }
    public static GetEventsForOrganizerActivity provideGetEventsForOrganizerActivity() {
        return DAGGER.provideGetEventsForOrganizerActivity();
    }
}
