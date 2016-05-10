package util;

import controller.states.AbstractState;
import model.deck.IDeckOfCards;
import model.stack.ICardStack;

import java.util.List;

/**
 * Created by Tarek on 10.05.2016. Be grateful for this superior Code!
 */
public class ControllerDataContainer {
    private AbstractState roundState;

    private int currentPlayerIndex;

    private IDeckOfCards drawPile;

    private IDeckOfCards discardPile;

    private List<ICardStack> allPhases;

    public AbstractState getRoundState() {
        return roundState;
    }

    public void setRoundState(AbstractState roundState) {
        this.roundState = roundState;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public IDeckOfCards getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(IDeckOfCards drawPile) {
        this.drawPile = drawPile;
    }

    public IDeckOfCards getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(IDeckOfCards discardPile) {
        this.discardPile = discardPile;
    }
}
