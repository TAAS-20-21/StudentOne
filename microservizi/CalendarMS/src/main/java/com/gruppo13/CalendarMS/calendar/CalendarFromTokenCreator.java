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
        String accessToken = "ya29.a0AfH6SMBhiJeB9CREgZ9Mq49s9MOGPqtbXx1OpJjpn359pARmudNM8HtEjQyh4DQK8CFmSs1IigF86o206SzmId_aWVja4b9lIYMdcEniJuFNK3ugyMLWLOBDHqWZQejz-bfU7BS0Qn4Hiw0X54PFXCz1iHZKM_vK4NkBUUvP66Z0";
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
