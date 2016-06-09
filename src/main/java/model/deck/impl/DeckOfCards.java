package model.deck.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.card.ICard;
import model.card.impl.Card;
import model.deck.IDeckOfCards;

import java.io.IOException;
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
    public DeckOfCards(@JsonProperty("cards") List<ICard> cards) {
        super(cards);
    }

    @Override
    public String getJSon() {
        return new Gson().toJson(this);
    }

    @Override
    public List<ICard> getCards() {
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Deserializer extends JsonDeserializer<DeckOfCards> {
        @Override
        public DeckOfCards deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            List<ICard> cards = new LinkedList<>();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jp);
            for (JsonNode n : node) {
                cards.add(mapper.readValue(n.traverse(), Card.class));
            }

            return new DeckOfCards(cards);
        }
    }
}
