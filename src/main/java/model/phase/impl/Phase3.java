package model.phase.impl;

import model.card.CardColor;
import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import model.phase.IPhase;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;

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
public class Phase3 implements IPhase {

    public static final int PHASE_SIZE = 6;
    public static final int PHASE_NUMBER = 3;
    private static final String DESCRIPTION_PHASE_3 = "six cards of one color";

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE_3;
    }

    @Override
    public boolean checkIfDeckFitsToPhase(IDeckOfCards phase) {
        return phase.size() == PHASE_SIZE && sixOfOneColor(phase);
    }

    @Override
    public List<ICardStack> splitPhaseIntoStacks(IDeckOfCards phase) {
        LinkedList<ICardStack> allStacks = new LinkedList<>();
        ICardStack colorStack = new ColorStack(phase);
        allStacks.add(colorStack);
        return allStacks;
    }

    @Override
    public IPhase getNextPhase() {
        return new Phase4();
    }

    @Override
    public int getPhaseNumber() {
        return PHASE_NUMBER;
    }

    private boolean sixOfOneColor(IDeckOfCards phase) {
        CardColor color = phase.get(0).getColor();
        for (ICard card : phase) {
            if (card.getColor() != color) {
                return false;
            }
        }
        return true;
    }
}
