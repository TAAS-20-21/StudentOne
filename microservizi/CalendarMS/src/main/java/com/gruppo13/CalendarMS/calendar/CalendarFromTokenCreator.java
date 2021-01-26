package com.gruppo13.CalendarMS.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import java.io.IOException;
import java.security.GeneralSecurityException;


public class CalendarFromTokenCreator {
    private static final String APPLICATION_NAME = "StudentOne-calendar";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static Credential getCredentials() throws IOException {
        String accessToken = "ya29.a0AfH6SMAHZoWRrK6RY2JDxkQXCw_vD9IFZHd6fyL_MkFUoUgYHD9SHhdAbZwSzPFRCScFDMdHd5AWut6Zn3" +
                "SuXKDaFi8NtVv5noBt9TjT19_Px_7UprNtnlrsOLqEzAXa_ntJ-7Fq8_e0b-s0YTa7bue902i8heWjfofMjN8I2FM";
        return new GoogleCredential().setAccessToken(accessToken);
    }

    public static Calendar getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }
}
