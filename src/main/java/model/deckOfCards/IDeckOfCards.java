package model.deckOfCards;

import model.card.ICard;

import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 24.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface IDeckOfCards extends List<ICard> {
    ICard removeLast();
}
