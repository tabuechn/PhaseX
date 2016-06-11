package actors.message;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;
import model.player.impl.Player;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import model.state.IRoundState;
import model.state.impl.RoundState;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
public class AddToPhaseMessageTest {
    private static final Card TEST_CARD = new Card(CardValue.SEVEN, CardColor.BLUE);
    private AddToPhaseMessage addToPhaseMessage;
    private IRoundState state;
    private IPlayer player;
    private ICardStack stack;
    private List<ICard> card;

    @Before
    public void setUp() {
        state = new RoundState();
        player = new Player(1);
        IDeckOfCards deck = new DeckOfCards();
        for (int i = 0; i < 6; ++i) {
            deck.add(new Card(CardValue.SEVEN, CardColor.BLUE));
        }
        stack = new ColorStack(deck);
        card = new DeckOfCards(Collections.singletonList(TEST_CARD));
        addToPhaseMessage = new AddToPhaseMessage(state, card, stack, player);
    }

    @Test
    public void getterTest() {
        assertEquals(card, addToPhaseMessage.getCard());
        assertEquals(player, addToPhaseMessage.getCurrentPlayer());
        assertEquals(stack, addToPhaseMessage.getStack());
        assertEquals(state, addToPhaseMessage.getRoundState());
    }
}
