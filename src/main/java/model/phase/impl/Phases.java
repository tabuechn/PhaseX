package model.phase.impl;

import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.phase.DeckNotFitException;
import model.phase.IPhase;
import model.phase.IPhaseChecker;
import model.phase.IPhaseSplitter;
import model.phase.checker.ColorChecker;
import model.phase.checker.ValueChecker;
import model.phase.splitter.DeckSplitter;
import model.stack.ICardStack;
import model.stack.impl.PairStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 *
 * edited: Konraifen88
 * date: 30.09.2015
 * merged phase checker and getter
 */
public class Phases implements IPhase {

    public static final int PHASE_NUMBER = 4;
    private static final String DESCRIPTION_PHASE = "number quadruple and number pair";
    private Boolean isNumberPhase = false;
    private String phaseType = null;
    private String[] numbersType = null;
    private Integer numberColors = 0;
    private Integer streetLenght = 0;

    public Phases() {
        //empty
    }

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE;
    }

    @Override
    public List<ICardStack> splitAndCheckPhase(IDeckOfCards phase) throws DeckNotFitException {

        if(phaseType.equals("NUMBERS")) {
            isNumberPhase = true;
            PhaseNumber phaseNumber = new PhaseNumber();
            return phaseNumber.splitAndCheckPhase(phase, numbersType);
        }
        if(phaseType.equals("COLORS")) {
            PhaseColor phaseNumber = new PhaseColor();
            return phaseNumber.splitAndCheckPhase(phase, numberColors);
        }
        if(phaseType.equals("STREET")) {
            PhaseStreet phaseNumber = new PhaseStreet();
            return phaseNumber.splitAndCheckPhase(phase, streetLenght);
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
        return isNumberPhase;
    }

    @Override
    public String toString() {
        return "Phase"+PHASE_NUMBER;
    }
}
