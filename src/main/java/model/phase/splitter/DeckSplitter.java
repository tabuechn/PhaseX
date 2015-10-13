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

    private final int size;
    private final Comparator comparator;

    List<IDeckOfCards> splittedDecks;

    IDeckOfCards nonStackCards;

    public DeckSplitter(int size, Comparator comparator) {
        this.size = size;
        this.comparator = comparator;
    }

    @Override
    public List<IDeckOfCards> split(IDeckOfCards cards) {
        splittedDecks = new LinkedList<>();
        nonStackCards = new DeckOfCards();
        IDeckOfCards stack = new DeckOfCards();
        cards.sort(comparator);
        stack.add(cards.removeFirst());
        stack = searchForValueStack(stack, cards);

        checkStackSize(stack);
        splittedDecks.add(nonStackCards);
        return splittedDecks;
    }

    private void checkStackSize(IDeckOfCards stack) {
        if (stack.size() == size) {
            splittedDecks.add(stack);
        } else {
            nonStackCards.addAll(stack);
        }
    }

    private IDeckOfCards searchForValueStack(IDeckOfCards stack, IDeckOfCards deck) {
        if (deck.size() > 0 && stack.size() < size) {
            ICard testCard = deck.removeFirst();
            if (comparator.compare(testCard, stack.get(0)) == 0) {
                stack.add(testCard);
            } else {
                nonStackCards.addAll(stack);
                stack.clear();
                stack.add(testCard);
            }
            searchForValueStack(stack, deck);
        }
        return stack;
    }

}
