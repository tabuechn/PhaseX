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
public class PairStackTest {

    PairStack ps;

    @Before
    public void setUp() throws Exception {
        DeckOfCards cardList = new DeckOfCards();
        cardList.add(new Card(4, CardColor.BLUE));
        cardList.add(new Card(4,CardColor.GREEN));
        cardList.add(new Card(4,CardColor.RED));
        ps = new PairStack(cardList);
    }

    @Test
    public void csTest() {
        assertTrue(ps.getList().size() == 3);
        assertTrue(ps.getStackNumber()== 4);
        ps.addCardToStack(new Card(4, CardColor.BLUE));
        assertTrue(ps.getList().size() == 4);
        assertTrue(ps.checkCardMatching(new Card(4, CardColor.BLUE)));
        assertFalse(ps.checkCardMatching(new Card(8, CardColor.GREEN)));
    }
}