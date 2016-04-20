package model.phase.impl;

import model.deck.IDeckOfCards;
import model.phase.DeckNotFitException;
import model.phase.IPhase;
import model.phase.IPhaseChecker;
import model.phase.checker.ColorChecker;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;

import java.util.Collections;
import java.util.List;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 * <p>
 * edited: Konraifen88
 * date: 30.09.2015
 * merged phase checker and getter
 */
public class Phase3 implements IPhase {

    public static final int PHASE_NUMBER = 3;
    public static final int PAIR_SIZE = 6;
    private static final String DESCRIPTION_PHASE_3 = "six cards of one color";
    private IPhaseChecker phaseChecker;

    public Phase3() {
        phaseChecker = new ColorChecker(PAIR_SIZE);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE_3;
    }

    @Override
    public List<ICardStack> splitAndCheckPhase(IDeckOfCards phase) throws DeckNotFitException {
        if (phaseChecker.check(phase)) {
            return Collections.singletonList(new ColorStack(phase));
        }
        throw new DeckNotFitException();
    }

    @Override
    public IPhase getNextPhase() {
        return new Phase4();
    }

    @Override
    public int getPhaseNumber() {
        return PHASE_NUMBER;
    }

    @Override
    public boolean isNumberPhase() {
        return phaseChecker instanceof ColorChecker;
    }

    @Override
    public String toString() {
        return "Phase3";
    }
}
