package persistence.hibernate;

import controller.UIController;
import controller.impl.ActorController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.DBEnum;
import persistence.DatabaseAccess;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by tabuechn on 07.05.2016.
 */
public class DatabaseAccessTest {
    DatabaseAccess dbtest;

    @Before
    public void setUp() throws Exception {
        dbtest = new DatabaseAccess(DBEnum.HIBERNATE);
    }

    @Test
    public void testSaveAndLoad() {
        UIController controller = new ActorController();
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
