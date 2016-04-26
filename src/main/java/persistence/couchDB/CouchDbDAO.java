package persistence.couchDB;

import controller.UIController;
import controller.impl.Controller;
import model.card.ICard;
import model.player.IPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import persistence.SaveSinglePlayerDAO;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 21.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CouchDbDAO implements SaveSinglePlayerDAO {

    private static final Logger LOGGER = LogManager.getLogger(CouchDbDAO.class);
    private CouchDbConnector db;

    public CouchDbDAO(){
        HttpClient client;
        try {
            client = new StdHttpClient.Builder().url(
                    "http://lenny2.in.htwg-konstanz.de:5984").build();
            CouchDbInstance dbInstance = new StdCouchDbInstance(client);
            db = dbInstance.createConnector("phase_x_db_card_test", true);
            db.createDatabaseIfNotExists();
        } catch (MalformedURLException e) {
            LOGGER.error("Cant connect to Couch DB", e);
        }
    }

    @Override
    public void saveGame(UIController controller) {
        CouchControllerData data = new CouchControllerData(controller);
        if (containsGame(data.getPlayerName())){
            db.update(data);
        } else {
            db.create(data);
        }
    }

    @Override
    public UIController loadGame(IPlayer player) {
        CouchControllerData data = db.find(CouchControllerData.class, player.getPlayerName());
        if (data != null){
            return data.getController();
        }
        return new Controller();
    }

    public void saveDeck(UIController controller) {
        CouchCardData data = new CouchCardData();
        data.setPlayerName(controller.getPlayers()[0].getPlayerName());
        data.setDeck(controller.getCurrentPlayersHand().get(0));
        String id = controller.getPlayers()[0].getPlayerName();
        if (containsGame(id)) {
            data.setId(id);
            db.update(data);
        } else {
            db.create(data);
        }
    }


    public ICard findCardByUser(IPlayer player) {
        List<CouchCardData> users = new ArrayList<>();

        ViewQuery query = new ViewQuery().allDocs();
        ViewResult vr = db.queryView(query);

        return getUserById(vr.getRows().get(0).getId()).getCard();
    }

    public CouchCardData getUserById(String id) {
        CouchCardData user = db.find(CouchCardData.class, id);
        if (user == null) {
            return null;
        }
        return user;
    }

    private boolean containsGame(String playerName) {
        return db.find(CouchCardData.class, playerName) != null;
    }

}
