package edu.wpi.u.rbe;

import com.fazecast.jSerialComm.*;

import javax.swing.*;



public class test {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        JSlider slider = new JSlider();
        slider.setMaximum(1023);
        window.add(slider);
        window.pack();
        window.setVisible(true);
        SerialPort.getCommPorts();
    }
}