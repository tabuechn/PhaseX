package model.card.impl;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;

import java.lang.reflect.Type;

/**
 * Created by tabuechn on 13.04.2016.
 */
public class CardDeserializer implements JsonDeserializer<ICard> {
    @Override
    public ICard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {

        ICard card = new Card();

        final JsonObject jsonObject = json.getAsJsonObject();

        final JsonElement jsonNumber = jsonObject.get("number");
        final String number = jsonNumber.getAsString();

        final JsonElement jsonColor = jsonObject.get("color");
        final String color = jsonColor.getAsString();

        card.setColor(CardColor.getColorByString(color));
        card.setNumber(CardValue.getValueByString(number));

        return card;
    }


}
