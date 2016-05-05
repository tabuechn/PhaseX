package controller.states;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controller.IController;
import controller.states.impl.*;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.stack.ICardStack;

import java.io.IOException;

/**
 * Created by Tarek on 25.09.2015. Be grateful for this superior Code!
 */
@JsonSerialize(using = AbstractState.Serializer.class)
@JsonDeserialize(using = AbstractState.Deserializer.class)
public abstract class AbstractState {
    protected static final String DRAW_PHASE = "DrawPhase";
    protected static final String END_PHASE = "EndPhase";
    protected static final String PLAYER_TURN_NOT_FINISHED = "PlayerTurnNotFinished";
    protected static final String PLAYER_TURN_FINISED = "PlayerTurnFinished";
    protected static final String START_PHASE = "StartPhase";

    public static AbstractState getStateFromString(String state) throws IllegalArgumentException {
        switch (state) {
            case DRAW_PHASE:
                return new DrawPhase();
            case END_PHASE:
                return new EndPhase();
            case PLAYER_TURN_FINISED:
                return new PlayerTurnFinished();
            case PLAYER_TURN_NOT_FINISHED:
                return new PlayerTurnNotFinished();
            case START_PHASE:
                return new StartPhase();
            default:
                throw new IllegalArgumentException();
        }
    }

    public void drawOpen(final IController controller) {
    }

    public void drawHidden(final IController controller) {
    }

    public void addToFinishedPhase(final IController controller, ICard card, ICardStack stack) {
    }

    public void discard(final IController controller, ICard card) {
    }

    public void playPhase(final IController controller, IDeckOfCards phase) {
    }

    public void start(final IController controller) {
    }

    @Override
    public abstract String toString();

    public static class Serializer extends JsonSerializer<AbstractState> {

        @Override
        public void serialize(AbstractState value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStartObject();
            jgen.writeStringField("state", value.toString());
            jgen.writeEndObject();
        }
    }

    public static class Deserializer extends JsonDeserializer<AbstractState> {
        @Override
        public AbstractState deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jp);
            return AbstractState.getStateFromString(node.get("state").asText());
        }
    }
}
