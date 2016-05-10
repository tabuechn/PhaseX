package controller.states.impl;

import controller.states.AbstractState;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;

import java.util.Collections;

/**
 * Created by Tarek on 25.09.2015. Be grateful for this superior Code!
 */
public class DrawPhase extends AbstractState {

    @Override
    public AbstractState drawOpen(IDeckOfCards discardPile, IDeckOfCards drawPile, IPlayer currentPlayer) {
        if(discardPile.isEmpty()) {
            IDeckOfCards currentDeck = currentPlayer.getDeckOfCards();
            currentDeck.add(discardPile.removeLast());
            currentPlayer.setDeckOfCards(currentDeck);
             return afterDraw(currentPlayer,discardPile,drawPile);
        }
        return this;
    }

    private AbstractState afterDraw(IPlayer currentPlayer, IDeckOfCards discardPile, IDeckOfCards drawPile) {
        AbstractState newState;
        if (currentPlayer.isPhaseDone()) {
            newState = new PlayerTurnFinished();
        } else {
            newState = new PlayerTurnNotFinished();
        }
        checkIfDrawPileEmpty(discardPile,drawPile);
        return newState;
    }

    private void checkIfDrawPileEmpty(IDeckOfCards discardPile, IDeckOfCards drawPile) {
        if(drawPile.isEmpty()) {
            DeckOfCards newDiscardPile = new DeckOfCards();
            newDiscardPile.add(discardPile.removeLast());
            DeckOfCards newDrawPile = new DeckOfCards(discardPile);
            Collections.shuffle(newDrawPile);
            Collections.shuffle(newDrawPile);
            drawPile = newDrawPile;
            discardPile = newDiscardPile;
        }
    }

    @Override
    public AbstractState drawHidden(IDeckOfCards discardPile, IDeckOfCards drawPile, IPlayer currentPlayer) {
        IDeckOfCards currentDeck = currentPlayer.getDeckOfCards();
        currentDeck.add(drawPile.removeLast());
        currentPlayer.setDeckOfCards(currentDeck);
        return afterDraw(currentPlayer, discardPile, drawPile);
    }

    @Override
    public String toString() {
        return "DrawPhase";
    }

}
