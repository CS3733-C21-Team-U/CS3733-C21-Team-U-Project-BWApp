package edu.wpi.u.rbe;

import com.fazecast.jSerialComm.*;
import javax.swing.*;


public class test {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        JSlider silder = new JSlider();
        silder.setMaximum(1023);
        window.add(silder);
        window.pack();
        window.setVisible(true);
    }
}
