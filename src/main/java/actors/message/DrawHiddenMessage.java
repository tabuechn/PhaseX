package actors.message;

import model.deck.IDeckOfCards;
import model.player.IPlayer;
import model.roundstate.IRoundState;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class DrawHiddenMessage extends MasterMessage {

    private IDeckOfCards drawPile;
    private IDeckOfCards hand;
    private IPlayer currentPlayer;

    /**
     * @param pile the hidden pile of the controller
     * @param hand hand of the current player
     */
    public DrawHiddenMessage(IDeckOfCards pile, IDeckOfCards hand, IPlayer currentPlayer, IRoundState roundState) {
        super(roundState);
        this.drawPile = pile;
        this.hand = hand;
        this.currentPlayer = currentPlayer;
    }

    public IDeckOfCards getPile() {
        return drawPile;
    }

    public IDeckOfCards getHand() {
        return hand;
    }

    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }
}
