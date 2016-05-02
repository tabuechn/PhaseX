package model.deck.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.gson.Gson;
import model.card.ICard;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 24.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class DeckOfCards extends LinkedList<ICard> implements IDeckOfCards, Serializable {

    @JsonProperty("_id")
    private String id;

    public DeckOfCards() {
        super();
    }

    @JsonCreator
    public DeckOfCards(@JsonDeserialize(contentAs = Card.class) List<ICard> cards) {
        super(cards);
    }

    @Override
    public String getJSon() {
        return new Gson().toJson(this);
    }

    public List<ICard> getCards() {
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
