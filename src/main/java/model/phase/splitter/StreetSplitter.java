package model.phase.splitter;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.phase.IPhaseSplitter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 12.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class StreetSplitter implements IPhaseSplitter {

    private final int size;

    private CardColor avoidColor = CardColor.BACK;

    private List<CardColor> remainingColors;

    private CardValue avoidValue = CardValue.ZERO;

    private IDeckOfCards street;

    /**
     * Constructor to create a streetSplitter.
     *
     * @param size the size needed for the street.
     */
    public StreetSplitter(int size) {
        this.size = size;
    }

    @Override
    public List<IDeckOfCards> split(IDeckOfCards cards) {
        Collections.sort(cards);
        street = new DeckOfCards();
        for (ICard card : cards) {
            ICard tmp = card;
            tmp = checkAvoidance(cards, tmp);
        }


        avoidColor = CardColor.BACK;
        avoidValue = CardValue.ZERO;
        return null;
    }

    private ICard checkAvoidance(IDeckOfCards fullDeck, ICard cardToCheck) {
        if (avoidColor.equals(cardToCheck.getColor())) {
            for (CardColor remainingColor : remainingColors) {
                if (fullDeck.contains(new Card(cardToCheck.getNumber(), remainingColor))) {
                    //TODO continue
                }
            }
        }
        return null;
    }

    public void setAvoidValue(CardValue avoidValue) {
        this.avoidValue = avoidValue;
    }

    public void setAvoidColor(CardColor avoidColor) {
        this.avoidColor = avoidColor;
        remainingColors = Arrays.asList(CardColor.values());
        remainingColors.remove(avoidColor);
    }
}
