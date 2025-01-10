package com.code.e_qurbani.chat;

import java.util.Date;

public class ChatMessage {
    private String text;

    private String senderEmail;

    private String receiverEmail;

    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT="bot";

    private String message;

    String sentBy;






    private String currentUserType;

    public ChatMessage() {
        // Default constructor required for calls to DataSnapshot.getValue(ChatMessage.class)
    }

    public ChatMessage(String text, String senderEmail, String receiverEmail) {
        this.text = text;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;

    }

    public ChatMessage(String text, String sentBy) {
        this.text = text;
        this.sentBy = sentBy;
    }

    public String getSenderEmail() {
        return senderEmail;
    }




    public String getReceiverEmail() {
        return receiverEmail;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getSentBy() {
        return sentBy;
    }
    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}