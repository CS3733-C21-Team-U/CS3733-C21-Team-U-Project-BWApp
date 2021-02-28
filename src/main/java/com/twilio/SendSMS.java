package com.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSMS {

    public static final String ACCOUNT_SID = "ACd8fdb8241630eb3c81dbb559d58426eb";
    public static final String AUTH_TOKEN = "b1812fbacb75c0beb9534d7ad56d986a";


    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+19148394600"),
                new PhoneNumber("+16502623516"),
                "Test message").create();

        System.out.println(message.getSid());
    }
}
