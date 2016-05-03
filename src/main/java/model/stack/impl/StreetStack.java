package model.stack.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.stack.ICardStack;
import model.stack.json.JacksonStackDeserializer;
import model.stack.json.JacksonStackSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
@JsonSerialize(using = JacksonStackSerializer.class)
@JsonDeserialize(using = JacksonStackDeserializer.class)
public class StreetStack implements ICardStack, Serializable {

    private final StackType type;
    private final IDeckOfCards list;
    @JsonProperty("_id")
    private String id;
    private ICard lowestCard;
    private ICard highestCard;

    public StreetStack(IDeckOfCards cards) {
        list = cards;
        list.sort(new CardValueComparator());
        this.lowestCard = list.get(0);
        this.highestCard = list.get(list.size() - 1);
        this.type = StackType.STREET;
    }

    public CardValue getHighestCardNumber() {
        return highestCard.getNumber();
    }

    public CardValue getLowestCardNumber() {
        return lowestCard.getNumber();
    }

    @Override
    public boolean checkCardMatching(ICard card) {
        return card.getNumber().equals(CardValue.byOrdinal(lowestCard.getNumber().ordinal() - 1))
                || card.getNumber().equals(CardValue.byOrdinal(highestCard.getNumber().ordinal() + 1));
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
