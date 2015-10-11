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

    private boolean isStreet;

    public StreetChecker(int size) {
        this.size = size;
    }

    @Override
    public boolean check(IDeckOfCards cards) {
        Collections.sort(cards, new CardValueComparator());
        if (cards.size() != this.size) {
            return false;
        }
        isStreet = true;
        return checkStreet(cards.removeFirst(), cards);
    }

    private boolean checkStreet(ICard smaller, IDeckOfCards rest) {
        ICard greater = rest.removeFirst();
        callMethodTillDeckIsEmpty(rest, greater);

        compareCards(smaller, greater);
        return isStreet;
    }

    private void callMethodTillDeckIsEmpty(IDeckOfCards rest, ICard greater) {
        if (!rest.isEmpty()) {
            checkStreet(greater, rest);
        }
    }

    private void compareCards(ICard smaller, ICard greater) {
        if (smaller.getNumber() + 1 == greater.getNumber()) {
            return;
        }
        isStreet = false;
    }
}
