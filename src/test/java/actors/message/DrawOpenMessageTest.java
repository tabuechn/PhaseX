package actors.message;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;
import model.player.impl.Player;
import model.state.IRoundState;
import model.state.impl.RoundState;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
public class DrawOpenMessageTest {
    private DrawOpenMessage drawOpenMessage;
    private IRoundState state;
    private IPlayer player;
    private ICardStack stack;
    private ICard card;
    private IDeckOfCards deck;

    @Before
    public void setUp() {
        state = new RoundState();
        player = new Player(1);
        deck = new DeckOfCards();
        for (int i = 0; i < 6; ++i) {
            deck.add(new Card(CardValue.SEVEN, CardColor.BLUE));
        }
        stack = new ColorStack(deck);
        card = new Card(CardValue.SEVEN, CardColor.BLUE);
        drawOpenMessage = new DrawOpenMessage(deck, deck, player, state);
    }

    @Test
    public void getterTest() {
        assertEquals(deck, drawOpenMessage.getHand());
        assertEquals(deck, drawOpenMessage.getPile());
        assertEquals(player, drawOpenMessage.getCurrentPlayer());
        assertEquals(state, drawOpenMessage.getRoundState());
    }
}
