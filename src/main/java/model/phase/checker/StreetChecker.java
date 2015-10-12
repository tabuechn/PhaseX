package model.phase.checker;

import model.card.ICard;
import model.card.impl.CardValueComparator;
import model.deckOfCards.IDeckOfCards;
import model.phase.IPhaseChecker;

import java.util.Collections;

/**
 * If everything works right this class was
 * created by Konraifen88 on 11.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class StreetChecker implements IPhaseChecker {

    private final int size;

    public StreetChecker(int size) {
        this.size = size;
    }

    @Override
    public boolean check(IDeckOfCards cards) {
        Collections.sort(cards, new CardValueComparator());
        return cards.size() == this.size && checkStreet(cards.removeFirst(), cards);
    }

    private boolean checkStreet(ICard smaller, IDeckOfCards rest) {
        boolean isStreet;
        ICard greater = rest.removeFirst();
        isStreet = compareCards(smaller, greater);
        if (!rest.isEmpty()) {
            isStreet = checkStreet(greater, rest);
        }
        return isStreet;
    }

    private boolean compareCards(ICard smaller, ICard greater) {
        return greater.compareTo(smaller) == 1;
    }
}
