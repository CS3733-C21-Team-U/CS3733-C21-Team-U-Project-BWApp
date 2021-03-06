package edu.wpi.u.requests;

public class RequestFactory {
    public RequestFactory(){}
    public SpecificRequest makeRequest(String type){
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
            case "Religious":
                return new ReligiousRequest();
            case "Computer":
                return new ComputerRequest();
            case "Language":
                return new LanguageRequest();
            case "Gift":
                return new GiftRequest();
            default:
                System.out.println("Type does not exist!");

        }

        return null;
    }
}
