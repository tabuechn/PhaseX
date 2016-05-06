package persistence.couchDB;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controller.UIController;
import controller.impl.Controller;
import org.ektorp.support.CouchDbDocument;
import persistence.IControllerData;

/**
 * If everything works right this class was
 * created by Konraifen88 on 20.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
class CouchControllerData extends CouchDbDocument implements IControllerData {

    protected static final String COUCH_DB_CONTROLLE_TYPE = "controller";
    @JsonProperty("_id")
    private String id;

    @JsonSerialize(as = Controller.class)
    @JsonDeserialize(as = Controller.class)
    private UIController controller;

    private String playerName;

    @JsonProperty("type")
    private String type;

    public CouchControllerData() {
        type = COUCH_DB_CONTROLLE_TYPE;
    }

    CouchControllerData(UIController controller) {
        this.controller = controller;
        this.playerName = getPlayerNameFromController(controller);
        this.type = COUCH_DB_CONTROLLE_TYPE;
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
