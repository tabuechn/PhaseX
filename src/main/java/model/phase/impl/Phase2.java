package model.phase.impl;

import model.card.CardValue;
import model.card.impl.CardValueComparator;
import model.deckOfCards.IDeckOfCards;
import model.phase.IPhase;
import model.stack.ICardStack;
import model.stack.impl.StreetStack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 * <p>
 * edited: Konraifen88
 * date: 30.09.2015
 * merged phase checker and getter
 */
public class Phase2 implements IPhase {

    public static final int PHASE_NUMBER = 2;
    private static final int SIZE_OF_PHASE = 6;
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
        Collections.sort(phase, new CardValueComparator());
        CardValue counter = phase.get(0).getNumber();
        for (int i = 1; i < phase.size(); i++) {
            if (!phase.get(i).getNumber().equals(CardValue.byOrdinal(counter.ordinal() + 1))) {
                return false;
            }
            counter = phase.get(i).getNumber();
        }
        return true;
    }
}
