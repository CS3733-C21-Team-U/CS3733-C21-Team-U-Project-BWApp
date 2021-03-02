package com.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.cdimascio.dotenv.Dotenv;

public class SendSMS {
    static Dotenv env = Dotenv.load();
    public static final String ACCOUNT_SID = env.get("ACCOUNT_SID");
    public static final String AUTH_TOKEN = env.get("AUTH_TOKEN");

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+19148394600"),
                new PhoneNumber("+16502623516"),
                "Tyler is a cool dude").create();

        System.out.println(message.getSid());
    }
}
