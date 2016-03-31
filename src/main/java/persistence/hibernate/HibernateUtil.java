package persistence.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Created by Tarek on 30.03.2016. Be grateful for this superior Code!
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        final AnnotationConfiguration cfg = new
                AnnotationConfiguration();
        cfg.configure(HibernateUtil.class.getResource("hibernate.cfg.xml"));
        sessionFactory = cfg.buildSessionFactory();
    }

    private HibernateUtil() {
    }

    public static SessionFactory getInstance() {
        return sessionFactory;
    }
}
