package model.phase;

import model.deckOfCards.IDeckOfCards;

import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 12.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface IPhaseSplitter {

    /**
     * Method to extract a color or value based pair, triple,... from a given deck.
     * If the given deck held more than one fitting stacks, this method will only extract the first found.
     *
     * @param cards the cards which should be splitted.
     * @return A list of CardDecks, where the first deck (index = 0) is the extracted deck and the last deck is the
     * last index.
     */
    List<IDeckOfCards> split(IDeckOfCards cards);
}
