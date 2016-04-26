package model.card.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;

import java.io.IOException;

/**
 * If everything works right this class was
 * created by Konraifen88 on 25.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CardDeserializer extends JsonDeserializer<Card> {
    @Override
    public Card deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        CardColor color = CardColor.byOrdinal((Integer) node.get("Color").numberValue());
        CardValue value = CardValue.byOrdinal((Integer) node.get("Value").numberValue());

        return new Card(value, color);
    }
}
