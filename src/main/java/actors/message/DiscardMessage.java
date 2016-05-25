package actors.message;

import model.card.ICard;
import model.deck.IDeckOfCards;
import model.player.IPlayer;
import model.roundState.IRoundState;

/**
 * Created by tabuechn on 12.05.2016.
 */
public class DiscardMessage extends MasterMessage {

    private IDeckOfCards discardPile;

    private ICard card;

    private IPlayer currentPlayer;

    public DiscardMessage(IRoundState roundState, IDeckOfCards discardPile, ICard card, IPlayer currentPlayer) {
        super(roundState);
        this.discardPile = discardPile;
        this.card = card;
        this.currentPlayer = currentPlayer;
    }

    public IDeckOfCards getDiscardPile() {
        return discardPile;
    }

    public ICard getCard() {
        return card;
    }

    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }
}
