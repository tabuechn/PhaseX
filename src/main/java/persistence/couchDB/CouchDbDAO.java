package persistence.couchDB;

import controller.UIController;
import controller.impl.Controller;
import model.deck.IDeckOfCards;
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
    private CouchDbConnector db;

    public CouchDbDAO() {
        HttpClient client;
        try {
            client = new StdHttpClient.Builder().url(
                    "http://lenny2.in.htwg-konstanz.de:5984").build();
            CouchDbInstance dbInstance = new StdCouchDbInstance(client);
            db = dbInstance.createConnector("a_aphase_x_db_card_test", true);
            db.createDatabaseIfNotExists();
        } catch (MalformedURLException e) {
            LOGGER.error("Cant connect to Couch DB", e);
        }
    }

    @Override
    public void saveGame(UIController controller) {
        CouchControllerData data = new CouchControllerData(controller);
        if (containsGame(data.getPlayerName())) {
            db.update(data);
        } else {
            db.create(data);
        }
    }

    @Override
    public UIController loadGame(IPlayer player) {
        CouchControllerData data = db.find(CouchControllerData.class, player.getPlayerName());
        if (data != null) {
            return data.getController();
        }
        return new Controller();
    }

    public void saveCardToDB(IDeckOfCards cards, String name) {
        CouchCardData data = new CouchCardData();
        data.setCards(cards);
        data.setPlayerName(name);
        if (!containsGame(name)) {
            db.create(data);
        } else {
            CouchCardData old = getCardDataFromDB(name);
            data.setId(old.getId());
            data.setRevision(old.getRevision());
            db.update(data);
        }
    }

    public IDeckOfCards getCardFromDB(String name) {
        return getCardDataFromDB(name).getCards();
    }

    private CouchCardData getCardDataFromDB(String name) {
        ViewQuery query = getUserQuery(name);
        List<CouchCardData> result = db.queryView(query, CouchCardData.class);
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
        return result.getSize() == 1;
    }

    private ViewQuery getUserQuery(String name) {
        return new ViewQuery()
                .allDocs()
                .includeDocs(true)
                .designDocId("_design/card_data")
                .viewName("find_card_by_name")
                .key(name);
    }
}
