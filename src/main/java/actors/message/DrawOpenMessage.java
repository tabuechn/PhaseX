package actors.message;

import model.deck.IDeckOfCards;
import model.player.IPlayer;
import model.roundState.IRoundState;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class DrawOpenMessage extends MasterMessage {

    private IDeckOfCards pile;
    private IDeckOfCards hand;
    private IPlayer currentPlayer;

    /**
     * sdfghj
     * @param pile the discard pile of the controller
     * @param hand hand of the current player
     */
    public DrawOpenMessage(IDeckOfCards pile, IDeckOfCards hand, IPlayer currentPlayer, IRoundState roundState) {
        super(roundState);
        this.pile = pile;
        this.hand = hand;
        this.currentPlayer = currentPlayer;
    }

    public IDeckOfCards getPile() {
        return pile;
    }

    public IDeckOfCards getHand() {
        return hand;
    }

    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }
}
