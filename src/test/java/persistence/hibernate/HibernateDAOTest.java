package persistence.hibernate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.card.impl.CardDeserializer;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.phase.IPhase;
import model.player.impl.Player;
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
    public void saveAndDeletePlayers() throws Exception {
        String pileString = new Gson().toJson(deck);

        Player herbertObject = new Player(0);
        herbertObject.setName(herbert);
        ctrlData.setPlayer1(herbertObject);
        ctrlData.setPlayer1Pile(pileString);
        ctrlData.setPlayer1PhaseString(herbertObject.getPhase());

        Player klausObject= new Player(1);
        klausObject.setName(klaus);
        ctrlData.setPlayer2(klausObject);
        ctrlData.setPlayer2Pile(pileString);
        ctrlData.setPlayer2PhaseString(klausObject.getPhase());

        Session session = HibernateUtil.getInstance().getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.save(ctrlData);
        trans.commit();

        session = HibernateUtil.getInstance().getCurrentSession();
        trans = session.beginTransaction();

        Criteria criteria = session.createCriteria(ControllerData.class);
        List testlist =criteria.list();
        int i = 0;
        for(Object o : testlist) {
            ControllerData cd = (ControllerData) o;
            assertEquals(herbert,cd.getPlayer1().getPlayerName());
            assertEquals(pileString, cd.getPlayer1Pile());

            DeckOfCards result = gson.fromJson(cd.getPlayer1Pile(),DeckOfCards.class);
            assertEquals(result,deck);

            assertEquals(cd.getPlayer1PhaseString(),herbertObject.getPhase().toString());

            assertEquals(klaus,cd.getPlayer2().getPlayerName());

            session.delete(o);
            i++;
        }
        trans.commit();
        assertEquals(i,1);
    }
}