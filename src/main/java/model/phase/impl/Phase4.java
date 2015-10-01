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
public class Phase4 implements IPhase {


    public static final int SIZE_OF_A_QUADRUPLE = 4;
    public static final int SIZE_OF_PHASE = 6;
    public static final int PAIR_SIZE = 2;
    public static final int QUADRUPLE_SIZE = 4;
    public static final int SAME_PAIRS = 1;
    public static final int PHASE_NUMBER = 4;
    private static final String DESCRIPTION_PHASE_4 = "number quadruple and number pair";

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE_4;
    }

    @Override
    public boolean checkIfDeckFitsToPhase(IDeckOfCards phase) {
        return phase.size() == SIZE_OF_PHASE && only2Numbers(phase) && checkQuadrupleAndPair(phase);
    }

    @Override
    public List<ICardStack> splitPhaseIntoStacks(IDeckOfCards phase) {
        int firstNumber = phase.get(0).getNumber();
        IDeckOfCards firstStack = new DeckOfCards();
        for (int i = 0; i < phase.size(); i++) {
            ICard card = phase.get(i);
            if (card.getNumber() == firstNumber && firstStack.size() < SIZE_OF_A_QUADRUPLE) {
                firstStack.add(card);
                phase.remove(card);
                i--;
            }
        }
        ICardStack firstPairStack = new PairStack(firstStack);
        ICardStack secondPairStack = new PairStack(phase);
        LinkedList<ICardStack> allStacks = new LinkedList<>();
        allStacks.add(firstPairStack);
        allStacks.add(secondPairStack);
        return allStacks;
    }

    @Override
    public IPhase getNextPhase() {
        return new Phase5();
    }

    private boolean only2Numbers(IDeckOfCards phase) {
        Set<Integer> presentNumbers = phase.stream().map(ICard::getNumber).collect(Collectors.toSet());
        return presentNumbers.size() == PAIR_SIZE || presentNumbers.size() == SAME_PAIRS;
    }

    private boolean checkQuadrupleAndPair(IDeckOfCards phase) {
        int tripleNumber = phase.get(0).getNumber();
        List<ICard> pair1 = new LinkedList<>();
        List<ICard> pair2 = new LinkedList<>();
        for (ICard card : phase) {
            if (card.getNumber() == tripleNumber) {
                pair1.add(card);
            } else {
                pair2.add(card);
            }
        }

        return quadrupleAndPairChecker(pair1.size(), pair2.size()) ||
                pairAndQuadrupleChecker(pair1.size(), pair2.size()) || pair1.size() == SIZE_OF_PHASE;
    }

    private boolean quadrupleAndPairChecker(int pair1Size, int pair2Size) {
        return pair1Size == QUADRUPLE_SIZE && pair2Size == PAIR_SIZE;
    }

    private boolean pairAndQuadrupleChecker(int pair1Size, int pair2Size) {
        return pair1Size == PAIR_SIZE && pair2Size == QUADRUPLE_SIZE;
    }

    @Override
    public int getPhaseNumber() {
        return PHASE_NUMBER;
    }
}
