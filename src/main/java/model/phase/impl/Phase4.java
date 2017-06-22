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
public class Phase4 implements IPhase {

    public static final int PHASE_NUMBER = 4;
	private static final String DESCRIPTION_PHASE = "street of six numbers in a row";
    private Integer streetLenght = 6;
    private IPhaseChecker phaseChecker;
    
    
    public Phase4() {
    phaseChecker = new StreetChecker(streetLenght);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE;
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
    	return new Phase5();
    }

    @Override
    public int getPhaseNumber() {
    	return PHASE_NUMBER;
    }

    @Override
    public boolean isNumberPhase() {
    	return false;
    }

    @Override
    public String toString() {
        return "Phase" + PHASE_NUMBER;
    }
}
