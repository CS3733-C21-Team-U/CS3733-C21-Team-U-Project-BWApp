package edu.wpi.u.web;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;

public class TextingService {

    static Dotenv dotenv = Dotenv.load();

    public TextingService(){
        Twilio.init(Objects.requireNonNull(dotenv.get("ACCOUNT_SID")), Objects.requireNonNull(dotenv.get("AUTH_TOKEN")));
    }

    public static void main(String[] args) {
        //new TextingService();
        //sendText("+19148394600", "Hello there test");
    }

    public void sendText(String to, String body){
        Message message = Message
                .creator(new PhoneNumber(to),
                        new PhoneNumber(Objects.requireNonNull(dotenv.get("TWILIO_NUMBER"))),
                        body)
                .create();
    }
}
