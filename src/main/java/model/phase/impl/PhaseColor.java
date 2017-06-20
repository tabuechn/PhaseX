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
public class PhaseColor  {

    public static final int PHASE_NUMBER = 3;
    public static final int COLOR_SIZE = 6;
    private static final String DESCRIPTION_PHASE_3 = "six cards of one color";
    private IPhaseChecker phaseChecker;

    public PhaseColor() {
        phaseChecker = new ColorChecker(COLOR_SIZE);
    }

    public List<ICardStack> splitAndCheckPhase(IDeckOfCards phase, Integer numberColors) throws DeckNotFitException {
        if (phaseChecker.check(phase)) {
            return Collections.singletonList(new ColorStack(phase));
        }
        throw new DeckNotFitException();
    }
}
