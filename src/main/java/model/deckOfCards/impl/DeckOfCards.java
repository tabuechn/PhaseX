package model.deckOfCards.impl;

import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 24.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
@Component
public class DeckOfCards extends LinkedList<ICard> implements IDeckOfCards {

    public DeckOfCards(){
        super();
    }

    public DeckOfCards(List<ICard> cards){
        super(cards);
    }

}
