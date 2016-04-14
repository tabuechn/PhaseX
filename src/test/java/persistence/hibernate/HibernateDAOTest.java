package persistence.hibernate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import controller.impl.Controller;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.card.impl.CardDeserializer;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.phase.IPhase;
import model.player.impl.Player;
import model.stack.ICardStack;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.CardCreator;

import java.util.List;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

/**
 * Created by tabuechn on 05.04.2016.
 */
public class HibernateDAOTest {

    String klaus;
    String herbert;
    IDeckOfCards deck;
    ControllerData ctrlData;
    Gson gson;

    @Before
    public void setUp() throws Exception {
        klaus = "Klaus";
        herbert = "Herbert";
        deck = CardCreator.giveDeckOfCards();
        ctrlData = new ControllerData();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ICard.class, new CardDeserializer());
        gson = gsonBuilder.create();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void deleteTest() throws Exception {
        Session session = HibernateUtil.getInstance().getCurrentSession();
        Transaction trans = session.beginTransaction();
        Criteria criteria = session.createCriteria(ControllerData.class);
        List testlist = criteria.list();
        for(Object o : testlist) {
            session.delete(o);
        }
        trans.commit();
    }

    @Test
    public void saveAndDeleteStacks() throws Exception {
        Controller ctrl = new Controller();
        ctrl.startGame(herbert);
        ctrl.setSecondPlayerName(klaus);
        List<ICardStack>
    }


    @Test
    public void saveAndDeletePlayers() throws Exception {
        Controller ctrl = new Controller();
        ctrl.startGame(herbert);
        ctrl.setSecondPlayerName(klaus);

        HibernateDAO hdao = new HibernateDAO();
        hdao.saveGame(ctrl);


        Session session = HibernateUtil.getInstance().getCurrentSession();
        Transaction trans = session.beginTransaction();

        Criteria criteria = session.createCriteria(ControllerData.class);
        List testlist =criteria.list();
        int i = 0;
        for(Object o : testlist) {
            ControllerData cd = (ControllerData) o;
            assertEquals(herbert,cd.getPlayer1().getPlayerName());
            DeckOfCards herbertDeck = gson.fromJson(cd.getPlayer1Pile(),DeckOfCards.class);
            assertEquals(ctrl.getPlayers()[0].getDeckOfCards(),herbertDeck);
            assertEquals(ctrl.getPlayers()[0].getPhase().toString(),cd.getPlayer1PhaseString());

            assertEquals(klaus,cd.getPlayer2().getPlayerName());
            DeckOfCards klausDeck = gson.fromJson(cd.getPlayer2Pile(),DeckOfCards.class);
            assertEquals(ctrl.getPlayers()[1].getDeckOfCards(),klausDeck);
            assertEquals(ctrl.getPlayers()[1].getPhase().toString(),cd.getPlayer2PhaseString());

            session.delete(o);
            i++;
        }
        trans.commit();
        assertEquals(i,1);
    }
}