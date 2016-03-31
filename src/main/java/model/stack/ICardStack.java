package model.stack;

import model.card.ICard;
import model.deck.IDeckOfCards;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
public interface ICardStack {

    /* checks if the Card in the Parameter can be added to the Stack*/
    boolean checkCardMatching(ICard card);

    /* adds the Card in the Parameter to the Stack */
    void addCardToStack(ICard card);

    /* returns the List where the cards of the Stack are saved in*/
    IDeckOfCards getList();
}
