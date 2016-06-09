package model.card.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by tabuechn on 09.06.2016.
 */
public class CardSerializerTest {

    @Test
    public void cardSerializerTest() {
        ICard card = new Card(CardValue.FOUR, CardColor.BLUE);
        Gson gsonNormal = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ICard.class,new CardDeserializer());
        Gson gsonDeserializer = gsonBuilder.create();
        String cardString = gsonNormal.toJson(card);
        ICard result = gsonDeserializer.fromJson(cardString,ICard.class);
        assertEquals(result,card);
    }
}
