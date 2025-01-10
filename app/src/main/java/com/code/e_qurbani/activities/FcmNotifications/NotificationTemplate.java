package com.code.e_qurbani.activities.FcmNotifications;

public class NotificationTemplate {



    private String token;
    NotificationTemplate(String s)
    {
        token = s;
    }


    public static String message = "{" +
            "  \"to\": \"%s\"," +
            "  \"data\": {" +
            "       \"body\":\"%s\"," +
            "       \"for\":\"%s\"" +
            "       \"screen\":\"%s\"" +
            "       \"forUid\":\"%s\"" +
            "   }" +
            "}";
}