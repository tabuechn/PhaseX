package model.phase;

import model.deck.IDeckOfCards;
import model.stack.ICardStack;

import java.util.List;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 *
 * edited: Konraifen88
 * date: 30.09.2015
 * merged phase checker and getter
 */
public interface IPhase {

    /**
     * Method to get the description for the current phase.
     * @return the description
     */
    String getDescription();

    /**
     * Method to split and check the cards into the card stacks.
     * @param phase the cards of the player which should be played.
     * @return the splitted cardStacks which fit the requirement for current phase.
     */
    List<ICardStack> splitAndCheckPhase(IDeckOfCards phase) throws DeckNotFitException;

    /**
     * Getter for the next phase.
     * @return A new instance for the next phase, returns a new instance of itself when in last phase.
     */
    IPhase getNextPhase();

    /**
     * Getter for the phase as a number
     * @return the phase as a number (for example: Phase5 will return 5);
     */
    int getPhaseNumber();

    /**
     * Checks which kind of checker is uses in this Phase
     * @return true if the Phase utilizes the ColorChecker, false otherwise
     */
    boolean isNumberPhase();



}
