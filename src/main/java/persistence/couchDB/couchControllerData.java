package persistence.couchDB;

import controller.UIController;
import org.ektorp.support.CouchDbDocument;
import persistence.IControllerData;

/**
 * If everything works right this class was
 * created by Konraifen88 on 20.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
class CouchControllerData extends CouchDbDocument implements IControllerData {

    private UIController controller;

    private String playerName;

    public CouchControllerData() {
        //Do Nothing
    }

    CouchControllerData(UIController controller) {
        this.controller = controller;
        playerName = getPlayerNameFromController(controller);
    }

    @Override
    public UIController getController() {
        return controller;
    }

    public void setController(UIController controller) {
        this.controller = controller;
        playerName = getPlayerNameFromController(controller);
    }

    private String getPlayerNameFromController(UIController controller) {
        return controller.getPlayers()[0].getPlayerName();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
