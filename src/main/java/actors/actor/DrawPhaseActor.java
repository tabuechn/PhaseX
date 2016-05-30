package actors.actor;

import actors.message.DrawHiddenMessage;
import actors.message.DrawOpenMessage;
import akka.actor.UntypedActor;
import model.deck.IDeckOfCards;
import model.player.IPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tabuechn on 12.05.2016.
 */
public class DrawPhaseActor extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(ActorMaster.class);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DrawOpenMessage) {
            drawOpenCard((DrawOpenMessage) message);
            getSender().tell(true, getSelf());
        } else if (message instanceof DrawHiddenMessage) {
            drawHiddenCard((DrawHiddenMessage) message);
            getSender().tell(true, getSelf());
        } else {
            LOG.error("unhandled message received");
            getSender().tell(false, getSelf());
        }
    }

    private void drawOpenCard(DrawOpenMessage dom) {
        IDeckOfCards discardPile = dom.getPile();
        IPlayer currentPlayer = dom.getCurrentPlayer();
        if (!discardPile.isEmpty()) {
            IDeckOfCards currentDeck = dom.getHand();
            currentDeck.add(discardPile.removeLast());
            currentPlayer.setDeckOfCards(currentDeck);
        }
    }

    private void drawHiddenCard(DrawHiddenMessage dhm) {
        IPlayer currentPlayer = dhm.getCurrentPlayer();
        IDeckOfCards drawPile = dhm.getPile();
        IDeckOfCards currentDeck = currentPlayer.getDeckOfCards();
        currentDeck.add(drawPile.removeLast());
        currentPlayer.setDeckOfCards(currentDeck);
    }
}
