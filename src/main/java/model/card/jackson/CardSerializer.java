package model.card.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.card.impl.Card;

import java.io.IOException;

/**
 * If everything works right this class was
 * created by Konraifen88 on 22.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CardSerializer extends JsonSerializer<Card> {

    @Override
    public void serialize(Card value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumberField("Color", value.getColor().ordinal());
        gen.writeNumberField("Value", value.getNumber().ordinal());
        gen.writeEndObject();
    }
}
