package model.phase.impl;

import model.card.impl.CardValueComparator;
import model.deckOfCards.IDeckOfCards;
import model.phase.DeckNotFitException;
import model.phase.IPhase;
import model.phase.IPhaseChecker;
import model.phase.IPhaseSplitter;
import model.phase.checker.ValueChecker;
import model.phase.splitter.DeckSplitter;
import model.stack.ICardStack;
import model.stack.impl.PairStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 * <p>
 * edited: Konraifen88
 * date: 30.09.2015
 * merged phase checker and getter
 */
public class Phase5 implements IPhase {

    public static final int PHASE_NUMBER = 5;
    private static final String DESCRIPTION_PHASE_5 = "two number quadruples";

    private static final int SIZE_OF_A_QUADRUPLE = 4;
    private static final int FIRST_QUADRUPLE_INDEX = 0;
    private static final int SECOND_QUADRUPLE_INDEX = 1;

    private IPhaseChecker phaseChecker;

    private IPhaseSplitter phaseSplitter;

    public Phase5() {
        phaseChecker = new ValueChecker(SIZE_OF_A_QUADRUPLE);
        phaseSplitter = new DeckSplitter(SIZE_OF_A_QUADRUPLE, new CardValueComparator());
    }

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE_5;
    }

    @Override
    public List<ICardStack> splitAndCheckPhase(IDeckOfCards phase) throws DeckNotFitException {
        List<IDeckOfCards> splitted = phaseSplitter.split(phase);
        if (phaseChecker.check(splitted.get(FIRST_QUADRUPLE_INDEX))
                && phaseChecker.check(splitted.get(SECOND_QUADRUPLE_INDEX))) {
            return Arrays.asList(new PairStack(splitted.get(FIRST_QUADRUPLE_INDEX)),
                    new PairStack(splitted.get(SECOND_QUADRUPLE_INDEX)));
        }
        throw new DeckNotFitException();
    }

    @Override
    public IPhase getNextPhase() {
        return new Phase5();
    }

    @Override
    public int getPhaseNumber() {
        return PHASE_NUMBER;
    }

}
