package actors.actor;

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
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
public class ActorMasterTest {
    private static final Timeout TIMEOUT = new Timeout(60, TimeUnit.SECONDS);
    private ActorSystem phaseXActorSystem;
    private ActorRef masterActor;
    private DiscardMessage discardMessage;
    private IDeckOfCards playerHand;
    private IDeckOfCards discardPile;
    private ICard card;
    private ICard card2;
    private IRoundState state;
    private IPlayer player;

    @Before
    public void setUp() {
        phaseXActorSystem = ActorSystem.create("PhaseXTestActorSystem");
        masterActor = phaseXActorSystem.actorOf(Props.create(ActorMaster.class), "masterTest");
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
    }

    @Test
    public void validDiscardMessagePlayTurnFinished() {
        Future<Object> fut = Patterns.ask(masterActor, discardMessage, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(result);
    }

    @Test
    public void validDiscardMessagePlayerTurnNotFinished() {
        state.setState(StateEnum.PLAYER_TURN_NOT_FINISHED);
        DiscardMessage ptnfDiscardMessage = new DiscardMessage(state, discardPile, card, player);
        Future<Object> fut = Patterns.ask(masterActor, ptnfDiscardMessage, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(result);
    }

    @Test
    public void validDiscardMessageDrawPhase() {
        state.setState(StateEnum.DRAW_PHASE);
        DiscardMessage dpDiscardMessage = new DiscardMessage(state, discardPile, card, player);
        Future<Object> fut = Patterns.ask(masterActor, dpDiscardMessage, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(result);
    }

    @Test
    public void validDiscardMessageInvalidPhase() {
        state.setState(StateEnum.END_PHASE);
        DiscardMessage epDiscardMessage = new DiscardMessage(state, discardPile, card, player);
        Future<Object> fut = Patterns.ask(masterActor, epDiscardMessage, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
        }
        assertFalse(result);
    }

    @Test
    public void wrongMessage() throws Exception {
        Future<Object> fut = Patterns.ask(masterActor, new Object(), TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertFalse(result);
    }
}
