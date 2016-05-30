package actors.actor;

import actors.message.DiscardMessage;
import akka.actor.UntypedActor;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.player.IPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tabuechn on 25.05.2016.
 */
public class DiscardActor extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(DiscardActor.class);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DiscardMessage) {
            discard((DiscardMessage) message);
            getSender().tell(true, getSelf());
        } else {
            LOG.error("unhandled message received");
            getSender().tell(false, getSelf());
        }
    }

    private void discard(DiscardMessage discardMessage) {
        IDeckOfCards discardPile = discardMessage.getDiscardPile();
        ICard card = discardMessage.getCard();
        IPlayer currentPlayer = discardMessage.getCurrentPlayer();
        currentPlayer.getDeckOfCards().remove(card);
        discardPile.add(card);
    }
}
