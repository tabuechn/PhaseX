package model.card.impl;

import model.card.CardColor;
import model.card.ICard;
import model.player.impl.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tarek on 24.09.2015. Be gratefull for this superior Code
 */
public class CardTest {

    private static final ICard CARD_1 = new Card(1, CardColor.YELLOW);
    private static final ICard CARD_2 = new Card(1, CardColor.YELLOW);
    private static final ICard CARD_3 = new Card(2, CardColor.YELLOW);
    private static final ICard CARD_4 = new Card(1, CardColor.GREEN);

    @Test
    public void cardTest() {
        Card x = new Card(5, CardColor.YELLOW);
        assertTrue(x.getColor() == CardColor.YELLOW);
        assertTrue(x.getNumber() == 5);
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
        assertFalse(CARD_1.equals(new Player("testplayer", 3)));
    }

    @Test
    public void hashTest() {
        assertNotNull(CARD_1.hashCode());
    }
}