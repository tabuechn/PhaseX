package model.stack.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.stack.AbstractStack;
import model.stack.ICardStack;

import java.io.Serializable;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
/**
 * Two different stacks can not be equals, because the abstract implementation uses the StackType Enum
 */
@SuppressWarnings("squid:S2160")
public class StreetStack extends AbstractStack implements ICardStack, Serializable {

    @JsonProperty("_id")
    private String id;
    private ICard lowestCard;
    private ICard highestCard;

    public StreetStack(IDeckOfCards cards) {
        super(StackType.STREET,cards);
        list.sort(new CardValueComparator());
        this.lowestCard = list.get(0);
        this.highestCard = list.get(list.size() - 1);
    }

    CardValue getHighestCardNumber() {
        return highestCard.getNumber();
    }

    CardValue getLowestCardNumber() {
        return lowestCard.getNumber();
    }

    @Override
    public boolean checkCardMatching(ICard card) {
        return card.getNumber().equals(CardValue.byOrdinal(lowestCard.getNumber().ordinal() - 1))
                || card.getNumber().equals(CardValue.byOrdinal(highestCard.getNumber().ordinal() + 1));
    }

    @Override
    public void addCardToStack(ICard card) {
        list.add(card);
        list.sort(new CardValueComparator());
        if (card.compareTo(this.highestCard) > 0) {
            this.highestCard = card;
        } else {
            this.lowestCard = card;
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

}
