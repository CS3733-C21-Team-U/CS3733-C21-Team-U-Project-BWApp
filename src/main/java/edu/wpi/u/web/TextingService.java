package edu.wpi.u.web;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.wpi.u.requests.SpecificRequest;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;

public class TextingService {

    static Dotenv dotenv = Dotenv.load();

    public TextingService(){
        Twilio.init(Objects.requireNonNull(dotenv.get("ACCOUNT_SID")), Objects.requireNonNull(dotenv.get("AUTH_TOKEN")));
    }

    public static void main(String[] args) {

    }

    public void sendText(String to, String body){
        Message message = Message
                .creator(new PhoneNumber(to),
                        new PhoneNumber(Objects.requireNonNull(dotenv.get("TWILIO_NUMBER"))),
                        body)
                .create();
    }
    public void sendText(String to, SpecificRequest specificRequest){
        Message message = Message
                .creator(new PhoneNumber(to),
                        new PhoneNumber(Objects.requireNonNull(dotenv.get("TWILIO_NUMBER"))),
                        "New " + specificRequest.getType() + " request" +
                        "Created by : " + specificRequest.getGenericRequest().getAuthor() +
                        "Date Needed : " + specificRequest.getGenericRequest().getDateNeeded() +
                        "Title : " + specificRequest.getGenericRequest().getTitle() +
                        "Desscription : " + specificRequest.getGenericRequest().getDescription())
                .create();
    }
}
