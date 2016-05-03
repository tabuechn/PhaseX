package model.stack.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.card.ICard;
import model.stack.ICardStack;

import java.io.IOException;

/**
 * If everything works right this class was
 * created by konraifen88 on 03.05.2016..
 * If it doesn't work I don't know who the hell wrote it.
 */
public class JacksonStackSerializer extends JsonSerializer<ICardStack> {
    @Override
    public void serialize(ICardStack value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("type", value.getStackType().ordinal());
        gen.writeArrayFieldStart("cards");
        for (ICard card : value.getList()) {
            gen.writeObject(card);
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}
