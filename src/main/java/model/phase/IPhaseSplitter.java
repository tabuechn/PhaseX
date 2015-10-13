package model.phase;

import model.deckOfCards.IDeckOfCards;

import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 12.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface IPhaseSplitter {
    List<IDeckOfCards> split(IDeckOfCards cards);
}
