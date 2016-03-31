package model.phase.checker;

import model.card.CardValue;
import model.card.ICard;
import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.phase.IPhaseChecker;

/**
 * If everything works right this class was
 * created by Konraifen88 on 11.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class ValueChecker implements IPhaseChecker {

    private static final int FIRST_ELEMENT = 0;
    private final int size;

    public ValueChecker(int size) {
        this.size = size;
    }

    @Override
    public boolean check(IDeckOfCards cards) {
        if (cards.size() != this.size) {
            return false;
        }
        cards.sort(new CardValueComparator());
        return allCardsHasTheSameValue(cards);
    }

    private boolean allCardsHasTheSameValue(IDeckOfCards cards) {
        CardValue valueOfDeck = cards.get(FIRST_ELEMENT).getNumber();
        for (ICard card : cards) {
            if (!card.getNumber().equals(valueOfDeck)) {
                return false;
            }
        }
        return true;
    }
}
