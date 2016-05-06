package model.stack.json;

import com.google.gson.*;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import model.stack.impl.PairStack;
import model.stack.impl.StreetStack;

import java.lang.reflect.Type;

/**
 * Created by tabuechn on 16.04.2016.
 */
public class GsonStackDeserializer implements JsonDeserializer<ICardStack> {

    @Override
    public ICardStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonElement jsonStackNumber = jsonObject.get("stackNumber");
        if(jsonStackNumber != null) {
            return createPairStack(jsonObject);
        }
        final  JsonElement jsonColor = jsonObject.get("color");
        if(jsonColor != null) {
            return createColorStack(jsonObject);
        }
        return createStreetStack(jsonObject);
    }

    private ICardStack createStreetStack(JsonObject jsonObject) {
        return new StreetStack(createDeck(jsonObject));
    }

    private ICardStack createColorStack(JsonObject jsonObject) {
        return new ColorStack(createDeck(jsonObject));
    }

    private ICardStack createPairStack(JsonObject jsonObject) {

        return new PairStack(createDeck(jsonObject));
    }

    private IDeckOfCards createDeck(JsonObject jsonObject) {
        IDeckOfCards deck = new DeckOfCards();
        JsonArray list =jsonObject.getAsJsonArray("list");
        for(JsonElement element :list) {
            JsonObject cardObject =element.getAsJsonObject();
            CardValue value = CardValue.getValueByString(cardObject.get("number").getAsString());
            CardColor color = CardColor.getColorByString(cardObject.get("color").getAsString());
            ICard card = new Card(value,color);
            deck.add(card);
        }
        return deck;
    }
}
