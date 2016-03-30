package persistence.hibernate;

import controller.IController;
import model.player.impl.Player;
import persistence.IPhaseXDao;

/**
 * Created by Tarek on 30.03.2016. Be grateful for this superior Code!
 */
public class HibernateDAO implements IPhaseXDao {
    @Override
    public void saveGame(IController controller) {

    }

    @Override
    public boolean isGameExisting(Player p1, Player p2) {
        return false;
    }

    @Override
    public IController loadGame(Player p1, Player p2) {
        return null;
    }

    @Override
    public void deleteGameForPlayers(Player p1, Player p2) {

    }

    @Override
    public void deleteAllGamesForPlayer(Player p1) {

    }
}
