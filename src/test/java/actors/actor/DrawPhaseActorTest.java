package actors.actor;

import actors.message.DrawHiddenMessage;
import actors.message.DrawOpenMessage;
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
import util.CardCreator;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
public class DrawPhaseActorTest {
    private static final Timeout TIMEOUT = new Timeout(60, TimeUnit.SECONDS);
    private ActorSystem phaseXActorSystem;
    private ActorRef drawPhaseActor;
    private DrawOpenMessage drawOpenMessage;
    private DrawHiddenMessage drawMessage;
    private IDeckOfCards playerHand;
    private IDeckOfCards discardPile;
    private IDeckOfCards drawPile;
    private ICard card;
    private ICard card2;
    private ICard card3;
    private IRoundState state;
    private IPlayer player;

    @Before
    public void setUp() {
        phaseXActorSystem = ActorSystem.create("PhaseXTestActorSystem");
        drawPhaseActor = phaseXActorSystem.actorOf(Props.create(DrawPhaseActor.class), "drawPhaseActorTest");
        state = new RoundState();
        state.setState(StateEnum.DRAW_PHASE);
        discardPile = new DeckOfCards();
        card3 = new Card(CardValue.FIVE, CardColor.BLUE);
        discardPile.add(card3);
        drawPile = CardCreator.giveDeckOfCards();
        playerHand = new DeckOfCards();
        card = new Card(CardValue.EIGHT, CardColor.BLUE);
        card2 = new Card(CardValue.ELEVEN, CardColor.GREEN);
        playerHand.add(card);
        playerHand.add(card2);
        player = new Player(0);
        player.setDeckOfCards(playerHand);
        drawOpenMessage = new DrawOpenMessage(discardPile, playerHand, player, state);
        drawMessage = new DrawHiddenMessage(drawPile, playerHand, player, state);
    }

    @Test
    public void validDrawOpen() {
        Future<Object> fut = Patterns.ask(drawPhaseActor, drawOpenMessage, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(result);
        assertEquals(card3, playerHand.get(0));

        assertEquals(discardPile.size(), 0);
    }

    @Test
    public void validDrawHidden() {
        int decksize = drawPile.size();
        int handsize = playerHand.size();
        Future<Object> fut = Patterns.ask(drawPhaseActor, drawMessage, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(result);
        assertEquals(decksize - 1, drawPile.size());
        assertEquals(handsize + 1, playerHand.size());
    }

    @Test
    public void invalidMessage() {
        Future<Object> fut = Patterns.ask(drawPhaseActor, new Object(), TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(result);
    }
}
