package edu.wpi.u.requests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RequestFactory {
    public RequestFactory(){}
    public IRequest makeRequest(String type){
        switch (type){
            case "Maintenance":
                return new MaintenanceRequest();
            case "Laundry":
                return new LaundryRequest();
            case "Security":
                return new SecurityRequest();
            case "Sanitation":
                return new SanitationRequest();
            case "AudioVisual":
                return new AudioVisualRequest();
            case "Floral":
                return new FloralRequest();
            case "Medical":
                return new MedicalRequest();
            default:
                System.out.println("Type does not exist!");

        }

        return null;
    }
}
