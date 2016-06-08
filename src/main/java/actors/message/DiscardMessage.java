package actors.message;

import model.card.ICard;
import model.deck.IDeckOfCards;
import model.player.IPlayer;
import model.roundState.IRoundState;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

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

    public static DiscardMessage getDiscardMessage(Future<Object> fut, FiniteDuration dur) {
        try {
            Object o = Await.result(fut, dur);
            if (o instanceof DiscardMessage) {
                return (DiscardMessage) o;
            }
        } catch (Exception e) {
            //got invalid message
        }
        return null;
    }
}
