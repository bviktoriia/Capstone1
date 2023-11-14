package org.example.utils;

public class UserCredentials {

    private static final String clientId = "aedd356bdbde48408e592c730bb564b8";
    private static final String clientSecret = "e47baf4bd3ae4a56913df5cdfb01c166";
    private static final String redirectUri = "http://localhost:3000";
    private static final String userId = "31ok6gblzqtadwzm4tacxdvsebsi";


    public static String getClientId() {
        return clientId;
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public static String getRedirectUri() {
        return redirectUri;
    }

    public static String getUserId() {
        return userId;
    }

}
