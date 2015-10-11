package model.phase.checker;

import model.card.CardColor;
import model.card.ICard;
import model.card.impl.CardColorComporator;
import model.deckOfCards.IDeckOfCards;
import model.phase.IPhaseChecker;

/**
 * If everything works right this class was
 * created by Konraifen88 on 11.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class ColorChecker implements IPhaseChecker {

    private final int size;

    public ColorChecker(int size) {
        this.size = size;
    }

    @Override
    public boolean check(IDeckOfCards cards) {
        if (cards.size() != this.size) {
            return false;
        }
        cards.sort(new CardColorComporator());
        return allCardsHasTheSameColor(cards);
    }

    private boolean allCardsHasTheSameColor(IDeckOfCards cards) {
        CardColor colorOfDeck = cards.get(0).getColor();
        for (ICard card : cards) {
            if (!card.getColor().equals(colorOfDeck)) {
                return false;
            }
        }
        return true;
    }
}
