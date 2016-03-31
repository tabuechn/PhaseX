package model.phase.splitter;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.card.impl.CardColorComparator;
import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameters;

/**
 * If everything works right this class was
 * created by Konraifen88 on 13.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
@RunWith(Parameterized.class)
public class DeckSplitterTest {

    private static final int NOT_SPLITTED = 1;

    private static final int SPLITTED = 2;
    private static final int STACK_SIZE = 3;
    private static final ICard TEST_CARD_1 = new Card(CardValue.ONE, CardColor.BLUE);
    private static final ICard TEST_CARD_2 = new Card(CardValue.TWO, CardColor.GREEN);
    private final Comparator<ICard> comparator;
    private DeckSplitter testee;
    private IDeckOfCards testDeck;

    public DeckSplitterTest(Comparator<ICard> comparator) {
        this.comparator = comparator;
    }

    @Parameters
    public static Collection<Comparator[]> data() {
        return Arrays.asList(new Comparator[]{new CardValueComparator()},
                new Comparator[]{new CardColorComparator()});
    }

    @Before
    public void setUp() {
        testee = new DeckSplitter(STACK_SIZE, comparator);
        testDeck = new DeckOfCards();
        getFilledTestDeck();
    }

    @Test
    public void cardsOfNormalStartDeckShouldNotBeSplitted() {
        List<IDeckOfCards> tmp = testee.split(testDeck);
        assertEquals(NOT_SPLITTED, tmp.size());
    }

    @Test
    public void correctDeckShouldBeSplitted() {
        testDeck.add(TEST_CARD_1);
        List<IDeckOfCards> tmp = testee.split(testDeck);
        assertEquals(SPLITTED, tmp.size());
    }

    @Test
    public void checkIfStackIsFirstElementInList() {
        testDeck.add(TEST_CARD_1);
        List<IDeckOfCards> tmp = testee.split(testDeck);
        assertEquals(STACK_SIZE, tmp.get(0).size());
    }

    @Test
    public void givenCardsShouldBeAddedAfterFindingElementsBeforeRecursiveMethodFinished() {
        testDeck.add(TEST_CARD_1);
        testDeck.add(TEST_CARD_1);
        testDeck.add(TEST_CARD_2);
        List<IDeckOfCards> tmp = testee.split(testDeck);
        assertEquals(SPLITTED, tmp.size());
        assertEquals(CardValue.ONE, tmp.get(0).get(0).getNumber());
        assertEquals(CardColor.BLUE, tmp.get(0).get(0).getColor());
        assertEquals(4, tmp.get(1).size());
    }

    @Test
    public void checkIfCardsWhichDidNotFitWereAddedToList() {
        testDeck.add(TEST_CARD_2);
        testDeck.add(TEST_CARD_2);
        List<IDeckOfCards> tmp = testee.split(testDeck);
        assertEquals(SPLITTED, tmp.size());
        assertEquals(CardValue.TWO, tmp.get(0).get(0).getNumber());
        assertEquals(CardColor.GREEN, tmp.get(0).get(0).getColor());
        assertEquals(3, tmp.get(1).size());
    }

    private void getFilledTestDeck() {
        testDeck.add(TEST_CARD_1);
        testDeck.add(TEST_CARD_2);
        testDeck.add(TEST_CARD_1);
        testDeck.add(TEST_CARD_2);
    }

}