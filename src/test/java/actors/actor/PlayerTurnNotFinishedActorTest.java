package actors.actor;

import actors.message.DiscardMessage;
import actors.message.PlayPhaseMessage;
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
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
public class PlayerTurnNotFinishedActorTest {
    private static final Timeout TIMEOUT = new Timeout(60, TimeUnit.SECONDS);
    private ActorSystem phaseXActorSystem;
    private ActorRef playerTurnNotFinishedActor;
    private DiscardMessage discardMessage;
    private PlayPhaseMessage validPlayPhaseMessage;
    private PlayPhaseMessage invalidPlayPhaseMessage;
    private IDeckOfCards playerHand;
    private IDeckOfCards discardPile;
    private ICard card;
    private ICard card2;
    private IRoundState state;
    private IPlayer player;
    private IPlayer invalidPlayer;
    private List<ICardStack> allStacks;

    @Before
    public void setUp() {
        phaseXActorSystem = ActorSystem.create("PhaseXTestActorSystem");
        playerTurnNotFinishedActor = phaseXActorSystem.actorOf(Props.create(PlayerTurnNotFinishedActor.class), "PlayerTurnNotTest");
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
        IDeckOfCards validPhase = new DeckOfCards();
        for (int i = 0; i < 6; i++) {
            ICard tmpCard = new Card(CardValue.EIGHT, CardColor.BLUE);
            validPhase.add(tmpCard);
            playerHand.add(tmpCard);
        }
        allStacks = new LinkedList<>();
        invalidPlayer = new Player(0);
        IDeckOfCards invalidPhase = new DeckOfCards();
        invalidPlayer.setDeckOfCards(invalidPhase);
        invalidPhase.add(new Card(CardValue.EIGHT, CardColor.BLUE));
        invalidPhase.add(new Card(CardValue.FOUR, CardColor.GREEN));
        validPlayPhaseMessage = new PlayPhaseMessage(state, validPhase, player, allStacks);
        invalidPlayPhaseMessage = new PlayPhaseMessage(state, invalidPhase, invalidPlayer, allStacks);
    }

    @Test
    public void validPlayPhaseMessage() {
        int handsize = playerHand.size();
        int numberOfPlayedPhases = allStacks.size();
        Future<Object> fut = Patterns.ask(playerTurnNotFinishedActor, validPlayPhaseMessage, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(result);
        assertEquals(handsize - 6, player.getDeckOfCards().size());
        assertEquals(numberOfPlayedPhases + 2, allStacks.size());
    }

    @Test
    public void invalidPlayPhaseMessage() {
        int handsize = invalidPlayer.getDeckOfCards().size();
        int numberOfPlayedPhases = allStacks.size();
        Future<Object> fut = Patterns.ask(playerTurnNotFinishedActor, invalidPlayPhaseMessage, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(result);
        assertEquals(handsize, invalidPlayer.getDeckOfCards().size());
        assertEquals(numberOfPlayedPhases, allStacks.size());
    }

    @Test
    public void validDiscardMessage() {
        Future<Object> fut = Patterns.ask(playerTurnNotFinishedActor, discardMessage, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(result);
    }

    @Test
    public void invalidMessage() {
        Future<Object> fut = Patterns.ask(playerTurnNotFinishedActor, new Object(), TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(result);
    }
}
