package model.stack.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.stack.ICardStack;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;


/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code
 */
public class ColorStackTest {

    public static final Card CARD_1 = new Card(CardValue.FOUR, CardColor.BLUE);
    public static final Card CARD_2 = new Card(CardValue.SIX, CardColor.BLUE);
    public static final Card CARD_3 = new Card(CardValue.TWO, CardColor.BLUE);
    private ColorStack testee;
    private IDeckOfCards deck;

    @Before
    public void setUp() throws Exception {
        deck = new DeckOfCards(Arrays.asList(CARD_1, CARD_2));
        testee = new ColorStack(deck);
    }

    @Test
    public void csTest() {
        assertTrue(testee.getList().size() == 2);
        assertTrue(testee.getStackColor() == CardColor.BLUE);
        testee.addCardToStack(new Card(CardValue.THREE, CardColor.BLUE));
        assertTrue(testee.getList().size() == 3);
        assertTrue(testee.checkCardMatching(new Card(CardValue.EIGHT, CardColor.BLUE)));
        assertFalse(testee.checkCardMatching(new Card(CardValue.EIGHT, CardColor.GREEN)));
    }

    @Test
    public void sameObjectShouldReturnTrueForEquals() {
        assertEquals(testee, testee);
    }

    @Test
    public void sameStackShouldReturnTrueForEquals() {
        ColorStack stack = new ColorStack(deck);
        assertEquals(testee, stack);
    }

    @Test
    public void differentStackShouldReturnFalseForEquals() {
        ICardStack stack = new PairStack(deck);
        assertNotEquals(testee, stack);
    }

    @Test
    public void sameStackWithDifferentCardsShouldReturnFalseForEquals() {
        ColorStack stack = new ColorStack(new DeckOfCards(Arrays.asList(CARD_2, CARD_3)));
        assertNotEquals(testee, stack);
    }

    @Test
    public void setAndGetIDTest() {
        String yolo = "YOLO";
        testee.setId(yolo);
        assertEquals(yolo,testee.getId());
    }

    @Test
    public void hashCodeTest() {
        assertNotNull(testee.hashCode());
    }

}