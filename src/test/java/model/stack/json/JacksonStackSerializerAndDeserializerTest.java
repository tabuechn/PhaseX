package model.stack.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import model.stack.impl.PairStack;
import model.stack.impl.StreetStack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameters;

/**
 * If everything works right this class was
 * created by konraifen88 on 13.06.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
@RunWith(Parameterized.class)
public class JacksonStackSerializerAndDeserializerTest {

    private ICardStack testeeStack;
    private String testeeJson;
    private ObjectMapper mapper;

    public JacksonStackSerializerAndDeserializerTest(ICardStack stack, String json) {
        testeeStack = stack;
        testeeJson = json;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new StreetStack(getThreeCardsForTest()), "{\"type\":1,\"cards\":[{\"color\":3,\"value\":8},{\"color\":3,\"value\":8},{\"color\":3,\"value\":8}]}"},
                {new ColorStack(getThreeCardsForTest()), "{\"type\":2,\"cards\":[{\"color\":3,\"value\":8},{\"color\":3,\"value\":8},{\"color\":3,\"value\":8}]}"},
                {new PairStack(getThreeCardsForTest()), "{\"type\":0,\"cards\":[{\"color\":3,\"value\":8},{\"color\":3,\"value\":8},{\"color\":3,\"value\":8}]}"}
        });
    }

    private static IDeckOfCards getThreeCardsForTest() {
        IDeckOfCards deck = new DeckOfCards();
        for (int i = 0; i < 2 + 1; i++) {
            deck.add(new Card(CardValue.EIGHT, CardColor.GREEN));
        }
        return deck;
    }

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
    }

    @Test
    public void serialize() throws Exception {
        String json = mapper.writeValueAsString(testeeStack);
        assertEquals(testeeJson, json);
    }

    @Test
    public void deserialize() throws Exception {
        ICardStack stack = mapper.readValue(testeeJson, ICardStack.class);
        assertEquals(testeeStack.getList(), stack.getList());
        assertEquals(testeeStack.getStackType(), stack.getStackType());
    }

}