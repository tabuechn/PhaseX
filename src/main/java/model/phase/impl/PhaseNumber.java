package model.phase.impl;

import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.phase.DeckNotFitException;
import model.phase.IPhaseChecker;
import model.phase.IPhaseSplitter;
import model.phase.checker.ValueChecker;
import model.phase.splitter.DeckSplitter;
import model.stack.ICardStack;
import model.stack.impl.PairStack;

import java.util.Arrays;
import java.util.List;


public class PhaseNumber {

    private static final int SIZE_OF_A_PAIR = 2;

    private static final int SIZE_OF_A_QUADRUPLE = 4;
    private static final int QUADRUPLE_INDEX = 0;
    private static final int PAIR_INDEX = 1;

    private IPhaseChecker pairPhaseChecker;

    private IPhaseChecker quadruplePhaseChecker;

    private IPhaseSplitter phaseSplitter;

    public PhaseNumber() {
        pairPhaseChecker = new ValueChecker(SIZE_OF_A_PAIR);
        quadruplePhaseChecker = new ValueChecker(SIZE_OF_A_QUADRUPLE);
        phaseSplitter = new DeckSplitter(SIZE_OF_A_QUADRUPLE, new CardValueComparator());
    }

    public List<ICardStack> splitAndCheckPhase(IDeckOfCards phase, String[] numberTypes) throws DeckNotFitException {
        List<IDeckOfCards> splitted = phaseSplitter.split(phase);
        if (quadruplePhaseChecker.check(splitted.get(QUADRUPLE_INDEX))
                && pairPhaseChecker.check(splitted.get(PAIR_INDEX))) {
            return Arrays.asList(new PairStack(splitted.get(QUADRUPLE_INDEX)), new PairStack(splitted.get(PAIR_INDEX)));
        }
        throw new DeckNotFitException();
    }

}
