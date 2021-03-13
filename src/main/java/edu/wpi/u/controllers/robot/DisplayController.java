package edu.wpi.u.controllers.robot;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class DisplayController extends Application {//TODO remove extends Application
    @FXML
    TextField celsiusLabel, fahrenheitLabel;

    final static private String MAIN_FXML = "edu/wpi/u/views/robot/Display.fxml";

    public static void main(String Args[]){
        launch();
    }

    @Override
    public void start(Stage primaryStage){
        Parent root;
        try {
            FXMLLoader displayTemps = new FXMLLoader(getClass().getResource("edu/wpi/u/views/robot/Display.fxml"));
            root = displayTemps.load();
        } catch (IOException e){
            System.err.println("Failed to load main FXML file!");
            e.printStackTrace();
            return;
        }

        primaryStage.setTitle("Temperature Tester");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initialize(){
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
                celsiusLabel.setText(CelsiusTemp);
                String fahrenheitTemp = String.valueOf(Math.round((Integer.valueOf(CelsiusTemp) * 1.8) + 32));
                fahrenheitLabel.setText(fahrenheitTemp);
            }
        });
    }


    }
