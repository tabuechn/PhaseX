package persistence.hibernate;

import controller.IController;
import model.player.IPlayer;
import persistence.IPhaseXDao;

import java.util.List;

/**
 * Created by Tarek on 30.03.2016. Be grateful for this superior Code!
 */
public class HibernateDAO implements IPhaseXDao {
    @Override
    public void saveGame(IController controller) {

    }

    @Override
    public boolean isGameExisting(IPlayer p1, IPlayer p2) {
        return false;
    }

    @Override
    public IController loadGame(IPlayer p1, IPlayer p2) {
        return null;
    }

    @Override
    public void deleteGameForPlayers(IPlayer p1, IPlayer p2) {

    }

    @Override
    public void deleteAllGamesForPlayer(IPlayer p1) {

    }

    @Override
    public List<IController> getAllSavedGamesForPlayer(IPlayer player) {
        return null;
    }
}
