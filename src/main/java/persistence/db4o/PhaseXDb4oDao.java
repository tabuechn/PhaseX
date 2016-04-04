package persistence.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import controller.IController;
import model.player.IPlayer;
import persistence.IPhaseXDao;

import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 23.03.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PhaseXDb4oDao implements IPhaseXDao {

    private ObjectContainer db;

    public PhaseXDb4oDao() {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "PhaseX");
    }

    @Override
    public void saveGame(IController controller) {
        db.store(controller);
    }

    @Override
    public boolean isGameExisting(IPlayer p1, IPlayer p2) {
        List<IController> gameList = db.query(new Predicate<IController>() {
            @Override
            public boolean match(IController controller) {
                return false;
            }
        });
        return !gameList.isEmpty();
    }

    @Override
    public IController loadGame(IPlayer p1, IPlayer p2) {
        //TODO Implement
        return null;
    }

    @Override
    public void deleteGameForPlayers(IPlayer p1, IPlayer p2) {
        //TODO Implement
    }

    @Override
    public void deleteAllGamesForPlayer(IPlayer p1) {
        //TODO Implement
    }

    @Override
    public List<IController> getAllSavedGamesForPlayer(IPlayer player) {
        return db.query(new Predicate<IController>() {
            @Override
            public boolean match(IController controller) {
                return controller.getCurrentPlayer().equals(player) || controller.getOpponentPlayer().equals(player);
            }
        });
    }
}
