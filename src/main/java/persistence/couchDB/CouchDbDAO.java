package persistence.couchDB;

import controller.UIController;
import controller.impl.Controller;
import model.player.IPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ektorp.*;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import persistence.SaveSinglePlayerDAO;

import java.net.MalformedURLException;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 21.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CouchDbDAO implements SaveSinglePlayerDAO {

    private static final Logger LOGGER = LogManager.getLogger(CouchDbDAO.class);
    private static final String CONTROLLER_FUNCTION = "function(doc) {emit(doc.playerName, doc);}";
    private static final String CONTROLLER_DOC_ID = "find_player_by_name";
    private static final String CONTROLLER_DESIGN_DOC_ID = "_design/player_data";
    private CouchDbConnector db;

    public CouchDbDAO() {
        HttpClient client;
        try {
            client = new StdHttpClient.Builder().url(
                    "http://lenny2.in.htwg-konstanz.de:5984").build();
            CouchDbInstance dbInstance = new StdCouchDbInstance(client);
            db = dbInstance.createConnector("a_aphase_x_db_card_test", true);
            db.createDatabaseIfNotExists();
//            db.callUpdateHandler(CONTROLLER_DESIGN_DOC_ID, CONTROLLER_FUNCTION, CONTROLLER_DOC_ID);
        } catch (MalformedURLException e) {
            LOGGER.error("Cant connect to Couch DB", e);
        }
    }

    @Override
    public void saveGame(UIController controller) {
        CouchControllerData data = new CouchControllerData(controller);
        if (containsGame(data.getPlayerName())) {
            CouchControllerData old = getControllerDataFromDB(data.getPlayerName());
            data.setRevision(old.getRevision());
            data.setId(old.getId());
            db.update(data);
        } else {
            db.create(data);
        }
    }

    @Override
    public UIController loadGame(IPlayer player) {
        CouchControllerData data = getControllerDataFromDB(player.getPlayerName());
        if (data != null) {
            return data.getController();
        }
        return new Controller();
    }


    private CouchControllerData getControllerDataFromDB(String name) {
        ViewQuery query = getUserQuery(name);
        List<CouchControllerData> result = db.queryView(query, CouchControllerData.class);
        return result.get(0);
    }

    private boolean containsGame(String playerName) {
        ViewQuery query = getUserQuery(playerName);
        ViewResult result;
        try {
            result = db.queryView(query);
        } catch (DocumentNotFoundException e) {
            return false;
        }
        return !result.isEmpty();
    }

    private ViewQuery getUserQuery(String name) {
        return new ViewQuery()
                .allDocs()
                .includeDocs(true)
                .designDocId(CONTROLLER_DESIGN_DOC_ID)
                .viewName(CONTROLLER_DOC_ID)
                .key(name);
    }
}
