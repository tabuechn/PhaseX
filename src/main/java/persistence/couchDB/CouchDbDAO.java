package persistence.couchDB;

import controller.UIController;
import controller.impl.Controller;
import model.player.IPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import persistence.SaveSinglePlayerDAO;

import java.net.MalformedURLException;

/**
 * If everything works right this class was
 * created by Konraifen88 on 21.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CouchDbDAO implements SaveSinglePlayerDAO {

    private CouchDbConnector db;
    private static final Logger LOGGER = LogManager.getLogger(CouchDbDAO.class);

    public CouchDbDAO(){
        HttpClient client;
        try {
            client = new StdHttpClient.Builder().url(
                    "http://lenny2.in.htwg-konstanz.de:5984").build();
            CouchDbInstance dbInstance = new StdCouchDbInstance(client);
            db = dbInstance.createConnector("sudoku_db", true);
            db.createDatabaseIfNotExists();
        } catch (MalformedURLException e) {
            LOGGER.error("Malformed URL", e);
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

    private boolean containsGame(String playerName){
        return db.find(CouchControllerData.class, playerName) != null;
    }
}
