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
 * <p>
 * edited: Konraifen88
 * date: 30.09.2015
 * merged phase checker and getter
 */
public class Phase1 implements IPhase {

    public static final int PHASE_NUMBER = 1;
    protected static final String DESCRIPTION_PHASE_1 = "two number triples";
    private static final int SIZE_OF_A_TRIPLE = 3;
    private static final int FIRST_TRIPLE_INDEX = 0;
    private static final int SECOND_TRIPLE_INDEX = 1;

    private IPhaseChecker phaseChecker;

    private IPhaseSplitter phaseSplitter;

    public Phase1() {
        phaseChecker = new ValueChecker(SIZE_OF_A_TRIPLE);
        phaseSplitter = new DeckSplitter(SIZE_OF_A_TRIPLE, new CardValueComparator());
    }

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE_1;
    }

    @Override
    public List<ICardStack> splitPhaseIntoStacks(IDeckOfCards phase) throws IllegalArgumentException {
        List<IDeckOfCards> splitted = phaseSplitter.split(phase);
        if (phaseChecker.check(splitted.get(FIRST_TRIPLE_INDEX))
                && phaseChecker.check(splitted.get(SECOND_TRIPLE_INDEX))) {
            return Arrays.asList(new PairStack(splitted.get(FIRST_TRIPLE_INDEX)),
                    new PairStack(splitted.get(SECOND_TRIPLE_INDEX)));
        }
        throw new IllegalArgumentException();
    }


    @Override
    public IPhase getNextPhase() {
        return new Phase2();
    }

    @Override
    public int getPhaseNumber() {
        return PHASE_NUMBER;
    }


}
