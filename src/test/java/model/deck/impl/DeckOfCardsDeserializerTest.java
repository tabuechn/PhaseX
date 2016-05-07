package model.deck.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertTrue;

/**
 * If everything works right this class was
 * created by konraifen88 on 07.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class DeckOfCardsDeserializerTest {

    private static final Card CARD_1 = new Card(CardValue.EIGHT, CardColor.BLUE);
    private static final Card CARD_2 = new Card(CardValue.TEN, CardColor.GREEN);
    private IDeckOfCards testCards;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        testCards = new DeckOfCards(Arrays.asList(CARD_1, CARD_2));
        mapper = new ObjectMapper();
    }

    @Test
    public void serialize() throws Exception {
        String json = mapper.writeValueAsString(testCards);
        assertTrue(json.contains("\"color\""));
        assertTrue(json.contains("\"value\""));
    }

    @Test
    public void deserialize() throws Exception {
        IDeckOfCards cards = mapper.readValue("[{\"color\":2,\"value\":8},{\"color\":3,\"value\":10}]", IDeckOfCards.class);
        assertTrue(cards.contains(CARD_1));
        assertTrue(cards.contains(CARD_2));
    }
}