package edu.wpi.u.web;

import edu.wpi.u.requests.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.sql.Timestamp;

public class EmailService {

    private static Dotenv dotenv = Dotenv.load();
    public static void main(String[] args) {
        //sendMail("nev.ingram30@gmail.com","The return of testy westy");
        SpecificRequest s = new RequestFactory().makeRequest("Food");
        //String requestID, Timestamp dateNeeded, ArrayList<String> locations, ArrayList<String> assignees, Comment comment
        //String title, String description, String author, CommentType type, Timestamp timestamp
        Comment c = new Comment("title", "desc", "author", CommentType.PRIMARY, new Timestamp(System.currentTimeMillis()));
        s.setRequest(new Request("the id", new Timestamp(System.currentTimeMillis()), null, null, c));
        //sendMail("nev.ingram30@gmail.com",s);
    }
    public static void sendMail(String to, String body){
        Email email = EmailBuilder.startingBlank()
                .from("Brigham & Women's Service Request Notifier", "cs3733teamu@gmail.com")
                .to("", to)
                .withSubject("New Service Request")
                .withPlainText(body)
                .buildEmail();
        MailerBuilder
                .withSMTPServer("smtp.gmail.com", 25, "cs3733teamu@gmail.com", dotenv.get("EMAIL_PASSWORD"))
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(email);
    }

    public static void sendMail(String to, SpecificRequest specificRequest){
        Email email = EmailBuilder.startingBlank()
                .from("Brigham & Women's Service Request Notifier", "cs3733teamu@gmail.com")
                .to("", to)
                .withSubject("New "+ specificRequest.getType() +" Request")
                .withPlainText(
                                "New Service Request for " + specificRequest.getGenericRequest().getAuthor() + "\n"+
                                "Date Needed by : " + specificRequest.getGenericRequest().getDateNeeded() + "\n"+
                                "Title : " + specificRequest.getGenericRequest().getTitle() + "\n"+
                                "Description : \n" + specificRequest.getGenericRequest().getDescription()
                )
                .buildEmail();
        try {
            MailerBuilder
                    .withSMTPServer("smtp.gmail.com", 25, "cs3733teamu@gmail.com", dotenv.get("EMAIL_PASSWORD"))
                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
                    .buildMailer()
                    .sendMail(email);
        }catch (Exception e){
            System.out.println("Email failed to send");
            e.printStackTrace();
        }
    }
}
