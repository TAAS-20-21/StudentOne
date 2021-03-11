package com.gruppo13.AuthenticationMS.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.io.IOException;
import java.security.GeneralSecurityException;
public class ApiCredentialManager {
    private DataStore<StoredCredential> dataStore;
    private static ApiCredentialManager instance = null;
    //Put your scopes here
    //public static String[] SCOPES_ARRAY = { "https://www.googleapis.com/auth/admin.directory.user" };
    private ApiCredentialManager() {
        try {
            dataStore = MemoryDataStoreFactory.getDefaultInstance().getDataStore("credentialDatastore");
        } catch (IOException e) {
            throw new RuntimeException("Unable to create in memory credential datastore", e);
        }
    }
    public static ApiCredentialManager getInstance() {
        if (instance == null) {
            instance = new ApiCredentialManager();
        }
        return instance;
    }
    public Credential getCredential(String email) throws Exception {
        try {
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setTransport(new NetHttpTransport())
                    .setJsonFactory(new JacksonFactory())
                    .addRefreshListener(
                            new DataStoreCredentialRefreshListener(
                                    email, dataStore))
                    .build();
            if(dataStore.containsKey(email)){
                StoredCredential storedCredential = dataStore.get(email);
                credential.setAccessToken(storedCredential.getAccessToken());
                credential.setRefreshToken(storedCredential.getRefreshToken());
            }else{
                //Do something of your own here to obtain the access token.
                //Most usually redirect the user to the OAuth page
            }
            return credential;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("isuue while setting credentials", e);
        }
    }
    //Call this when you've obtained the access token and refresh token from Google
    public void saveCredential(String email, OAuth2AccessToken accessToken) {
        StoredCredential storedCredential = new StoredCredential();
        storedCredential.setAccessToken(accessToken.getTokenValue());
        //storedCredential.setRefreshToken(credential.getRefreshToken());
        try {
            dataStore.set(email, storedCredential);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}