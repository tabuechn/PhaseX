package model.card.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
@Entity
@Table(name = "PhaseX_Card5")
public class Card implements ICard, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "PhaseX_CardValue5")
    private CardValue number;
    @Column(name = "PhaseX_CardColor5")
    private CardColor color;

    public Card() {}

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
    public void setColor(CardColor color) {
        this.color = color;
    }

    @Override
    public void setNumber(CardValue value) {
        this.number = value;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
