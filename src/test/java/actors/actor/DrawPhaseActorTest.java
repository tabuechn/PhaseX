package actors.actor;

import actors.message.DrawHiddenMessage;
import actors.message.DrawOpenMessage;
import actors.message.MasterMessage;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import model.state.StateEnum;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
public class DrawPhaseActorTest extends AbstractActorTest {
    private static final Timeout TIMEOUT = new Timeout(60, TimeUnit.SECONDS);
    private ActorRef drawPhaseActor;

    @Before
    public void setUp() {
        ActorSystem phaseXActorSystem = ActorSystem.create("PhaseXTestActorSystem");
        drawPhaseActor = phaseXActorSystem.actorOf(Props.create(DrawPhaseActor.class), "drawPhaseActorTest");
        setState(StateEnum.DRAW_PHASE);
        initPiles();
        initPlayer();
    }

    @Test
    public void validDrawOpenShouldReturnTheChangedDrawOpenMessage() throws Exception {
        DrawOpenMessage message = new DrawOpenMessage(discardPile, player.getDeckOfCards(), player, state);
        Future<Object> fut = Patterns.ask(drawPhaseActor, message, TIMEOUT);
        Object result = Await.result(fut, TIMEOUT.duration());
        assertTrue(result instanceof DrawOpenMessage);
        DrawOpenMessage ret = (DrawOpenMessage) result;
        assertTrue(ret.getHand().contains(TEST_CARD_3));
        assertTrue(ret.getPile().isEmpty());
    }

    @Test
    public void validDrawHiddenShouldReturnTheChangedDrawHiddenMessage() throws Exception {
        int deckSize = drawPile.size();
        int handSize = player.getDeckOfCards().size();
        DrawHiddenMessage message = new DrawHiddenMessage(drawPile, player.getDeckOfCards(), player, state);
        Future<Object> fut = Patterns.ask(drawPhaseActor, message, TIMEOUT);
        Object result = Await.result(fut, TIMEOUT.duration());
        assertTrue(result instanceof DrawHiddenMessage);
        DrawHiddenMessage ret = (DrawHiddenMessage) result;
        assertEquals(deckSize - 1, ret.getPile().size());
        assertEquals(handSize + 1, ret.getHand().size());
    }

    @Test
    public void invalidMessageShouldNotReturnAValidMasterMessage() throws Exception {
        Future<Object> fut = Patterns.ask(drawPhaseActor, new Object(), TIMEOUT);
        Object result = Await.result(fut, TIMEOUT.duration());
        assertFalse(result instanceof MasterMessage);
    }
}
