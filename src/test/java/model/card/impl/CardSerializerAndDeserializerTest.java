package model.card.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * If everything works right this class was
 * created by konraifen88 on 07.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CardSerializerAndDeserializerTest {

    private ICard testCard;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        testCard = new Card(CardValue.EIGHT, CardColor.BLUE);
        mapper = new ObjectMapper();
    }

    @Test
    public void serialize() throws Exception {
        String json = mapper.writeValueAsString(testCard);
        assertTrue(json.contains("\"color\""));
        assertTrue(json.contains("\"value\""));
    }

    @Test
    public void deserialize() throws Exception {
        ICard startPhase = mapper.readValue("{\"color\":2,\"value\":8}", ICard.class);
        assertTrue(startPhase instanceof Card);
    }
}