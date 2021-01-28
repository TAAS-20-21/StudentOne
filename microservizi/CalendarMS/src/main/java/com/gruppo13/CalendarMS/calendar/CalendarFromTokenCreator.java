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
        String accessToken = "ya29.a0AfH6SMCTLN1wh7H8i7mE27F07vy5cAMX_j-CkWxfc09Y0yxZ-FwPQ2li6u7EgeKLdRlyG_B3-lJcWF1ZogNf_wLDeRs" +
                "Nn4O_saP-yuYpscdPBKQeK3W9ucb8YyZnmgCL9wH1-WN2GowSnrXOJqR4mAs-VRDznGxyRGRDa-wUGhY";
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
