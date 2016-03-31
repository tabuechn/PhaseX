package model.stack.impl;

import model.card.CardColor;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.stack.ICardStack;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
public class ColorStack implements ICardStack {

    private final CardColor color;

    private final IDeckOfCards list;

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
