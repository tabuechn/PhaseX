package model.phase.impl;
import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.phase.DeckNotFitException;
import model.phase.IPhase;
import model.phase.IPhaseChecker;
import model.phase.IPhaseSplitter;
import model.phase.checker.ValueChecker;
import model.phase.checker.ColorChecker;
import model.phase.checker.StreetChecker;
import model.phase.splitter.DeckSplitter;
import model.stack.ICardStack;
import model.stack.impl.PairStack;
import model.stack.impl.ColorStack;
import model.stack.impl.StreetStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 *
 * edited: Konraifen88
 * date: 30.09.2015
 * merged phase checker and getter
 * edited: daschwin
 * date: 20.07.2017
 * to be generated
 */
public class Phase3 implements IPhase {

    public static final int PHASE_NUMBER = 3;
	private static final String DESCRIPTION_PHASE = "number quadruple and number triple";
private static final String DOUBLE = "DOUBLE";
private static final String TRIPLE = "TRIPLE";
private static final String QUADRUPLE = "QUADRUPLE";
private IPhaseChecker firstChecker = new ValueChecker(4);
private IPhaseChecker secondChecker = new ValueChecker(3);
private IPhaseSplitter phaseSplitter = new DeckSplitter(4, new CardValueComparator());
    
    
    public Phase3() {
    }

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE;
    }

    @Override
    public List<ICardStack> splitAndCheckPhase(IDeckOfCards phase) throws DeckNotFitException {
List<IDeckOfCards> splitted = phaseSplitter.split(phase);
if (firstChecker.check(splitted.get(0)) && secondChecker.check(splitted.get(1)) ) {
    	return Arrays.asList(new PairStack(splitted.get(0)), new PairStack(splitted.get(1)));
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
    	return true;
    }

    @Override
    public String toString() {
        return "Phase" + PHASE_NUMBER;
    }
}
