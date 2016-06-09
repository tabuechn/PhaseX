package model.stack.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.card.impl.CardDeserializer;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import model.stack.impl.PairStack;
import model.stack.impl.StreetStack;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by tabuechn on 09.06.2016.
 */
public class GsonStackDeserializerTest {
    private ICardStack streetStack;
    private ICardStack pairStack;
    private ICardStack colorStack;

    private IDeckOfCards deck;
    private ICard card;
    private Gson gson;
    private Gson gsonDeserialize;

    @Before
    public void setUp() {
        deck = new DeckOfCards();
        card = new Card(CardValue.FOUR, CardColor.BLUE);
        deck.add(card);
        streetStack = new StreetStack(deck);
        pairStack = new PairStack(deck);
        colorStack = new ColorStack(deck);
        gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ICard.class, new CardDeserializer());
        gsonBuilder.registerTypeAdapter(ICardStack.class, new GsonStackDeserializer());
        gsonDeserialize = gsonBuilder.create();
    }

    @Test
    public void deserializeStreetStack() {
        String streetString = gson.toJson(streetStack);
        ICardStack stack = gsonDeserialize.fromJson(streetString,ICardStack.class);
        assertEquals(stack.getStackType(),streetStack.getStackType());
        assertEquals(stack.getList(),streetStack.getList());
    }

    @Test
    public void deserializePairStack() {
        String streetString = gson.toJson(pairStack);
        ICardStack stack = gsonDeserialize.fromJson(streetString,ICardStack.class);
        assertEquals(stack.getStackType(),pairStack.getStackType());
        assertEquals(stack.getList(),streetStack.getList());
    }

    @Test
    public void deserializeColorStack() {
        String streetString = gson.toJson(colorStack);
        ICardStack stack = gsonDeserialize.fromJson(streetString,ICardStack.class);
        assertEquals(stack.getStackType(),colorStack.getStackType());
        assertEquals(stack.getList(),streetStack.getList());
    }
}
