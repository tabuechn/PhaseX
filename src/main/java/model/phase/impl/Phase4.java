package model.phase.impl;

import model.card.impl.CardValueComparator;
import model.deckOfCards.IDeckOfCards;
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
 *
 * edited: Konraifen88
 * date: 30.09.2015
 * merged phase checker and getter
 */
public class Phase4 implements IPhase {


    public static final int PHASE_NUMBER = 4;

    private static final String DESCRIPTION_PHASE_4 = "number quadruple and number pair";

    private static final int SIZE_OF_A_PAIR = 2;

    private static final int SIZE_OF_A_QUADRUPLE = 4;
    private static final int QUADRUPLE_INDEX = 0;
    private static final int PAIR_INDEX = 1;

    private IPhaseChecker pairPhaseChecker;

    private IPhaseChecker quadruplePhaseChecker;

    private IPhaseSplitter phaseSplitter;

    public Phase4() {
        pairPhaseChecker = new ValueChecker(SIZE_OF_A_PAIR);
        quadruplePhaseChecker = new ValueChecker(SIZE_OF_A_QUADRUPLE);
        phaseSplitter = new DeckSplitter(SIZE_OF_A_QUADRUPLE, new CardValueComparator());
    }

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE_4;
    }

    @Override
    public List<ICardStack> splitPhaseIntoStacks(IDeckOfCards phase) throws IllegalArgumentException {
        List<IDeckOfCards> splitted = phaseSplitter.split(phase);
        if (quadruplePhaseChecker.check(splitted.get(QUADRUPLE_INDEX))
                && pairPhaseChecker.check(splitted.get(PAIR_INDEX))) {
            return Arrays.asList(new PairStack(splitted.get(QUADRUPLE_INDEX)), new PairStack(splitted.get(PAIR_INDEX)));
        }
        throw new IllegalArgumentException();
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
