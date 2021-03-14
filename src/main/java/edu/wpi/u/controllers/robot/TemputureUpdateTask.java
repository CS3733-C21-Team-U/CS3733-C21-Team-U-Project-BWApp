package edu.wpi.u.controllers.robot;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;

import java.util.TimerTask;

public class TemputureUpdateTask extends TimerTask {

    SimpleBooleanProperty flag;

    public TemputureUpdateTask(SimpleBooleanProperty flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        if(flag != null){
            flag.set(!flag.get());
        }
    }
}
