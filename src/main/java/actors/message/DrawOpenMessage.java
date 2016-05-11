package actors.message;

import model.deck.IDeckOfCards;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class DrawOpenMessage {

    private IDeckOfCards pile;
    private IDeckOfCards hand;

    /**
     * sdfghj
     * @param pile the discard pile of the controller
     * @param hand hand of the current player
     */
    public DrawOpenMessage(IDeckOfCards pile, IDeckOfCards hand){
        this.pile = pile;
        this.hand = hand;
    }

    public IDeckOfCards getPile() {
        return pile;
    }

    public IDeckOfCards getHand() {
        return hand;
    }
}
