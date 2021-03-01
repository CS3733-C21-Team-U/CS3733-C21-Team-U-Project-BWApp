package com.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.cdimascio.dotenv.Dotenv;

public class SendSms {

    static Dotenv dotenv = Dotenv.load();

    public static final String ACCOUNT_SID = dotenv.get("ACCOUNT_SID");
    public static final String AUTH_TOKEN = dotenv.get("AUTH_TOKEN");
    public static final String twilioNumber = dotenv.get("TWILIO_NUMBER");
    private String phoneNumber;
    private String body;

    public SendSms(String phoneNumber, String body){
        this.phoneNumber = "+19148394600";
        this.body = body;
        System.out.println("Body " + this.body);
        sendMessage();
    }
    public static void main(String[] args) {
        //Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendMessage(){
        if (body == null){
            System.out.println("Body was null here");
            return;
        }
        Message message = Message
                .creator(new PhoneNumber(this.phoneNumber), // to
                        new PhoneNumber(twilioNumber), // from
                        this.body)
                .create();
    }
}
