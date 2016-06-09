package persistence.hibernate;

import com.google.gson.Gson;
import controller.UIController;
import controller.statusmessage.impl.StatusMessage;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.phase.impl.Phase1;
import model.player.impl.Player;
import model.roundstate.IRoundState;
import model.roundstate.StateEnum;
import model.roundstate.impl.RoundState;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import org.junit.Before;
import org.junit.Test;
import util.CardCreator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by tabuechn on 19.04.2016.
 */
public class HibernateControllerDataTest {

    private HibernateControllerData hibernateControllerData;

    @Before
    public void setUp() throws Exception {
        hibernateControllerData = new HibernateControllerData();
    }


    @Test
    public void testGetAndSetStringID() throws Exception {
        hibernateControllerData.setStringID(1);
        assertEquals(hibernateControllerData.getStringID(), new Integer(1));

    }


    @Test
    public void testGetAndSetPlayer2() throws Exception {
        Player player2 = new Player(0);
        player2.setName("Herbert");
        hibernateControllerData.setPlayer2(player2);
        assertEquals(hibernateControllerData.getPlayer2().getPlayerName(), "Herbert");
    }


    @Test
    public void testGetAndSetPlayer1() throws Exception {
        Player player1 = new Player(0);
        player1.setName("Herbert");
        hibernateControllerData.setPlayer1(player1);
        assertEquals(hibernateControllerData.getPlayer1().getPlayerName(), "Herbert");
    }


    @Test
    public void testGetAndSetPlayer1Pile() throws Exception {
        hibernateControllerData.setPlayer1Pile("test");
        assertEquals(hibernateControllerData.getPlayer1Pile(), "test");
    }


    @Test
    public void testGetPlayer2Pile() throws Exception {
        hibernateControllerData.setPlayer2Pile("test");
        assertEquals(hibernateControllerData.getPlayer2Pile(), "test");
    }


    @Test
    public void testGetAndSetPlayer1PhaseString() throws Exception {
        Player player1 = new Player(0);
        hibernateControllerData.setPlayer1PhaseString(player1.getPhase().toString());
        assertEquals(hibernateControllerData.getPlayer1PhaseString(), player1.getPhase().toString());
    }


    @Test
    public void testGetAndSetPlayer2PhaseString() throws Exception {
        Player player2 = new Player(0);
        hibernateControllerData.setPlayer2PhaseString(player2.getPhase().toString());
        assertEquals(hibernateControllerData.getPlayer2PhaseString(), player2.getPhase().toString());
    }


    @Test
    public void testGetAndSetDiscardPile() throws Exception {
        hibernateControllerData.setDiscardPile("test");
        assertEquals(hibernateControllerData.getDiscardPile(), "test");
    }


    @Test
    public void testGetAndSetDrawPile() throws Exception {
        hibernateControllerData.setDrawPile("test");
        assertEquals(hibernateControllerData.getDrawPile(), "test");
    }


    @Test
    public void testGetRoundState() throws Exception {
        hibernateControllerData.setRoundState("test");
        assertEquals(hibernateControllerData.getRoundState(), "test");
    }

    @Test
    public void setPlayer1PhaseString() {
        hibernateControllerData.setPlayer1PhaseString(new Phase1());
        assertEquals("Phase1", hibernateControllerData.getPlayer1PhaseString());
    }

    @Test
    public void setPlayer2PhaseString() {
        hibernateControllerData.setPlayer2PhaseString(new Phase1());
        assertEquals("Phase1", hibernateControllerData.getPlayer2PhaseString());
    }

    @Test
    public void testAndGetStatusMessage() {
        hibernateControllerData.setStatusMessage("testnachricht");
        assertEquals("testnachricht", hibernateControllerData.getStatusMessage());
    }

    @Test
    public void getAndSetCurrentPlayerIndex() {
        hibernateControllerData.setCurrentPlayerIndex(1);
        assertEquals(1, hibernateControllerData.getCurrentPlayerIndex());
    }

    @Test
    public void testGetAndSetStacks() {
        hibernateControllerData.setStack("stack1", 1);
        hibernateControllerData.setStack("stack2", 2);
        hibernateControllerData.setStack("stack3", 3);
        hibernateControllerData.setStack("stack4", 4);
        assertEquals("stack1", hibernateControllerData.getStack1());
        assertEquals("stack2", hibernateControllerData.getStack2());
        assertEquals("stack3", hibernateControllerData.getStack3());
        assertEquals("stack4", hibernateControllerData.getStack4());
    }

    @Test
    public void setInvalidStack() {
        boolean error = false;
        try {
            hibernateControllerData.setStack("error", 5);
        } catch (IllegalArgumentException iae) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void getControllerTest() {

        Player player1 = new Player(0);
        IDeckOfCards player1Hand = new DeckOfCards();
        player1.setDeckOfCards(player1Hand);
        Player player2 = new Player(1);
        IDeckOfCards player2Hand = new DeckOfCards();
        player2.setDeckOfCards(player2Hand);

        IDeckOfCards drawPile = CardCreator.giveDeckOfCards();
        for(int i = 0; i < 10;++i) {
            player1.getDeckOfCards().add(drawPile.removeLast());
            player2.getDeckOfCards().add(drawPile.removeLast());
        }
        IDeckOfCards discardPile = new DeckOfCards();
        discardPile.add(drawPile.removeLast());
        int currentPlayerIndex = 0;
        IRoundState state = new RoundState();
        state.setState(StateEnum.DRAW_PHASE);
        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatusMessage("start");
        hibernateControllerData.setPlayer1(player1);
        hibernateControllerData.setPlayer1Pile(player1.getDeckOfCards().getJSon());
        hibernateControllerData.setPlayer1PhaseString(player1.getPhase());

        hibernateControllerData.setPlayer2(player2);
        hibernateControllerData.setPlayer2Pile(player2.getDeckOfCards().getJSon());
        hibernateControllerData.setPlayer2PhaseString(player2.getPhase());

        hibernateControllerData.setCurrentPlayerIndex(currentPlayerIndex);

        hibernateControllerData.setDiscardPile(discardPile.getJSon());
        hibernateControllerData.setDrawPile(drawPile.getJSon());

        hibernateControllerData.setRoundState(state.toString());
        hibernateControllerData.setStatusMessage(statusMessage.getStatusMessage());

        UIController controller = hibernateControllerData.getController();
        Gson gsonNormal = new Gson();

        ICardStack stack1 = new ColorStack(getColorStack(CardColor.BLUE));
        ICardStack stack2 = new ColorStack(getColorStack(CardColor.BLUE));
        ICardStack stack3 = new ColorStack(getColorStack(CardColor.BLUE));
        ICardStack stack4 = new ColorStack(getColorStack(CardColor.BLUE));
        hibernateControllerData.setStack1(gsonNormal.toJson(stack1));
        hibernateControllerData.setStack2(gsonNormal.toJson(stack2));
        hibernateControllerData.setStack3(gsonNormal.toJson(stack3));
        hibernateControllerData.setStack4(gsonNormal.toJson(stack4));
        controller = hibernateControllerData.getController();
    }

    private IDeckOfCards getColorStack(CardColor color) {
        IDeckOfCards retDeck = new DeckOfCards();
        for (int i = 1; i < 9; i++) {
            ICard card = new Card(CardValue.byOrdinal(i), color);
            retDeck.add(card);
        }
        return retDeck;
    }


}