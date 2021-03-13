package edu.wpi.u.controllers.robot;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
        //move to next and start screen cleaning robot
    }
}
