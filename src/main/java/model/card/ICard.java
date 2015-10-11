package model.card;

import javax.annotation.Nonnull;

/**
 * If everything works right this class was
 * created by Konraifen88 on 22.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface ICard extends Comparable<ICard> {
    CardValue getNumber();

    CardColor getColor();

    @Override
    int compareTo(@Nonnull ICard other);
}
