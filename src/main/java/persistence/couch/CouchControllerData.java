package persistence.couch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controller.UIController;
import controller.impl.ActorController;
import org.ektorp.support.CouchDbDocument;
import persistence.IControllerData;

/**
 * If everything works right this class was
 * created by Konraifen88 on 20.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class CouchControllerData extends CouchDbDocument implements IControllerData {

    static final String COUCH_DB_CONTROLLER_TYPE = "controller";
    @JsonProperty("_id")
    private String id;

    @JsonSerialize(as = ActorController.class)
    @JsonDeserialize(as = ActorController.class)
    private UIController controller;

    private String playerName;

    @JsonProperty("type")
    private String type;

    CouchControllerData() {
        type = COUCH_DB_CONTROLLER_TYPE;
    }

    CouchControllerData(UIController controller) {
        this.controller = controller;
        this.playerName = getPlayerNameFromController(controller);
        this.type = COUCH_DB_CONTROLLER_TYPE;
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
        return controller.getPlayersArray()[0].getPlayerName();
    }

    public String getPlayerName() {
        return playerName;
    }

    void setPlayerName(String playerName) {
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

    @SuppressWarnings("unused") //needed for CouchDB to find entries
    public String getType() {
        return type;
    }

    @SuppressWarnings("unused") //needed for CouchDB to find entries
    public void setType(String type) {
        this.type = type;
    }
}
