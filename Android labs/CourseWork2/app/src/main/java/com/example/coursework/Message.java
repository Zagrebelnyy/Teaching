package com.example.coursework;

public class Message {
    private String textMessage;
    private String authorMessage;
    private String timeMessage;
    public Message(String text, String author, String time){
        this.textMessage = text;
        this.authorMessage = author;
        this.timeMessage = time;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getAuthorMessage() {
        return authorMessage;
    }

    public String getTimeMessage(){
        return this.timeMessage;
    }

}
