package model.card.impl;

import model.card.CardColor;
import model.card.ICard;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Tarek on 22.09.2015. Be gratefull for this superior Code
 */
public class Card implements ICard{
    private int number;
    private CardColor color;

    public Card(int cardNumber, CardColor cardColor) {
        this.number = cardNumber;
        this.color = cardColor;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public CardColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        if(o == this){
            return true;
        }
        Card other = (Card) o;
        return new EqualsBuilder()
                .append(this.number, other.getNumber())
                .append(this.color, other.getColor())
                .isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder()
                .append(this.number)
                .append(this.color)
                .toHashCode();
    }


}
