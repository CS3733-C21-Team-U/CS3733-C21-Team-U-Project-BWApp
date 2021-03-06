package edu.wpi.u.web;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.wpi.u.requests.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Timestamp;
import java.util.Objects;

public class TextingService {

    static Dotenv dotenv = Dotenv.load();

    public TextingService(){
        Twilio.init(Objects.requireNonNull(dotenv.get("ACCOUNT_SID")), Objects.requireNonNull(dotenv.get("AUTH_TOKEN")));
    }

    public static void main(String[] args) {
//        new TextingService();
//        SpecificRequest s = new RequestFactory().makeRequest("Food");
//        Comment c = new Comment("title", "desc", "author", CommentType.PRIMARY, new Timestamp(System.currentTimeMillis()));
//        s.setRequest(new Request("the id", new Timestamp(System.currentTimeMillis()), null, null, c));
//        sendText("+17813155706", s);
//        sendText("+19148394600", s);
    }

    /**
     * Sends a text to the given number
     * @param to the to phone number
     * @param body the message body
     */
    public void sendText(String to, String body){
        Message message = Message
                .creator(new PhoneNumber(to),
                        new PhoneNumber(Objects.requireNonNull(dotenv.get("TWILIO_NUMBER"))),
                        body)
                .create();
    }

    /**
     * Sends a text to the given number with the specific request
     * @param to the to phone number
     * @param specificRequest the request
     */
    public void sendText(String to, SpecificRequest specificRequest){
        System.out.println("Sending text to " +to);
        Message message = Message
                .creator(new PhoneNumber("+1"+to),
                        new PhoneNumber(Objects.requireNonNull(dotenv.get("TWILIO_NUMBER"))),
                        "New "+ specificRequest.getType() +" Request from " + specificRequest.getGenericRequest().getAuthor()+
                            "\n\nTitle: " + specificRequest.getGenericRequest().getTitle() + "\n"+
                            "Requested Completion Date: " + specificRequest.getGenericRequest().getDateNeeded() + "\n"+
                            "Details: " + specificRequest.getGenericRequest().getDescription() +"\n\n"+
                            "This notification is from the Brigham & Women's Faulkner Hospital's Service Request system.\n"+
                            "If you would like to turn these notifications off, please log into the application and change your preferences.")
                .create();
    }
}
