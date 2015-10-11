package model.phase;

import model.deckOfCards.IDeckOfCards;

/**
 * If everything works right this class was
 * created by Konraifen88 on 11.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface IPhaseChecker {

    /**
     * Method to check if the given deck fits to specification of the current phase.
     *
     * @param cards the given deck which should be checked.
     * @return true if the deck fits to the specification, false if not.
     */
    boolean check(IDeckOfCards cards);

}
