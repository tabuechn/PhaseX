package model.stack.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.impl.DeckOfCards;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code
 */
public class StreetStackTest {

    private StreetStack testee;

    @Before
    public void setUp() throws Exception {
        DeckOfCards cardList = new DeckOfCards();
        cardList.add(new Card(CardValue.ONE, CardColor.BLUE));
        cardList.add(new Card(CardValue.TWO, CardColor.BLUE));
        cardList.add(new Card(CardValue.THREE, CardColor.BLUE));
        testee = new StreetStack(cardList);
    }

    @Test
    public void ssTest() {
        assertEquals(testee.getList().size(), 3);
        assertEquals(testee.getHighestCardNumber(), CardValue.THREE);
        assertEquals(testee.getLowestCardNumber(), CardValue.ONE);
        assertTrue(testee.checkCardMatching(new Card(CardValue.FOUR, CardColor.GREEN)));
        assertTrue(testee.checkCardMatching(new Card(CardValue.ZERO, CardColor.GREEN)));
        assertFalse(testee.checkCardMatching(new Card(CardValue.NINE, CardColor.GREEN)));
    }

    @Test
    public void addCardToStackTest() {
        assertEquals(testee.getList().size(), 3);
        assertEquals(testee.getHighestCardNumber(), CardValue.THREE);
        assertEquals(testee.getLowestCardNumber(), CardValue.ONE);
        testee.addCardToStack(new Card(CardValue.FOUR, CardColor.GREEN));
        assertEquals(testee.getList().size(), 4);
        assertEquals(testee.getHighestCardNumber(), CardValue.FOUR);
        assertEquals(testee.getLowestCardNumber(), CardValue.ONE);
        testee.addCardToStack(new Card(CardValue.ZERO, CardColor.GREEN));
        assertEquals(testee.getList().size(), 5);
        assertEquals(testee.getHighestCardNumber(), CardValue.FOUR);
        assertEquals(testee.getLowestCardNumber(), CardValue.ZERO);
    }

    @Test
    public void setAndGetIDTest() {
        String yolo = "YOLO";
        testee.setId(yolo);
        Assert.assertEquals(yolo,testee.getId());
    }

    @Test
    public void getStackType() {
        Assert.assertEquals(testee.getStackType(),StackType.STREET);
    }

    @Test
    public void hashCodeTest() {
        assertNotNull(testee.hashCode());
    }

    @Test
    public void equalsWithNullShouldReturnFalse() {
        Assert.assertFalse(testee.equals(null));
    }

    @Test
    public void equalsWithSelfShouldReturnTrue() {
        assertTrue(testee.equals(testee));
    }

    @Test
    public void equalsWithOtherObjectShouldReturnFalse() {
        DeckOfCards deck = new DeckOfCards();
        deck.add(new Card(CardValue.FOUR,CardColor.BLUE));
        Assert.assertFalse(testee.equals(new StreetStack(deck)));
    }
}