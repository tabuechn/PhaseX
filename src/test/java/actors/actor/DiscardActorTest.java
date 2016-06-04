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

import static org.junit.Assert.*;

/**
 * Created by Tarek on 30.05.2016. Be grateful for this superior Code!
 */
public class DiscardActorTest {
    private static final Timeout TIMEOUT = new Timeout(60, TimeUnit.SECONDS);
    private ActorSystem phaseXActorSystem;
    private ActorRef discardActor;
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
        discardActor = phaseXActorSystem.actorOf(Props.create(DiscardActor.class), "discardTest");
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
    public void discardMessage() {

        Future<Object> fut = Patterns.ask(discardActor, discardMessage, TIMEOUT);
        DiscardMessage result = null;
        try {
            Object o = Await.result(fut, TIMEOUT.duration());
            if (o instanceof DiscardMessage) {
                result = (DiscardMessage) o;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(result);
        assertEquals(1, result.getDiscardPile().size());
        assertEquals(1, result.getCurrentPlayer().getDeckOfCards().size());
        assertEquals(card, result.getDiscardPile().get(0));
        assertEquals(card2, result.getCurrentPlayer().getDeckOfCards().get(0));
    }

    @Test
    public void wrongMessage() throws Exception {
        Future<Object> fut = Patterns.ask(discardActor, new Object(), TIMEOUT);
        DiscardMessage result = null;
        try {
            Object o = Await.result(fut, TIMEOUT.duration());
            if (o instanceof DiscardMessage) {
                result = (DiscardMessage) o;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNull(result);
    }

}
