package persistence.hibernate;

import controller.UIController;
import controller.impl.Controller;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import persistence.DatabaseAccess;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by tabuechn on 07.05.2016.
 */
public class DatabaseAccessTest {
    DatabaseAccess dbtest;

    @Before
    public void setUp() throws Exception {
        dbtest = new DatabaseAccess();
    }

    @Test
    public void testSaveAndLoad() {
        UIController controller = new Controller();
        controller.startGame("Peter");
        dbtest.saveGame(controller);

        UIController loadedController = dbtest.loadGame(controller.getPlayers()[0]);
        assertNotNull(loadedController);
    }

    @After
    public void deleteHibernateTest() throws Exception {
        HibernateDAOTest hibernateDAOTest = new HibernateDAOTest();
        hibernateDAOTest.deleteTest();
    }

}
