package persistence.db4o;

import controller.IController;
import model.player.impl.Player;
import persistence.IPhaseXDao;

/**
 * If everything works right this class was
 * created by Konraifen88 on 23.03.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PhaseXDb4oDao implements IPhaseXDao {
    @Override
    public void saveGame(IController controller) {
        //TODO Implement
    }

    @Override
    public boolean isGameExisting(Player p1, Player p2) {
        //TODO Implement
        return false;
    }

    @Override
    public IController loadGame(Player p1, Player p2) {
        //TODO Implement
        return null;
    }

    @Override
    public void deleteGameForPlayers(Player p1, Player p2) {
        //TODO Implement
    }

    @Override
    public void deleteAllGamesForPlayer(Player p1) {
        //TODO Implement
    }
}
