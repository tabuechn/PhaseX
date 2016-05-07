package phasex;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import persistence.SaveSinglePlayerDAO;
import persistence.couch.CouchDbDAO;
import persistence.hibernate.HibernateDAO;

/**
 * Created by tabuechn on 07.05.2016.
 */
public class CouchedbModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SaveSinglePlayerDAO.class).to(CouchDbDAO.class).in(Singleton.class);
    }
}
