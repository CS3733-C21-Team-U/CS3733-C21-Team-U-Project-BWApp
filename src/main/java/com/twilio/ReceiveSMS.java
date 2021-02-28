package com.twilio;

import static spark.Spark.post;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;


public class ReceiveSMS {

    public static void main(String[] args) {
        post("/receive-sms", (req,res) -> {
            Message sms = new Message.Builder("The British are coming !")
                    .build();

            MessagingResponse response = new MessagingResponse.Builder()
                    .message(sms).build();

            try{
                System.out.println(response.toXml());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return response.toXml();
        });
    }

}
