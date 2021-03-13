package edu.wpi.u.controllers.robot;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class KioskTemperatureScannerController {

    @FXML
    Label tempLabel;
    private LinkedList<Integer> rollingAverage = new LinkedList<>();

    public void initialize(){
        rollingAverage = new LinkedList<>();
        com.fazecast.jSerialComm.SerialPort comPort = com.fazecast.jSerialComm.SerialPort.getCommPort("COM5");
        comPort.openPort();
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[comPort.bytesAvailable()];
                String CelsiusTemp = String.valueOf(newData[0]);
                int fahrenheitTemp = (int)Math.round((Integer.valueOf(CelsiusTemp) * 1.8) + 32);
                if(rollingAverage.size() < 10){
                    rollingAverage.addFirst(fahrenheitTemp);
                }else {
                    rollingAverage.addFirst(fahrenheitTemp);
                    rollingAverage.removeLast();
                }
                String displayTemp = String.valueOf(averageLL(rollingAverage));
                tempLabel.setText(displayTemp);
            }
        });
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
    }
}
