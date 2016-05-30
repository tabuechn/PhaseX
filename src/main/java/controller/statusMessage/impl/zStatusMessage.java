package controller.statusMessage.impl;

import controller.statusMessage.IStatusMessage;

/**
 * Created by tabuechn on 25.05.2016.
 */
public class zStatusMessage implements IStatusMessage {

    private String statusMessage;

    public zStatusMessage() {
        statusMessage = "";
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
