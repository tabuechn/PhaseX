package model.stack.impl;

import model.card.CardColor;
import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import model.stack.ICardStack;

/**
 * Created by Tarek on 22.09.2015. Be gratefull for this superior Code
 */
public class ColorStack implements ICardStack {

    private CardColor color;

    private IDeckOfCards list;

    public ColorStack(IDeckOfCards cards) {
        list = cards;
        this.color = cards.get(0).getColor();
    }

    @Override
    public boolean checkCardMatching(ICard card) {
        return card.getColor() == color;
    }

    @Override
    public void addCardToStack(ICard card) {
        list.add(card);
    }

    @Override
    public IDeckOfCards getList() {
        return this.list;
    }

    public CardColor getStackColor() {
        return this.color;
    }
}
