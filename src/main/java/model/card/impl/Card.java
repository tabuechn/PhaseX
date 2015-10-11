package model.card.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.Nonnull;

/**
 * Created by Tarek on 22.09.2015. Be gratefull for this superior Code
 */
public class Card implements ICard {
    private CardValue number;
    private CardColor color;

    public Card(CardValue cardNumber, CardColor cardColor) {
        this.number = cardNumber;
        this.color = cardColor;
    }

    @Override
    public CardValue getNumber() {
        return number;
    }

    @Override
    public CardColor getColor() {
        return color;
    }

    @Override
    public int compareTo(@Nonnull ICard other) {
        return this.number.ordinal() - other.getNumber().ordinal();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.number).append(this.color).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (o == this) {
            return true;
        }
        Card other = (Card) o;
        return new EqualsBuilder().append(this.number, other.getNumber()).append(this.color, other.getColor())
                .isEquals();
    }


}
