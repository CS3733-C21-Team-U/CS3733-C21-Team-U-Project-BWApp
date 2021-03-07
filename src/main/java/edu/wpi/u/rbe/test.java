package edu.wpi.u.rbe;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.util.Scanner;


public class test {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        JSlider slider = new JSlider();  // depend on what we connect on arduino
        slider.setMaximum(1023);
        window.add(slider);  // add the device
        window.pack();
        window.setVisible(true);
        SerialPort.getCommPorts();

        SerialPort ports[] = SerialPort.getCommPorts();

        System.out.println("Select a port :");
        int i = 1;
        for (SerialPort port : ports) {
            System.out.println(i++ + ", " + port.getSystemPortName());
        }
        Scanner s = new Scanner(System.in);  //conncet board
        int chosenPort = s.nextInt();

        SerialPort port = ports[chosenPort - 1];
        if (port.openPort()) {
            System.out.println("Successfully opened the port");
        } else {
            System.out.println("Fail to open the port");
            return;
        }
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0 ,0); //set timeout  necessary for windows

        Scanner data = new Scanner(port.getInputStream());
        while(data.hasNextLine()){ // wait input from serialport from Arduino
            //System.out.println(data.nextLine());
            int number =0; //init the number
            try{Integer.parseInt(data.nextLine());}
            catch(Exception e){}
            slider.setValue(number);


        }

    }
}