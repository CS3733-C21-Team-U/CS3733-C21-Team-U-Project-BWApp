package com.twilio;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import io.github.cdimascio.dotenv.Dotenv;
import static spark.Spark.*;

public class ReceiveSms {
    static Dotenv env = Dotenv.load();
    public static final String ACCOUNT_SID = env.get("ACCOUNT_SID");
    public static final String AUTH_TOKEN = env.get("AUTH_TOKEN");

    private String response;
    public ReceiveSms(String response){
        this.response = response;
    }

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void receiveSms(){
        post("/sms", (req, res) -> {
            res.type("application/xml");
            Body body = new Body
                    .Builder(this.response)
                    .build();
            Message sms = new Message
                    .Builder()
                    .body(body)
                    .build();
            MessagingResponse twiml = new MessagingResponse
                    .Builder()
                    .message(sms)
                    .build();
            return twiml.toXml();
        });
    }
}
