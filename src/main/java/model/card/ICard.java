package model.card;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import model.card.impl.Card;

import javax.annotation.Nonnull;

/**
 * If everything works right this class was
 * created by Konraifen88 on 22.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
@JsonSerialize(using = Card.Serializer.class)
@JsonDeserialize(using = Card.Deserializer.class)
public interface ICard extends Comparable<ICard> {
    CardValue getNumber();

    void setNumber(CardValue value);

    CardColor getColor();

    void setColor(CardColor color);

    @Override
    int compareTo(@Nonnull ICard other);
}
