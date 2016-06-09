package controller.statusmessage.impl;

import controller.statusmessage.IStatusMessage;

/**
 * Created by tabuechn on 25.05.2016.
 */
public class StatusMessage implements IStatusMessage {

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
