package model.stack.impl;

import model.card.CardValue;
import model.card.ICard;
import model.card.impl.CardValueComparator;
import model.deckOfCards.IDeckOfCards;
import model.stack.ICardStack;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
public class StreetStack implements ICardStack {

    private final IDeckOfCards list;
    private ICard lowestCard;
    private ICard highestCard;

    public StreetStack(IDeckOfCards cards) {
        list = cards;
        list.sort(new CardValueComparator());
        this.lowestCard = list.get(0);
        this.highestCard = list.get(list.size() - 1);
    }

    public CardValue getHighestCardNumber() {
        return highestCard.getNumber();
    }

    public CardValue getLowestCardNumber() {
        return lowestCard.getNumber();
    }

    @Override
    public boolean checkCardMatching(ICard card) {
        return (card.getNumber().equals(CardValue.byOrdinal(lowestCard.getNumber().ordinal() - 1))
                || card.getNumber().equals(CardValue.byOrdinal(highestCard.getNumber().ordinal() + 1)));
    }

    @Override
    public void addCardToStack(ICard card) {
        list.add(card);
        list.sort(new CardValueComparator());
        if (card.compareTo(this.highestCard) > 0) {
            this.highestCard = card;
        } else {
            this.lowestCard = card;
        }
    }

    @Override
    public IDeckOfCards getList() {
        return this.list;
    }

}
