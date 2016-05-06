package persistence.couchDB;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;

import java.util.List;

/**
 * If everything works right this class was
 * created by konraifen88 on 05.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
@View(name = "all", map = "function(doc) { if (doc.type == '" + CouchControllerData.COUCH_DB_CONTROLLE_TYPE + "' ) emit( null, doc._id )}")
public class PhaseXRepository extends CouchDbRepositorySupport<CouchControllerData> {


    protected PhaseXRepository(Class<CouchControllerData> type, CouchDbConnector db, boolean createIfNotExists) {
        super(type, db, createIfNotExists);
        initStandardDesignDocument();
    }

    @View(name = "by_name", map = "function(doc) { emit(doc.playerName, doc); }")
    public CouchControllerData findByName(String name) {
        List<CouchControllerData> result = queryView("by_name", name);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
