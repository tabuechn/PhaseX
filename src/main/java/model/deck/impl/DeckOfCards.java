package model.deck.impl;

import com.google.gson.Gson;
import com.sun.javafx.beans.IDProperty;
import model.card.ICard;
import model.deck.IDeckOfCards;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 24.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */

public class DeckOfCards extends LinkedList<ICard> implements IDeckOfCards, Serializable {

    public DeckOfCards() {
        super();
    }

    public DeckOfCards(List<ICard> cards) {
        super(cards);
    }

    @Override
    public String getJSon() {
        return new Gson().toJson(this);
    }

}
