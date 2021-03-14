package edu.wpi.u.controllers.robot;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.requests.Comment;
import edu.wpi.u.requests.CommentType;
import edu.wpi.u.requests.SpecificRequest;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;

public class KioskTemperatureScannerController {

    @FXML
    public JFXTextField tempLabel;
    public JFXButton submitTempButton;
    private LinkedList<Integer> rollingAverage = new LinkedList<>();
    int savedTemp;
    SimpleBooleanProperty updateFlag = new SimpleBooleanProperty(true);
    public void initialize(){
        submitTempButton.setDisable(true);
        SerialPort comPort = SerialPort.getCommPort("COM5");
        comPort.setBaudRate(9600);
        comPort.openPort();
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[comPort.bytesAvailable()];
                comPort.readBytes(newData, newData.length);
                String CelsiusTemp = String.valueOf(newData[0]);
                int celsiusTemp = Integer.valueOf(CelsiusTemp);
                int fahrenheitTemp = (int)Math.round((Integer.valueOf(celsiusTemp) * 1.8) + 32);
                savedTemp = fahrenheitTemp;
                if (rollingAverage.size() >= 10) {
                    rollingAverage.removeFirst();
                }
                rollingAverage.addLast(savedTemp);
                tempLabel.setText(String.valueOf(averageLL(rollingAverage)));
                if(checkAverage()){
                    submitTempButton.setDisable(false);
                }else {
                    submitTempButton.setDisable(true);
                }
            }
        });
    }

    private boolean checkAverage(){
        for(int curInt: rollingAverage){
            if(curInt < 90){
                return false;
            }
        }
        return true;
    }

    private int averageLL(LinkedList<Integer> input){
        int sum = 0;
        int count = 0;
        for(int curInt : input){
            sum += curInt;
            count ++;
        }
        return (sum/count);
    }

    public void handelTakeTempButton() {
        ArrayList<String> specificData = App.requestService.curCovidRequest.getSpecificData();
        specificData.set(1,String.valueOf(averageLL(rollingAverage)));
        App.requestService.curCovidRequest.updateRequest(App.requestService.curCovidRequest.getGenericRequest().getTitle(),App.requestService.curCovidRequest.getGenericRequest().getDescription(),App.requestService.curCovidRequest.getGenericRequest().getDateNeeded(),App.requestService.curCovidRequest.getGenericRequest().getLocations(),App.requestService.curCovidRequest.getGenericRequest().getAssignees(),specificData);
        App.requestService.updateRequest(App.requestService.curCovidRequest);
        SpecificRequest curRequest = null;
        for(SpecificRequest curReq: App.requestService.getRequests()){
            if(curReq.getGenericRequest().getCreator().equals(App.userService.getActiveUser().getUserName()) && curReq.getType().equals("CovidSurvey")){
                curRequest = curReq;
            }
        }
        if(curRequest != null){
            App.requestService.resolveRequest(curRequest, new Comment("Resolve","CLOSED",App.userService.getActiveUser().getUserName(), CommentType.RESOLVE));
        }else{
            //be mad
        }
        String fxmlLocation = "/edu/wpi/u/views/robot/KioskLastScreen.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlLocation));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }
}
