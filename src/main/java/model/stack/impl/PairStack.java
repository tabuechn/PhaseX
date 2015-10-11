package model.stack.impl;

import model.card.CardValue;
import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import model.stack.ICardStack;

/**
 * Created by Tarek on 22.09.2015. Be gratefull for this superior Code
 */
public class PairStack implements ICardStack {
    private CardValue stackNumber;

    private IDeckOfCards list;

    public PairStack(IDeckOfCards cards) {
        list = cards;
        this.stackNumber = cards.get(0).getNumber();
    }

    @Override
    public boolean checkCardMatching(ICard card) {
        return card.getNumber() == stackNumber;
    }

    @Override
    public void addCardToStack(ICard card) {
        list.add(card);
    }

    @Override
    public IDeckOfCards getList() {
        return list;
    }

    public CardValue getStackNumber() {
        return stackNumber;
    }
}
