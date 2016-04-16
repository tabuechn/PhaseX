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
import model.stack.impl.ColorStack;
import model.stack.impl.PairStack;
import model.stack.impl.StackDeserializer;
import model.stack.impl.StreetStack;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.CardCreator;

import java.util.LinkedList;
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
    HibernateDAO hdao;

    @Before
    public void setUp() throws Exception {
        klaus = "Klaus";
        herbert = "Herbert";
        deck = CardCreator.giveDeckOfCards();
        ctrlData = new ControllerData();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ICard.class, new CardDeserializer());
        gsonBuilder.registerTypeAdapter(ICardStack.class, new StackDeserializer());
        gson = gsonBuilder.create();
        hdao = new HibernateDAO();
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

        List<ICardStack> testStacks = new LinkedList<>();

        ICardStack pairStack = new PairStack(getPairStack(4));
        ICardStack colorStack = new ColorStack(getColorStack(CardColor.RED));
        ICardStack streetStack = new StreetStack(getStreetStack());

        testStacks.add(pairStack);
        testStacks.add(colorStack);
        testStacks.add(streetStack);
        ctrl.setAllStacks(testStacks);

        hdao.saveGame(ctrl);

        Session session = HibernateUtil.getInstance().getCurrentSession();
        Transaction trans = session.beginTransaction();
        Criteria criteria = session.createCriteria(ControllerData.class);
        List testlist =criteria.list();
        int i = 0;
        for(Object o : testlist) {
            ControllerData cd = (ControllerData) o;
            assertFalse(cd.getStack1().isEmpty());
            assertFalse(cd.getStack2().isEmpty());

            ICardStack stack1res = gson.fromJson(cd.getStack1(),ICardStack.class);
            assertTrue(stack1res instanceof PairStack);
            assertEquals(pairStack.getList(),stack1res.getList());

            ICardStack stack2res = gson.fromJson(cd.getStack2(),ICardStack.class);
            assertTrue(stack2res instanceof ColorStack);
            assertEquals(colorStack.getList(),stack2res.getList());

            ICardStack stack3res = gson.fromJson(cd.getStack3(),ICardStack.class);
            assertTrue(stack3res instanceof StreetStack);
            assertEquals(streetStack.getList(),stack3res.getList());
            System.out.println(cd.getStack4());


            session.delete(o);
            i++;
        }
        trans.commit();
        assertEquals(i,1);
    }


    @Test
    public void saveAndDeletePlayers() throws Exception {
        Controller ctrl = new Controller();
        ctrl.startGame(herbert);
        ctrl.setSecondPlayerName(klaus);


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

    private IDeckOfCards getColorStack(CardColor color) {
        IDeckOfCards retDeck = new DeckOfCards();
        for(int i = 1; i < 9; i++) {
            ICard card = new Card(CardValue.byOrdinal(i),color);
            retDeck.add(card);
        }
        return retDeck;
    }

    private IDeckOfCards getStreetStack() {
        IDeckOfCards retDeck = new DeckOfCards();
        for(int i = 1; i < 6; i++) {
            ICard card = new Card(CardValue.byOrdinal(i),CardColor.BLUE);
            retDeck.add(card);
        }
        return retDeck;
    }

    private IDeckOfCards getPairStack(int pairValue) {
        IDeckOfCards pairStack = new DeckOfCards();
        for(int i = 0; i < 4;i++) {
            ICard card = new Card(CardValue.byOrdinal(pairValue),CardColor.GREEN);
            pairStack.add(card);
        }
        return pairStack;
    }
}