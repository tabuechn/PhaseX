package model.stack.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.card.CardValue;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.stack.AbstractStack;
import model.stack.ICardStack;
import model.stack.StackType;

import java.io.Serializable;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */

/**
 * Two different stacks can not be equals, because the abstract implementation uses the StackType Enum
 */
@SuppressWarnings("squid:S2160")
public class PairStack extends AbstractStack implements ICardStack, Serializable {


    private final CardValue stackNumber;

    @JsonProperty("_id")
    private String id;

    public PairStack(IDeckOfCards cards) {
        super(StackType.PAIR,cards);
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
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    CardValue getStackNumber() {
        return stackNumber;
    }

}
