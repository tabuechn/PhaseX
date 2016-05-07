package phasex;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import persistence.SaveSinglePlayerDAO;
import persistence.couch.CouchDbDAO;
import persistence.hibernate.HibernateDAO;

/**
 * Created by tabuechn on 07.05.2016.
 */
public class HibernateModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SaveSinglePlayerDAO.class).to(HibernateDAO.class).in(Singleton.class);
    }
}
