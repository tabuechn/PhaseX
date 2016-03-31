package util;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * If everything works right this class was
 * created by Konraifen88 on 25.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
@SuppressWarnings("squid:S1118")
public class CardCreator {

    private CardCreator() {}

    public static final int SIZE_OF_DECK_MULTIPLIER = 4;
    public static final int LOWEST_CARD = 1;
    public static final int HIGHEST_CARD = 12;

    private static IDeckOfCards giveDeckOfCardsByList(List<?> range) {
        IDeckOfCards deck = new DeckOfCards();
        range.stream().filter(entry -> entry instanceof Number)
                .forEach(card -> deck.addAll(createCardsOfAllColors((Number) card)));
        return deck;
    }

    private static List<ICard> createCardsOfAllColors(Number number) {
        List<ICard> tmp = new LinkedList<>();
        for (int i = 0; i < SIZE_OF_DECK_MULTIPLIER; i++) {
            tmp.add(new Card(CardValue.byOrdinal(number.intValue()), CardColor.GREEN));
            tmp.add(new Card(CardValue.byOrdinal(number.intValue()), CardColor.RED));
            tmp.add(new Card(CardValue.byOrdinal(number.intValue()), CardColor.BLUE));
            tmp.add(new Card(CardValue.byOrdinal(number.intValue()), CardColor.YELLOW));
        }
        return tmp;
    }

    public static IDeckOfCards giveDeckOfCards() {
        List<Integer> range = IntStream.rangeClosed(LOWEST_CARD, HIGHEST_CARD).boxed().collect(Collectors.toList());
        return giveDeckOfCardsByList(range);
    }
}
