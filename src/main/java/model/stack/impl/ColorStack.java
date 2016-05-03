package model.stack.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import model.card.CardColor;
import model.card.ICard;
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
public class ColorStack implements ICardStack, Serializable {

    private final StackType type;
    private final CardColor color;
    private final IDeckOfCards list;
    @JsonProperty("_id")
    private String id;

    public ColorStack(IDeckOfCards cards) {
        list = cards;
        this.color = cards.get(0).getColor();
        type = StackType.COLOR;
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

    @Override
    public StackType getStackType() {
        return this.type;
    }

    public CardColor getStackColor() {
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
