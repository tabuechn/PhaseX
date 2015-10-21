package model.phase.splitter;

import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.phase.IPhaseSplitter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 13.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class DeckSplitter implements IPhaseSplitter {

    private static final int FIRST_ELEMENT = 0;
    private static final int EMPTY_DECK = 0;
    private final int size;
    private final Comparator<ICard> comparator;

    private List<IDeckOfCards> splittedDecks;

    private IDeckOfCards nonStackCards;

    public DeckSplitter(int size, Comparator<ICard> comparator) {
        this.size = size;
        this.comparator = comparator;
    }

    @Override
    public List<IDeckOfCards> split(IDeckOfCards cards) {
        splittedDecks = new LinkedList<>();
        nonStackCards = new DeckOfCards();
        IDeckOfCards copyOfCards = new DeckOfCards(cards);
        IDeckOfCards stack = new DeckOfCards();
        copyOfCards.sort(comparator);
        stack.add(copyOfCards.removeFirst());
        stack = searchForStack(stack, copyOfCards);

        checkStackSize(stack);
        splittedDecks.add(nonStackCards);
        nonStackCards.addAll(copyOfCards);
        return splittedDecks;
    }

    private void checkStackSize(IDeckOfCards stack) {
        if (stack.size() == size) {
            splittedDecks.add(stack);
        } else {
            nonStackCards.addAll(stack);
        }
    }

    private IDeckOfCards searchForStack(IDeckOfCards stack, IDeckOfCards deck) {
        if (deck.size() > EMPTY_DECK && stack.size() < size) {
            ICard testCard = deck.removeFirst();
            if (comparator.compare(testCard, stack.get(FIRST_ELEMENT)) == EMPTY_DECK) {
                stack.add(testCard);
            } else {
                nonStackCards.addAll(stack);
                stack.clear();
                stack.add(testCard);
            }
            searchForStack(stack, deck);
        }
        return stack;
    }

}
