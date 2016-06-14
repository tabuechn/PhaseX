package model.stack;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.stack.json.JacksonStackDeserializer;
import model.stack.json.JacksonStackSerializer;

import java.io.Serializable;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
@JsonSerialize(using = JacksonStackSerializer.class)
@JsonDeserialize(using = JacksonStackDeserializer.class)
public interface ICardStack extends Serializable {

    /* checks if the Card in the Parameter can be added to the Stack*/
    boolean checkCardMatching(ICard card);

    /* adds the Card in the Parameter to the Stack */
    void addCardToStack(ICard card);

    /* returns the List where the cards of the Stack are saved in*/
    IDeckOfCards getList();

    /**
     * Method to check which kind of stack is present
     *
     * @return the type of the stack
     */
    StackType getStackType();

    String getId();

    void setId(String id);
}
