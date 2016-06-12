package controller.message.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import controller.message.IStatusMessage;

/**
 * Created by tabuechn on 25.05.2016.
 */
public class StatusMessage implements IStatusMessage {

    @JsonProperty("statusMessageString")
    private String statusMessageString;

    public StatusMessage() {
        statusMessageString = "";
    }

    @Override
    public String getStatusMessage() {
        return statusMessageString;
    }

    @Override
    public void setStatusMessage(String statusMessage) {
        this.statusMessageString = statusMessage;
    }
}
