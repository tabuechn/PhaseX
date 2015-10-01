package model.stack.impl;

import model.card.CardColor;
import model.card.impl.Card;
import model.deckOfCards.impl.DeckOfCards;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Tarek on 24.09.2015. Be gratefull for this superior Code
 */
public class ColorStackTest {

    ColorStack cs;

    @Before
    public void setUp() throws Exception {
        DeckOfCards cardList = new DeckOfCards();
        cardList.add(new Card(4, CardColor.BLUE));
        cardList.add(new Card(6,CardColor.BLUE));
        cardList.add(new Card(1,CardColor.BLUE));
        cs = new ColorStack(cardList);
    }

    @Test
    public void csTest() {
        assertTrue(cs.getList().size() == 3);
        assertTrue(cs.getStackColor()== CardColor.BLUE);
        cs.addCardToStack(new Card(3, CardColor.BLUE));
        assertTrue(cs.getList().size() == 4);
        assertTrue(cs.checkCardMatching(new Card(8, CardColor.BLUE)));
        assertFalse(cs.checkCardMatching(new Card(8, CardColor.GREEN)));
    }


}