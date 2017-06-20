package model.phase.impl;

import model.deck.IDeckOfCards;
import model.phase.DeckNotFitException;
import model.phase.IPhase;
import model.phase.IPhaseChecker;
import model.phase.checker.ColorChecker;
import model.phase.checker.StreetChecker;
import model.stack.ICardStack;
import model.stack.impl.StreetStack;

import java.util.Collections;
import java.util.List;


public class PhaseStreet {

    public static final int PHASE_NUMBER = 2;
    public static final int LENGTH_OF_STREET = 6;
    private static final String DESCRIPTION_PHASE_2 = "street of six numbers";
    private IPhaseChecker phaseChecker;

    public PhaseStreet() {
        phaseChecker = new StreetChecker(LENGTH_OF_STREET);
    }

    public List<ICardStack> splitAndCheckPhase(IDeckOfCards phase, Integer streetLenght) throws DeckNotFitException {
        if (phaseChecker.check(phase)) {
            return Collections.singletonList(new StreetStack(phase));
        }
        throw new DeckNotFitException();
    }
}
