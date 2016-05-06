package phasex;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import controller.IController;
import controller.UIController;
import controller.impl.Controller;
import persistence.SaveSinglePlayerDAO;
import persistence.couchdb.CouchDbDAO;


/**
 * Created by tabuechn on 07.10.2015. Be grateful for this code! ^(�.�)^
 */
class PhaseXModule extends AbstractModule {

    @Override
    public void configure() {
        bind(IController.class).to(Controller.class).in(Singleton.class);
        bind(UIController.class).to(Controller.class).in(Singleton.class);
        bind(SaveSinglePlayerDAO.class).to(CouchDbDAO.class).in(Singleton.class);
    }



}
