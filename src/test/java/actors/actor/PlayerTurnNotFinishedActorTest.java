package actors.actor;

import actors.message.DiscardMessage;
import actors.message.MasterMessage;
import actors.message.PlayPhaseMessage;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.roundState.StateEnum;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
public class PlayerTurnNotFinishedActorTest extends AbstractActorTest {
    private static final int NUMBER_OF_PLAYED_PHASES = 2;
    private static final int SIZE_OF_TWO_PHASES = 6;
    private ActorRef playerTurnNotFinishedActor;

    @Before
    public void setUp() {
        ActorSystem phaseXActorSystem = ActorSystem.create("PhaseXTestActorSystem");
        playerTurnNotFinishedActor = phaseXActorSystem.actorOf(Props.create(PlayerTurnNotFinishedActor.class), "PlayerTurnNotTest");
        setState(StateEnum.PLAYER_TURN_FINISHED);
        initPiles();
        initPlayer();
    }

    @Test
    public void validPlayPhaseMessageShouldReturnExtractedPhase() throws Exception {
        setDeckForCurrentPlayer(getDeckWithValidPhase());
        player.getDeckOfCards().add(TEST_CARD_3);
        int handSize = player.getDeckOfCards().size();
        int numberOfPlayedPhases = allStacks.size();
        PlayPhaseMessage message = new PlayPhaseMessage(state, getDeckWithValidPhase(), player, allStacks);
        Future<Object> fut = Patterns.ask(playerTurnNotFinishedActor, message, TIMEOUT);
        Object result = Await.result(fut, TIMEOUT.duration());
        assertTrue(result instanceof PlayPhaseMessage);
        PlayPhaseMessage ret = (PlayPhaseMessage) result;
        assertEquals(handSize - SIZE_OF_TWO_PHASES, ret.getCurrentPlayer().getDeckOfCards().size());
        assertEquals(numberOfPlayedPhases + NUMBER_OF_PLAYED_PHASES, ret.getAllStacks().size());
    }

    @Test
    public void invalidPlayPhaseMessageShouldNotChangeAnything() throws Exception {
        setDeckForCurrentPlayer(getDeckWithInvalidPhase());
        int handSize = player.getDeckOfCards().size();
        int numberOfPlayedPhases = allStacks.size();
        PlayPhaseMessage invalidPlayPhaseMessage = new PlayPhaseMessage(state, player.getDeckOfCards(), player, allStacks);
        Future<Object> fut = Patterns.ask(playerTurnNotFinishedActor, invalidPlayPhaseMessage, TIMEOUT);
        Object result = Await.result(fut, TIMEOUT.duration());
        assertTrue(result instanceof PlayPhaseMessage);
        assertEquals(handSize, player.getDeckOfCards().size());
        assertEquals(numberOfPlayedPhases, allStacks.size());
    }

    @Test
    public void validDiscardMessageShouldBeForwardedToDiscardActor() {
        DiscardMessage message = new DiscardMessage(state, discardPile, TEST_CARD, player);
        Future<Object> fut = Patterns.ask(playerTurnNotFinishedActor, message, TIMEOUT);
        DiscardMessage result = DiscardMessage.getDiscardMessage(fut, TIMEOUT.duration());
        assertNotNull(result);
    }

    @Test
    public void invalidMessageShouldNotReturnAMasterMessage() throws Exception {
        Future<Object> fut = Patterns.ask(playerTurnNotFinishedActor, new Object(), TIMEOUT);
        Object result = Await.result(fut, TIMEOUT.duration());
        assertFalse(result instanceof MasterMessage);
    }

    private IDeckOfCards getDeckWithValidPhase() {
        IDeckOfCards validPhase = new DeckOfCards();
        for (int i = 0; i < 6; i++) {
            ICard tmpCard = TEST_CARD;
            validPhase.add(tmpCard);
        }
        return validPhase;
    }

    private IDeckOfCards getDeckWithInvalidPhase() {
        return new DeckOfCards(Arrays.asList(TEST_CARD, TEST_CARD_2, TEST_CARD_3));

    }

    private void setDeckForCurrentPlayer(IDeckOfCards deck) {
        player.setDeckOfCards(deck);
    }
}
