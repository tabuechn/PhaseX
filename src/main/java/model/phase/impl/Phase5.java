package model.phase.impl;

import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.phase.IPhase;
import model.stack.ICardStack;
import model.stack.impl.PairStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
public class Phase5 implements IPhase {

    public static final int SIZE_OF_A_QUADRUPLE = 4;
    public static final int SAME_PAIR = 1;
    public static final int NUMBER_OF_QUADRUPLES = 2;
    public static final int QUADRUPLE_SIZE = 4;
    public static final int PHASE_NUMBER = 5;
    private static final String DESCRIPTION_PHASE_5 = "two number quadruples";

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE_5;
    }

    @Override
    public boolean checkIfDeckFitsToPhase(IDeckOfCards phase) {
        return phase.size() == QUADRUPLE_SIZE * 2 && only2Numbers(phase) && checkQuadruple(phase);
    }

    @Override
    public List<ICardStack> splitPhaseIntoStacks(IDeckOfCards phase) {
        ICard first = phase.remove(0);
        IDeckOfCards firstQuadruple = new DeckOfCards();
        firstQuadruple.add(first);
        for (int i = 0; i < phase.size(); i++) {
            ICard card = phase.get(i);
            if (card.getNumber() == first.getNumber() && firstQuadruple.size() < SIZE_OF_A_QUADRUPLE) {
                firstQuadruple.add(card);
                phase.remove(card);
                i--;
            }
        }
        ICardStack firstQuadrupleStack = new PairStack(firstQuadruple);
        IDeckOfCards secondQuadruple = new DeckOfCards(phase);
        ICardStack secondQuadrupleStack = new PairStack(secondQuadruple);
        LinkedList<ICardStack> listOfStacks = new LinkedList<>();
        listOfStacks.add(firstQuadrupleStack);
        listOfStacks.add(secondQuadrupleStack);
        return listOfStacks;
    }

    @Override
    public IPhase getNextPhase() {
        return new Phase5();
    }

    @Override
    public int getPhaseNumber() {
        return PHASE_NUMBER;
    }

    private boolean only2Numbers(IDeckOfCards phase) {
        Set<Integer> presentNumbers = phase.stream().map(ICard::getNumber).collect(Collectors.toSet());
        return presentNumbers.size() == NUMBER_OF_QUADRUPLES || presentNumbers.size() == SAME_PAIR;
    }

    private boolean checkQuadruple(IDeckOfCards phase) {
        int quadrupleNumber = phase.get(0).getNumber();
        List<ICard> quadruple = phase.stream().filter(card -> card.getNumber() == quadrupleNumber)
                .collect(Collectors.toCollection(LinkedList::new));
        return quadruple.size() == QUADRUPLE_SIZE || quadruple.size() == QUADRUPLE_SIZE * 2;
    }


}
