package persistence;

import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.UIController;
import model.player.IPlayer;
import persistence.hibernate.HibernateDAO;
import phasex.CouchedbModule;
import phasex.HibernateModule;
import phasex.PhaseXModule;

/**
 * Created by tabuechn on 07.05.2016.
 */
public class DatabaseAccess {

    private SaveSinglePlayerDAO saveSinglePlayerDAO;
    private Injector in;

    public DatabaseAccess(DBEnum type) {
        if(type == DBEnum.HIBERNATE) {
            in = Guice.createInjector(new HibernateModule());
        } else {
            in = Guice.createInjector(new CouchedbModule());
        }
        saveSinglePlayerDAO = in.getInstance(SaveSinglePlayerDAO.class);
    }

    public void saveGame(UIController controller) {
        saveSinglePlayerDAO.saveGame(controller);
    }

    public UIController loadGame(IPlayer player) {
        return saveSinglePlayerDAO.loadGame(player);
    }
}
