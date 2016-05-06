package persistence.couchDB;

import controller.UIController;
import controller.impl.Controller;
import org.ektorp.CouchDbConnector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * If everything works right this class was
 * created by konraifen88 on 05.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CouchDbDAOTest {

    private static final String PLAYER_NAME = "p1";
    private UIController controller;
    private CouchControllerData data;

    @InjectMocks
    private CouchDbDAO testee = new CouchDbDAO();

    @Mock
    private CouchDbConnector db;

    @Mock
    private PhaseXRepository repo;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        initController(PLAYER_NAME);
    }

    @Test
    public void saveGameToDbWithoutUserShouldCallCreate() {
        when(repo.findByName(any())).thenReturn(null);
        testee.saveGame(controller);
        verify(db).create(any(CouchControllerData.class));
    }

    @Test
    public void saveGameToDbWithExistingUserShouldCallUpdate() {
        when(repo.findByName(any())).thenReturn(data);
        testee.saveGame(controller);
        verify(db).update(any(CouchControllerData.class));
    }

    @Test
    public void loadUserNotInDBShouldReturnNewController() {
        when(repo.findByName(any())).thenReturn(null);
        testee.loadGame(controller.getCurrentPlayer());
        assertNotEquals(controller, null);
    }

    @Test
    public void loadUserInDBShouldReturnSavedController() {
        //Return data for CouchControllerData
        assertNotEquals(data.getController(), null);
    }

    private void initController(String playerName) {
        controller = new Controller(2);
        controller.startGame(PLAYER_NAME);
        data = new CouchControllerData();
        data.setController(controller);
    }

}