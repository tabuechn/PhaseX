package actors.actor;

import actors.message.AddToPhaseMessage;
import actors.message.DiscardMessage;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;
import model.player.impl.Player;
import model.roundState.IRoundState;
import model.roundState.StateEnum;
import model.roundState.impl.RoundState;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
public class PlayerTurnFinishedActorTest {
    private static final Timeout TIMEOUT = new Timeout(60, TimeUnit.SECONDS);
    private ActorSystem phaseXActorSystem;
    private ActorRef playerTurnFinishedActor;
    private DiscardMessage discardMessage;
    private AddToPhaseMessage addToPhaseMessageWithValidCard;
    private AddToPhaseMessage addToPhaseMessageWithInvalidCard;
    private IDeckOfCards playerHand;
    private IDeckOfCards discardPile;
    private ICard card;
    private ICard card2;
    private IRoundState state;
    private IPlayer player;
    private ICardStack stack;

    @Before
    public void setUp() {
        phaseXActorSystem = ActorSystem.create("PhaseXTestActorSystem");
        playerTurnFinishedActor = phaseXActorSystem.actorOf(Props.create(PlayerTurnFinishedActor.class), "playerTurnFinishedTest");
        state = new RoundState();
        state.setState(StateEnum.PLAYER_TURN_FINISHED);
        discardPile = new DeckOfCards();
        playerHand = new DeckOfCards();
        card = new Card(CardValue.EIGHT, CardColor.BLUE);
        card2 = new Card(CardValue.ELEVEN, CardColor.GREEN);
        playerHand.add(card);
        playerHand.add(card2);
        player = new Player(0);
        player.setDeckOfCards(playerHand);
        discardMessage = new DiscardMessage(state, discardPile, card, player);
        IDeckOfCards colorDeck = new DeckOfCards();
        for (int i = 0; i < 6; i++) {
            colorDeck.add(new Card(CardValue.EIGHT, CardColor.BLUE));
        }
        stack = new ColorStack(colorDeck);
        addToPhaseMessageWithValidCard = new AddToPhaseMessage(state, card, stack, player);
        addToPhaseMessageWithInvalidCard = new AddToPhaseMessage(state, card2, stack, player);
    }

    @Test
    public void validDiscardMessage() {
        Future<Object> fut = Patterns.ask(playerTurnFinishedActor, discardMessage, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(result);
    }

    @Test
    public void validAddToPhaseMessageWithValidCard() {
        int stackSize = stack.getList().size();
        int handsize = player.getDeckOfCards().size();
        Future<Object> fut = Patterns.ask(playerTurnFinishedActor, addToPhaseMessageWithValidCard, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(result);
        assertEquals(stackSize + 1, stack.getList().size());
        assertEquals(handsize - 1, player.getDeckOfCards().size());
    }

    @Test
    public void validAddToPhaseMessageWithInvalidCard() {
        int stackSize = stack.getList().size();
        int handsize = player.getDeckOfCards().size();
        Future<Object> fut = Patterns.ask(playerTurnFinishedActor, addToPhaseMessageWithInvalidCard, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(result);
        assertEquals(stackSize, stack.getList().size());
        assertEquals(handsize, player.getDeckOfCards().size());
    }

    @Test
    public void invalidMessage() {
        Future<Object> fut = Patterns.ask(playerTurnFinishedActor, new Object(), TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(result);
    }
}
