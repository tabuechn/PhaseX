package persistence;

import org.hibernate.*;
import org.hibernate.mapping.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.hibernate.HibernateUtil;
import persistence.hibernate.PersistentTest;



import static org.junit.Assert.*;

/**
 * Created by tabuechn on 31.03.2016.
 */
public class IPhaseXDaoTest {



    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSaveGame() throws Exception {

        PersistentTest test = new PersistentTest();
        test.setTestString("this is a testString");
        Transaction trans = null;
        Session session = HibernateUtil.getInstance().getCurrentSession();
        trans = session.beginTransaction();
        session.save(test);
        trans.commit();
        /*
        try(Session session = HibernateUtil.getInstance().getCurrentSession()) {
            trans = session.beginTransaction();
            session.save(test);
            trans.commit();
        } catch (HibernateException ex) {
            if(trans != null) {
                try {
                    trans.rollback();
                } catch (HibernateException e) {
                    //ignore
                }
                throw new RuntimeException(ex.getMessage());
            }
        }*

        /*
        java.util.List<PersistentTest> testList = null;
        try(Session se = HibernateUtil.getInstance().getCurrentSession()) {
            trans = se.beginTransaction();
            Criteria criteria = se.createCriteria(PersistentTest.class);
            testList = null;
            trans.commit();
        } catch(HibernateException exc) {
            if(trans != null) {
                try {
                    trans.rollback();
                } catch (HibernateException e) {
                    //ignore
                }
                throw new RuntimeException(exc.getMessage());
            }
        }
        System.out.println(testList);*/
    }

    @Test
    public void testIsGameExisting() throws Exception {

    }

    @Test
    public void testLoadGame() throws Exception {

    }

    @Test
    public void testDeleteGameForPlayers() throws Exception {

    }

    @Test
    public void testDeleteAllGamesForPlayer() throws Exception {

    }
}