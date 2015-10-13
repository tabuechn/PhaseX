package model.phase.impl;

import model.card.CardValue;
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
 *
 * edited: Konraifen88
 * date: 30.09.2015
 * merged phase checker and getter
 */
public class Phase1 implements IPhase {

    public static final int PHASE_NUMBER = 1;
    private static final int SIZE_OF_A_TRIPLE = 3;
    private static final int PHASE_SIZE = 6;
    private static final int NUMBER_OF_TWO_DIFFERENT_TRIPLES = 2;
    private static final int NUMBER_OF_TWO_SAME_TRIPLES = 1;
    private static final int SIZE_OF_ONE_TRIPLE = 3;
    private static final String DESCRIPTION_PHASE_1 = "two number triples";

    private IDeckOfCards setSecondStack(IDeckOfCards restPhase) {
        return restPhase.stream().collect(Collectors.toCollection(DeckOfCards::new));
    }

    @Override
    public String getDescription() {
        return DESCRIPTION_PHASE_1;
    }

    @Override
    public boolean checkIfDeckFitsToPhase(IDeckOfCards phase) {
        return phase.size() == PHASE_SIZE && only2Numbers(phase) && checkTriple(phase);
    }

    @Override
    public List<ICardStack> splitPhaseIntoStacks(IDeckOfCards phase) {
        ICard first = phase.remove(0);
        IDeckOfCards firstTriple = new DeckOfCards();
        firstTriple.add(first);
        for (int i = 0; i < phase.size(); i++) {
            ICard card = phase.get(i);
            if (card.getNumber() == first.getNumber() && firstTriple.size() < SIZE_OF_A_TRIPLE) {
                firstTriple.add(card);
                phase.remove(card);
                i--;
            }
        }
        ICardStack firstTripleStack = new PairStack(firstTriple);
        IDeckOfCards secondTriple = setSecondStack(phase);
        ICardStack secondTripleStack = new PairStack(secondTriple);
        LinkedList<ICardStack> listOfStacks = new LinkedList<>();
        listOfStacks.add(firstTripleStack);
        listOfStacks.add(secondTripleStack);
        return listOfStacks;
    }

    @Override
    public IPhase getNextPhase() {
        return new Phase2();
    }

    @Override
    public int getPhaseNumber() {
        return PHASE_NUMBER;
    }

    private boolean only2Numbers(IDeckOfCards phase) {
        Set<CardValue> presentNumbers = phase.stream().map(ICard::getNumber).collect(Collectors.toSet());
        return presentNumbers.size() == NUMBER_OF_TWO_DIFFERENT_TRIPLES ||
                presentNumbers.size() == NUMBER_OF_TWO_SAME_TRIPLES;
    }

    private boolean checkTriple(IDeckOfCards phase) {
        CardValue tripleNumber = phase.get(0).getNumber();
        List<ICard> triple = phase.stream().filter(card -> card.getNumber() == tripleNumber)
                .collect(Collectors.toCollection(LinkedList::new));
        return triple.size() == SIZE_OF_ONE_TRIPLE || triple.size() == PHASE_SIZE;
    }


}
