package model.phase.checker;

import model.card.CardColor;
import model.card.ICard;
import model.card.impl.CardColorComparator;
import model.deck.IDeckOfCards;
import model.phase.IPhaseChecker;

/**
 * If everything works right this class was
 * created by Konraifen88 on 11.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class ColorChecker implements IPhaseChecker {

    private static final int FIRST_ELEMENT = 0;
    private final int size;

    public ColorChecker(int size) {
        this.size = size;
    }

    @Override
    public boolean check(IDeckOfCards cards) {
        return cards.size() == this.size && allCardsHasTheSameColor(cards);
    }

    private boolean allCardsHasTheSameColor(IDeckOfCards cards) {
        cards.sort(new CardColorComparator());
        CardColor colorOfDeck = cards.get(FIRST_ELEMENT).getColor();
        for (ICard card : cards) {
            if (!card.getColor().equals(colorOfDeck)) {
                return false;
            }
        }
        return true;
    }
}
