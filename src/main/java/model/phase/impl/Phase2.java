package model.phase.impl;

import model.card.impl.CardComparator;
import model.deckOfCards.IDeckOfCards;
import model.phase.IPhase;
import model.stack.ICardStack;
import model.stack.impl.StreetStack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 */

/**
 * edited: merged phase checker & getter
 * <p>
 * If everything works right this class was
 * created by Konraifen88 on 30.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class Phase2 implements IPhase {

    public static final int SIZE_OF_PHASE = 6;
    public static final int PHASE_NUMBER = 2;
    private static final String DESCRIPTION_PHASE_2 = "street of six numbers";

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE_2;
    }

    @Override
    public boolean checkIfDeckFitsToPhase(IDeckOfCards phase) {
        return phase.size() == SIZE_OF_PHASE && streetOfSix(phase);
    }

    @Override
    public List<ICardStack> splitPhaseIntoStacks(IDeckOfCards phase) {
        ICardStack streetStack = new StreetStack(phase);
        LinkedList<ICardStack> allStacks = new LinkedList<>();
        allStacks.add(streetStack);
        return allStacks;
    }

    @Override
    public IPhase getNextPhase() {
        return new Phase3();
    }

    @Override
    public int getPhaseNumber() {
        return PHASE_NUMBER;
    }

    private boolean streetOfSix(IDeckOfCards phase) {
        Collections.sort(phase, new CardComparator());
        int counter = phase.get(0).getNumber();
        for (int i = 1; i < phase.size(); i++) {
            if (phase.get(i).getNumber() != (counter + 1)) {
                return false;
            }
            counter = phase.get(i).getNumber();
        }
        return true;
    }
}
