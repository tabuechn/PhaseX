package model.stack.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.card.CardValue;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.stack.ICardStack;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
public class PairStack implements ICardStack, Serializable {

    private final StackType type;
    private final CardValue stackNumber;
    private final IDeckOfCards list;
    @JsonProperty("_id")
    private String id;

    public PairStack(IDeckOfCards cards) {
        list = cards;
        this.stackNumber = cards.get(0).getNumber();
        this.type = StackType.PAIR;
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

    @Override
    public StackType getStackType() {
        return this.type;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.type).append(this.list).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (o == this) {
            return true;
        }
        ICardStack other = (ICardStack) o;
        return new EqualsBuilder().append(this.type, other.getStackType()).append(this.list, other.getList())
                .isEquals();
    }
}
