package model.deck;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import model.card.ICard;
import model.deck.impl.DeckOfCards;

import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 24.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
@JsonDeserialize(using = DeckOfCards.Deserializer.class)
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

    /**
     * Method to return a the Deck as a List of ICards
     * @return list of ICards
     */
    List<ICard> getCards();

}
