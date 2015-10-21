package model.phase.impl;

import model.deckOfCards.IDeckOfCards;
import model.phase.DeckNotFitException;
import model.phase.IPhase;
import model.phase.IPhaseChecker;
import model.phase.checker.StreetChecker;
import model.stack.ICardStack;
import model.stack.impl.StreetStack;

import java.util.Collections;
import java.util.List;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 * <p>
 * edited: Konraifen88
 * date: 30.09.2015
 * merged phase checker and getter
 */
public class Phase2 implements IPhase {

    public static final int PHASE_NUMBER = 2;
    public static final int LENGTH_OF_STREET = 6;
    private static final String DESCRIPTION_PHASE_2 = "street of six numbers";
    private IPhaseChecker phaseChecker;

    public Phase2() {
        phaseChecker = new StreetChecker(LENGTH_OF_STREET);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE_2;
    }

    @Override
    public List<ICardStack> splitAndCheckPhase(IDeckOfCards phase) throws DeckNotFitException {
        if (phaseChecker.check(phase)) {
            return Collections.singletonList(new StreetStack(phase));
        }
        throw new DeckNotFitException();
    }

    @Override
    public IPhase getNextPhase() {
        return new Phase3();
    }

    @Override
    public int getPhaseNumber() {
        return PHASE_NUMBER;
    }
}
