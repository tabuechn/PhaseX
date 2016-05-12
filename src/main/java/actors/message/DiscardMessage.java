package actors.message;

import model.deck.IDeckOfCards;
import model.roundState.IRoundState;

/**
 * Created by tabuechn on 12.05.2016.
 */
public class DiscardMessage extends MasterMessage {

    private IDeckOfCards discardPile;

    public DiscardMessage(IRoundState roundState) {
        super(roundState);
    }

    public IDeckOfCards getDiscardPile() {
        return discardPile;
    }
}
