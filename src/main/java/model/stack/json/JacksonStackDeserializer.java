package model.stack.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.stack.ICardStack;
import model.stack.StackType;
import model.stack.impl.ColorStack;
import model.stack.impl.PairStack;
import model.stack.impl.StreetStack;

import java.io.IOException;

/**
 * If everything works right this class was
 * created by konraifen88 on 03.05.2016..
 * If it doesn't work I don't know who the hell wrote it.
 */
public class JacksonStackDeserializer extends JsonDeserializer<ICardStack> {
    @Override
    public ICardStack deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(p);

        JsonNode cards = node.path("cards");
        IDeckOfCards deck = mapper.readValue(cards.traverse(), DeckOfCards.class);


        return getCardStack(node.get("type").asInt(), deck);
    }

    private ICardStack getCardStack(int type, IDeckOfCards cards) {
        StackType t = StackType.byOrdinal(type);
        assert t != null;
        switch (t) {
            case COLOR:
                return new ColorStack(cards);
            case PAIR:
                return new PairStack(cards);
            default:
                return new StreetStack(cards);
        }
    }
}
