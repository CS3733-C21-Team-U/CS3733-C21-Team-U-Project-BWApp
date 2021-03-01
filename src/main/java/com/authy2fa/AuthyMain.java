package com.authy2fa;

import io.github.cdimascio.dotenv.Dotenv;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthyMain extends AbstractHandler {

    static Dotenv dotenv = Dotenv.load();
    public static final String ACCOUNT_SID = dotenv.get("ACCOUNT_SID");
    public static final String AUTH_TOKEN = dotenv.get("AUTH_TOKEN");

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new AuthyMain());

        server.start();
        server.join();
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //String countryCode = request.getParameter("countryCode");
        String phoneNumber = request.getParameter("phoneNumber");
    }
}


//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        Message message = Message.creator(
//                new PhoneNumber("+17742706792"),
//                new PhoneNumber("+16502623516"),
//                "Thanks ;)").create();
//        Verification verification = Verification.creator(
//                dotenv.get("PATH_SERVICE_SID"),
//                "+19148394600",
//                "sms")
//                .create();
//
//        get("/", (req,res) -> "Authentication page");
//
//        post("/authenticate", (req,res) -> {
//            VerificationCheck vc = VerificationCheck.creator(dotenv.get("PATH_SERVICE_SID"), "1234").setTo("+19148394600").create();
//            System.out.println(vc.getStatus());
//            Message sms = new Message.Builder("The British are coming!")
//                    .build();
//            MessagingResponse response = new MessagingResponse.Builder()
//                    .message(sms).build();
//            try{
//                System.out.println(response.toXml());
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//            return response.toXml();
//        });
