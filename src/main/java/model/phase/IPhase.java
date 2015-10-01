package model.phase;

import model.deckOfCards.IDeckOfCards;
import model.stack.ICardStack;

import java.util.List;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 */

/**
 * edited: merged phase checker & getter
 *
 * If everything works right this class was
 * created by Konraifen88 on 30.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface IPhase {

    String getDescription();

    boolean checkIfDeckFitsToPhase(IDeckOfCards cards);

    List<ICardStack> splitPhaseIntoStacks(IDeckOfCards phase);

    IPhase getNextPhase();

    int getPhaseNumber();
}
