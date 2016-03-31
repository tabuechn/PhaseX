package model.deck;

import model.card.ICard;

import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 24.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface IDeckOfCards extends List<ICard> {
    /**
     * Removes the last card from the current deck.
     *
     * @return the card which is removed from the deck.
     */
    ICard removeLast();

    /**
     * Removes the first card from the current deck.
     *
     * @return the card which is removed from the deck.
     */
    ICard removeFirst();


    /**
     * Method for getting a JSon Object of the current deck
     * @return a json formatted string
     */
    String getJSon();

}
