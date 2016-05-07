package persistence;

import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.UIController;
import model.player.IPlayer;
import persistence.hibernate.HibernateDAO;
import phasex.HibernateModule;
import phasex.PhaseXModule;

/**
 * Created by tabuechn on 07.05.2016.
 */
public class DatabaseAccess {

    private SaveSinglePlayerDAO saveSinglePlayerDAO;
    private Injector in;

    public DatabaseAccess() {
        in = Guice.createInjector(new HibernateModule());
        saveSinglePlayerDAO = in.getInstance(SaveSinglePlayerDAO.class);
    }

    public void saveGame(UIController controller) {
        saveSinglePlayerDAO.saveGame(controller);
    }

    public UIController loadGame(IPlayer player) {
        return saveSinglePlayerDAO.loadGame(player);
    }
}
