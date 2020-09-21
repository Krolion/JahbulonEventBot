package com.server_service.google_calendar_package;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalendarQuickstart {

    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String CALENDAR_ID = "jahbuloneventbot@gmail.com";

    private NetHttpTransport HTTP_TRANSPORT;
    private Credential credential;
    private Calendar calendar;

    @SneakyThrows
    public CalendarQuickstart() {
        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        credential = getCredentials(HTTP_TRANSPORT);

        calendar = new Calendar
                .Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @SneakyThrows
    public Event GenerateEvent() {
        Event event = CreateEvent();
        event = calendar.events().insert(CALENDAR_ID, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());

        return event;
    }

    @SneakyThrows
    public String GenerateEventLink() {
        var event = GenerateEvent();

        return event.getHtmlLink();
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
/*
    @SneakyThrows
    public static void main(String... args) {
        var calendarQuickstart = new CalendarQuickstart();
        calendarQuickstart.GenerateEvent();
        calendarQuickstart.GenerateEvent();
        calendarQuickstart.GenerateEvent();
        System.out.println("Finished");
    }
*/
    public Event CreateEvent() {
        Event event = new Event()
                .setSummary("HB")
                .setLocation("HB")
                .setDescription("birthday");
        DateTime startDateTime = new DateTime("2020-10-01T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);
        DateTime endDateTime = new DateTime("2020-10-03T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);
        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
        event.setRecurrence(Arrays.asList(recurrence));
        EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("beswently@gmail.com"),
                new EventAttendee().setEmail("sbrin@example.com"),
                new EventAttendee().setEmail("plehalex09@gmail.com"),
        };
        event.setAttendees(Arrays.asList(attendees));
        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);
        String calendarId = "jahbuloneventbot@gmail.com";

        return event;
    }
}