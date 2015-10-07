package model.phase;

import model.deckOfCards.IDeckOfCards;
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

    String getDescription();

    boolean checkIfDeckFitsToPhase(IDeckOfCards cards);

    List<ICardStack> splitPhaseIntoStacks(IDeckOfCards phase);

    IPhase getNextPhase();

    int getPhaseNumber();
}
