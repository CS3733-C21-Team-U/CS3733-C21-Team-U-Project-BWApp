package com.twilio;

import io.github.cdimascio.dotenv.Dotenv;
public class TwilioMain {

    static Dotenv dotenv = Dotenv.load();
    public static final String ACCOUNT_SID = dotenv.get("ACCOUNT_SID");
    public static final String AUTH_TOKEN = dotenv.get("AUTH_TOKEN");

    private String phoneNumber;
    private String body;
    private String response;

    public TwilioMain() {
    }

    public TwilioMain(String phoneNumber, String body, String response) {
        this.phoneNumber = phoneNumber;
        this.body = body;
        this.response = response;
        run();
    }

    public void run(){
        SendSms send = new SendSms(this.phoneNumber, this.body);
        ReceiveSms receive = new ReceiveSms(this.response);
    }
}

