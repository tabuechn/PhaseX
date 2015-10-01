package model.stack.impl;

import model.card.ICard;
import model.card.impl.CardComparator;
import model.deckOfCards.IDeckOfCards;
import model.stack.ICardStack;

/**
 * Created by Tarek on 22.09.2015. Be gratefull for this superior Code
 */
public class StreetStack implements ICardStack {

    private int lowestCardNumber;
    private int highestCardNumber;

    private IDeckOfCards list;

    public StreetStack(IDeckOfCards cards) {
        list = cards;
        list.sort(new CardComparator());
        lowestCardNumber = list.get(0).getNumber();
        highestCardNumber = list.get(list.size() - 1).getNumber();
    }

    public int getHighestCardNumber() {
        return highestCardNumber;
    }

    public int getLowestCardNumber() {
        return lowestCardNumber;
    }

    @Override
    public boolean checkCardMatching(ICard card) {
        return (card.getNumber() == (lowestCardNumber-1) || card.getNumber() == (highestCardNumber+1)) ;
    }

    @Override
    public void addCardToStack(ICard card) {
        list.add(card);
        list.sort(new CardComparator());
        if(card.getNumber() > this.highestCardNumber) {
            increaseHighestCardNumber();
        } else {
            decreaseLowestCardNumber();
        }
    }

    private void decreaseLowestCardNumber() {
        this.lowestCardNumber--;
    }

    private void increaseHighestCardNumber() {
        this.highestCardNumber++;
    }

    @Override
    public IDeckOfCards getList() {
        return this.list;
    }
}
