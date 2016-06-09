package actors.actor;

import akka.util.Timeout;
import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;
import model.player.impl.Player;
import model.roundstate.IRoundState;
import model.roundstate.StateEnum;
import model.roundstate.impl.RoundState;
import model.stack.ICardStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * If everything works right this class was
 * created by konraifen88 on 05.06.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
class AbstractActorTest {
    static final Timeout TIMEOUT = new Timeout(6000, TimeUnit.SECONDS);
    static final Card TEST_CARD = new Card(CardValue.EIGHT, CardColor.BLUE);
    static final Card TEST_CARD_2 = new Card(CardValue.ELEVEN, CardColor.GREEN);
    static final Card TEST_CARD_3 = new Card(CardValue.FIVE, CardColor.BLUE);


    IDeckOfCards discardPile;
    IDeckOfCards drawPile;
    IRoundState state;
    IPlayer player;
    List<ICardStack> allStacks;

    AbstractActorTest() {
        allStacks = new ArrayList<>();
        state = new RoundState();
        initPiles();
        initPlayer();
    }

    void initPlayer() {
        IDeckOfCards playerHand = new DeckOfCards();
        playerHand.add(TEST_CARD);
        playerHand.add(TEST_CARD_2);
        player = new Player(0);
        player.setDeckOfCards(playerHand);
    }


    void initPiles() {
        discardPile = new DeckOfCards();
        discardPile.add(TEST_CARD_3);
        drawPile = new DeckOfCards();
        for (int i = 0; i < 20; i++) {
            drawPile.add(TEST_CARD);
        }
    }

    void setState(StateEnum state) {
        this.state.setState(state);
    }
}
