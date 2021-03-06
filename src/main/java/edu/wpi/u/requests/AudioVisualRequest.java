package edu.wpi.u.requests;

import java.io.Serializable;
import java.util.LinkedList;

public class AudioVisualRequest extends SpecificRequest {

    public AudioVisualRequest() {

    }
    @Override
    public String getType() {
        return "AudioVisual";
    }

    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"isAudio", "name"};
        return res;
    }

}
