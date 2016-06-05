package actors.actor;

import actors.message.DiscardMessage;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import model.roundState.StateEnum;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by Tarek on 30.05.2016. Be grateful for this superior Code!
 */
public class DiscardActorTest extends AbstractActorTest {
    private ActorRef discardActor;


    @Before
    public void setUp() {
        setState(StateEnum.PLAYER_TURN_FINISHED);
        ActorSystem phaseXActorSystem = ActorSystem.create("PhaseXTestActorSystem");
        discardActor = phaseXActorSystem.actorOf(Props.create(DiscardActor.class), "discardTest");
        initPiles();
        initPlayer();
    }

    @Test
    public void discardMessage() {
        DiscardMessage message = new DiscardMessage(state, discardPile, TEST_CARD, player);
        Future<Object> fut = Patterns.ask(discardActor, message, TIMEOUT);
        DiscardMessage result = DiscardMessage.getDiscardMessage(fut, TIMEOUT.duration());
        assertNotNull(result);
        assertEquals(2, result.getDiscardPile().size());
        assertEquals(1, result.getCurrentPlayer().getDeckOfCards().size());
        assertEquals(TEST_CARD_3, result.getDiscardPile().get(0));
        assertEquals(TEST_CARD_2, result.getCurrentPlayer().getDeckOfCards().get(0));
    }

    @Test
    public void wrongMessage() throws Exception {
        Future<Object> fut = Patterns.ask(discardActor, new Object(), TIMEOUT);
        DiscardMessage result = DiscardMessage.getDiscardMessage(fut, TIMEOUT.duration());
        assertNull(result);
    }

}
