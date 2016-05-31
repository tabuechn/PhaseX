package controller.statusMessage.impl;

import controller.statusMessage.IStatusMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
public class StatusMessageTest {
    private IStatusMessage statusMessage;
    private String statusString;

    @Before
    public void setUp() {
        statusString = "A_STATUS_MSG";
        statusMessage = new StatusMessage();
    }

    @Test
    public void testStatusMessage() {
        assertEquals("", statusMessage.getStatusMessage());
        statusMessage.setStatusMessage(statusString);
        assertEquals(statusString, statusMessage.getStatusMessage());
    }
}
