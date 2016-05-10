package controller.states.impl;

import controller.IController;
import controller.states.AbstractState;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;
import model.player.impl.Player;
import model.stack.ICardStack;
import util.CardCreator;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Tarek on 25.09.2015. Be grateful for this superior Code!
 */
public class StartPhase extends AbstractState {

    public void start(final IController controller) {
        controller.initGame();
        controller.newRound();
    }

    @Override
    public void start(IPlayer[] players, int playerCount, IDeckOfCards drawPile, IDeckOfCards discardPile, LinkedList<ICardStack> allPhases) {
        players = new IPlayer[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player(i);
        }
        drawPile = new DeckOfCards();
        discardPile = new DeckOfCards();
        allPhases = new LinkedList<>();
        drawPile = CardCreator.giveDeckOfCards();
        Collections.shuffle(drawPile);
        discardPile.add(drawPile.removeLast());
        for (IPlayer player : players) {
            player.setDeckOfCards(draw10Cards(drawPile));
        }
        /*
        currentPlayerIndex = 0;
        currentPlayer = players[currentPlayerIndex];
        roundState = new DrawPhase();*/
    }

    private IDeckOfCards draw10Cards(IDeckOfCards drawPile) {
        DeckOfCards deck = new DeckOfCards();
        for (int i = 0; i < 10; i++) {
            deck.add(drawPile.removeLast());
        }
        return deck;

    }

    @Override
    public String toString() {
        return "StartPhase";
    }
}
