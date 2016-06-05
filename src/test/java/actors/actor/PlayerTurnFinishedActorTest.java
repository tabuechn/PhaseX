package actors.actor;

import actors.message.AddToPhaseMessage;
import actors.message.DiscardMessage;
import actors.message.MasterMessage;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.roundState.StateEnum;
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
public class PlayerTurnFinishedActorTest extends AbstractActorTest {
    private static final Timeout TIMEOUT = new Timeout(60, TimeUnit.SECONDS);
    private static final Card VALID_CARD = new Card(CardValue.EIGHT, CardColor.BLUE);
    private static final Card INVALID_CARD = new Card(CardValue.ELEVEN, CardColor.GREEN);
    private static final int TEST_DECK_SIZE = 6;
    private ActorRef playerTurnFinishedActor;
    private ICardStack stack;

    @Before
    public void setUp() {
        ActorSystem phaseXActorSystem = ActorSystem.create("PhaseXTestActorSystem");
        playerTurnFinishedActor = phaseXActorSystem.actorOf(Props.create(PlayerTurnFinishedActor.class), "playerTurnFinishedTest");
        setState(StateEnum.PLAYER_TURN_FINISHED);
        initPlayer();
        IDeckOfCards colorDeck = new DeckOfCards();
        for (int i = 0; i < TEST_DECK_SIZE; i++) {
            colorDeck.add(new Card(CardValue.EIGHT, CardColor.BLUE));
        }
        stack = new ColorStack(colorDeck);
    }

    @Test
    public void validDiscardMessageShouldBeForwardedToDiscardActor() {
        DiscardMessage message = new DiscardMessage(state, new DeckOfCards(), VALID_CARD, player);
        Future<Object> fut = Patterns.ask(playerTurnFinishedActor, message, TIMEOUT);
        DiscardMessage result = DiscardMessage.getDiscardMessage(fut, TIMEOUT.duration());
        assertNotNull(result);
    }

    @Test
    public void validAddToPhaseMessageWithValidCardShouldAddCardToStack() throws Exception {
        int stackSize = stack.getList().size();
        int handSize = player.getDeckOfCards().size();
        AddToPhaseMessage message = new AddToPhaseMessage(state, VALID_CARD, stack, player);
        Future<Object> fut = Patterns.ask(playerTurnFinishedActor, message, TIMEOUT);
        AddToPhaseMessage ret = WaitForResultAndCheckType(fut);
        assertEquals(stackSize + 1, ret.getStack().getList().size());
        assertEquals(handSize - 1, ret.getCurrentPlayer().getDeckOfCards().size());
    }

    @Test
    public void validAddToPhaseMessageWithInvalidCardShouldNotAddCardToStack() throws Exception {
        int stackSize = stack.getList().size();
        int handSize = player.getDeckOfCards().size();
        AddToPhaseMessage message = new AddToPhaseMessage(state, INVALID_CARD, stack, player);
        Future<Object> fut = Patterns.ask(playerTurnFinishedActor, message, TIMEOUT);
        AddToPhaseMessage ret = WaitForResultAndCheckType(fut);
        assertEquals(stackSize, ret.getStack().getList().size());
        assertEquals(handSize, player.getDeckOfCards().size());
    }

    @Test
    public void invalidMessageShouldNotReturnAValidMasterMessage() throws Exception {
        Future<Object> fut = Patterns.ask(playerTurnFinishedActor, new Object(), TIMEOUT);
        Object result = Await.result(fut, TIMEOUT.duration());
        assertFalse(result instanceof MasterMessage);
    }

    private AddToPhaseMessage WaitForResultAndCheckType(Future<Object> fut) throws Exception {
        Object result = Await.result(fut, TIMEOUT.duration());
        assertTrue(result instanceof AddToPhaseMessage);
        return (AddToPhaseMessage) result;
    }
}
