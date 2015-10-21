package model.card.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.player.impl.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code
 */
public class CardTest {

    private static final CardValue CARD_NUMBER_ONE = CardValue.ONE;
    private static final CardValue CARD_NUMBER_TWO = CardValue.TWO;
    private static final CardValue CARD_NUMBER_FIVE = CardValue.FIVE;

    private static final ICard CARD_1 = new Card(CARD_NUMBER_ONE, CardColor.YELLOW);
    private static final ICard CARD_2 = new Card(CARD_NUMBER_ONE, CardColor.YELLOW);
    private static final ICard CARD_3 = new Card(CARD_NUMBER_TWO, CardColor.YELLOW);
    private static final ICard CARD_4 = new Card(CARD_NUMBER_ONE, CardColor.GREEN);

    @Test
    public void cardTest() {
        Card x = new Card(CARD_NUMBER_FIVE, CardColor.YELLOW);
        assertTrue(x.getColor() == CardColor.YELLOW);
        assertTrue(x.getNumber().equals(CardValue.FIVE));
    }

    @Test
    public void equalsOnSameObjectShouldReturnTrue() {
        assertEquals(CARD_1, CARD_1);
    }

    @Test
    public void equalsOnTwoEquivalentCardsShouldReturnTrue() {
        assertEquals(CARD_1, CARD_2);
    }

    @Test
    public void equalsWithDifferentNumberShouldReturnFalse() {
        assertNotEquals(CARD_1, CARD_3);
    }

    @Test
    public void equalsWithDifferentColorShouldReturnFalse() {
        assertNotEquals(CARD_1, CARD_4);
    }

    @Test
    public void equalsWithDifferentClassShouldReturnFalse() {
        assertFalse(CARD_1.equals(new Player("testPlayer", 3)));
    }

    @Test
    public void hashTest() {
        assertNotNull(CARD_1.hashCode());
    }
}