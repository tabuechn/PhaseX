package actors.actor;

import actors.message.DiscardMessage;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import model.roundstate.StateEnum;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.Future;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;


/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
//TODO: Please provide more meaningful names for methods, describing what is tested
public class ActorMasterTest extends AbstractActorTest {

    private ActorRef masterActor;

    @Before
    public void setUp() {
        ActorSystem phaseXActorSystem = ActorSystem.create("PhaseXTestActorSystem");
        masterActor = phaseXActorSystem.actorOf(Props.create(ActorMaster.class), "masterTest");
        setState(StateEnum.PLAYER_TURN_FINISHED);
        initPiles();
        initPlayer();
    }

    @Test
    public void validDiscardMessagePlayTurnFinishedShouldBeForwardedToDiscardActor() {
        DiscardMessage message = new DiscardMessage(state, discardPile, TEST_CARD, player);
        Future<Object> fut = Patterns.ask(masterActor, message, TIMEOUT);
        DiscardMessage result = DiscardMessage.getDiscardMessage(fut, TIMEOUT.duration());
        assertNotNull(result);
    }

    @Test
    public void validDiscardMessagePlayerTurnNotFinishedShouldBeForwardedToDiscardActor() {
        state.setState(StateEnum.PLAYER_TURN_NOT_FINISHED);
        DiscardMessage message = new DiscardMessage(state, discardPile, TEST_CARD, player);
        Future<Object> fut = Patterns.ask(masterActor, message, TIMEOUT);
        DiscardMessage result = DiscardMessage.getDiscardMessage(fut, TIMEOUT.duration());
        assertNotNull(result);
    }

    @Test
    //TODO Correct in this case? Discard not allowed in DrawPhase?
    public void validDiscardMessageDrawPhaseShouldBeForwardedToDiscardActor() {
        setState(StateEnum.DRAW_PHASE);
        DiscardMessage message = new DiscardMessage(state, discardPile, TEST_CARD, player);
        Future<Object> fut = Patterns.ask(masterActor, message, TIMEOUT);
        DiscardMessage result = DiscardMessage.getDiscardMessage(fut, TIMEOUT.duration());
        assertNull(result);
    }


    @Test
    public void validDiscardMessageInvalidPhase() {
        state.setState(StateEnum.END_PHASE);
        DiscardMessage message = new DiscardMessage(state, discardPile, TEST_CARD, player);
        Future<Object> fut = Patterns.ask(masterActor, message, TIMEOUT);
        DiscardMessage result = DiscardMessage.getDiscardMessage(fut, TIMEOUT.duration());
        assertNull(result);
    }

    @Test
    public void wrongMessage() throws Exception {
        Future<Object> fut = Patterns.ask(masterActor, new Object(), TIMEOUT);
        DiscardMessage result = DiscardMessage.getDiscardMessage(fut, TIMEOUT.duration());
        assertNull(result);
    }

}
