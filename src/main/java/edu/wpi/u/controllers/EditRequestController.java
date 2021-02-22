package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.models.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Date;

public class EditRequestController {

    @FXML TextField editTitleField;

    @FXML TextArea editDescripArea;

    @FXML TextField editLocField;

    @FXML TextField editCreaDateField;

    @FXML TextField editCompDateField;

    @FXML TextArea editPeopleArea;

    @FXML Label editCreaDateErrorLabel;

    @FXML Label editCompDateErrorLabel;


    public void initialize(){
        Request request = makeDummyRequest();
        editTitleField.setText(request.getTitle());
        editDescripArea.setText(request.getDescription());
        editLocField.setText(request.getLocation());
        editCreaDateField.setText(request.getDateCreated().toString());
        editCompDateField.setText(request.getDateCompleted().toString());
    }

    public void handleCancel() {

        App.rightDrawerRoot.set("../views/ViewRequest.fxml");
    }

    public void handleSaveRequest() {
        if(!(checkCreaDate() && checkCompDate())){
            return;
        }
        //Replace with getter of request or set request as class level variable.
        Request request = makeDummyRequest();

        request.setTitle(editTitleField.getText());
        request.setDescription(editDescripArea.getText());
        request.setLocation(editLocField.getText());

        //Replace date with calendars to remove deprecated code.
        String newCreaDateS = editCreaDateField.getText();
        String newCompDateS = editCompDateField.getText();
        request.setDateCreated(new Date(Integer.parseInt(newCreaDateS.substring(6,10)),
                Integer.parseInt(newCreaDateS.substring(3,5)),
                Integer.parseInt(newCreaDateS.substring(0,2))));
        request.setDateCompleted(new Date(Integer.parseInt(newCompDateS.substring(6,10)),
                Integer.parseInt(newCompDateS.substring(3,5)),
                Integer.parseInt(newCompDateS.substring(0,2))));
        //save added people
    }

    private boolean checkCreaDate(){
        String creaDate = editCreaDateField.getText();
        if(isValidDate(creaDate)){
            editCreaDateErrorLabel.setText("Date is invalid.");
            return false;
        }
        return true;
    }

    private boolean checkCompDate(){
        String compDate = editCompDateField.getText();
        if(!isValidDate(compDate)){
            editCompDateErrorLabel.setText("Date is invalid.");
            return false;
        }
        return true;
    }

    private Request makeDummyRequest(){
        Request request = new Request("Bobby", "Bobby wants a good steak.", "King of the Hill");
        request.setDateCompleted(new Date());
        request.setDateCreated(new Date());
        ArrayList<String> staff = new ArrayList<String>();
        staff.add("Hank");
        staff.add("Peggy");
        request.setStaff(staff);
        return request;
    }

    private boolean isValidDate(String dateS){
        if(dateS.length() != 10){
            return false;
        }
        try{
            int day = Integer.parseInt(dateS.substring(0, 2));
            int month = Integer.parseInt(dateS.substring(3, 5));
            int year = Integer.parseInt(dateS.substring(6, 10));
            if(month > 12 || month < 1){
                return false;
            }
            if(month != 2){
                if(day < 1 || day > 31){
                    return false;
                }
            } else{
                if(isLeapYear(year)){
                    if(day < 1 || day > 29){
                        return false;
                    }
                }else{
                    if(day < 1 || day > 28){
                        return false;
                    }
                }
            }
        }catch (Error e){
            return false;
        }
        return true;
    }

    private boolean isLeapYear(int year){
        if (year % 4 == 0){
            if(year % 100 == 0){
                if(year % 400 == 0){
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }
}
