package persistence.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.hibernate.controller.PlayerData;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tabuechn on 05.04.2016.
 */
public class HibernateDAOTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void saveAndDeleteObject() throws Exception {
        ControllerData test = new ControllerData();
        String klaus = "Klaus";
        String herbert = "Herbert";
        PlayerData herbertObject = new PlayerData();
        herbertObject.setPlayerName(herbert);
        test.setPlayer1Data(herbertObject);
        PlayerData klausObject= new PlayerData();
        klausObject.setPlayerName(klaus);
        test.setPlayer2Data(klausObject);
        Session session = HibernateUtil.getInstance().getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.save(test);
        trans.commit();

        session = HibernateUtil.getInstance().getCurrentSession();
        trans = session.beginTransaction();

        Criteria criteria = session.createCriteria(ControllerData.class);
        List testlist =criteria.list();
        int i = 0;
        for(Object o : testlist) {
            ControllerData pt = (ControllerData) o;
            assertEquals(herbert,pt.getPlayer1Data().getPlayerName());
            assertEquals(klaus,pt.getPlayer2Data().getPlayerName());
            session.delete(o);
            i++;
        }
        assertEquals(i,1);
        trans.commit();
    }
}