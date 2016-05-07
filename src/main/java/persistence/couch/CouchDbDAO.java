package persistence.couch;

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
import persistence.IControllerData;
import persistence.SaveSinglePlayerDAO;

import java.net.MalformedURLException;

/**
 * If everything works right this class was
 * created by Konraifen88 on 21.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CouchDbDAO implements SaveSinglePlayerDAO {

    public static final int DB_TIMEOUT = 60000;
    private static final Logger LOGGER = LogManager.getLogger(CouchDbDAO.class);
    private CouchDbConnector db;
    private PhaseXRepository repo;

    public CouchDbDAO() {
        this(System.getenv("couchDbLink"), System.getenv("couchDbUser"), System.getenv("couchDbPassword"), System.getenv("couchDbDocName"));
    }


    /**
     * Constructor with creating of the connection
     *
     * @param link     link to the db
     * @param userName username for db, set null if not needed
     * @param password password for db, set null if not needed
     */
    private CouchDbDAO(String link, String userName, String password, String designDocName) {
        HttpClient client;
        try {
            if (link == null || userName == null) {
                client = new StdHttpClient.Builder()
                        .url("http://lenny2.in.htwg-konstanz.de:5984")
                        .connectionTimeout(DB_TIMEOUT)
                        .build();
            } else {
                client = new StdHttpClient.Builder()
                        .url(link)
                        .username(userName)
                        .password(password)
                        .socketTimeout(DB_TIMEOUT)
                        .build();
            }
            CouchDbInstance dbInstance = new StdCouchDbInstance(client);
            if (designDocName != null) {
                db = dbInstance.createConnector(designDocName, true);
            } else {
                db = dbInstance.createConnector("phasex_controller_data", true);
            }
            db.createDatabaseIfNotExists();
            repo = new PhaseXRepository(CouchControllerData.class, db, true);
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
        IControllerData data = getControllerDataFromDB(player.getPlayerName());
        if (data != null) {
            return data.getController();
        }
        return new Controller();
    }


    private CouchControllerData getControllerDataFromDB(String name) {
        return repo.findByName(name);
    }

    private boolean containsGame(String playerName) {
        IControllerData result = repo.findByName(playerName);
        return result != null;
    }
}
