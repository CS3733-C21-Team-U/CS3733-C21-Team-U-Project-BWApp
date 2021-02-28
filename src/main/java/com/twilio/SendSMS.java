package com.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSMS {

    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";


    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+19148394600"),
                new PhoneNumber("+16502623516"),
                "Test message").create();

        System.out.println(message.getSid());
    }
}
