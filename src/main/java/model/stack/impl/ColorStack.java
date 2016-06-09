package model.stack.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.card.CardColor;
import model.card.ICard;
import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.stack.AbstractStack;
import model.stack.ICardStack;

import java.io.Serializable;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
public class ColorStack extends AbstractStack implements ICardStack, Serializable {

    private final CardColor color;
    @JsonProperty("_id")
    private String id;

    public ColorStack(IDeckOfCards cards) {
        super(StackType.COLOR,cards);
        this.color = cards.get(0).getColor();
    }

    @Override
    public boolean checkCardMatching(ICard card) {
        return card.getColor() == color;
    }

    @Override
    public void addCardToStack(ICard card) {
        list.add(card);
        list.sort(new CardValueComparator());
    }

    CardColor getStackColor() {
        return this.color;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

}
