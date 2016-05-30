package actors.actor;

import actors.message.DiscardMessage;
import actors.message.zAddToPhaseMessage;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import model.card.ICard;
import model.player.IPlayer;
import model.stack.ICardStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tabuechn on 24.05.2016.
 */
public class PlayerTurnFinishedActor extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(PlayerTurnFinishedActor.class);
    private final ActorRef discardActor = getContext().actorOf(Props.create(DiscardActor.class), "discardActor");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DiscardMessage) {
            discardActor.forward(message, getContext());
        } else if (message instanceof zAddToPhaseMessage) {
            addToFinishedPhase((zAddToPhaseMessage) message);
            getSender().tell(true, getSelf());
        } else {
            LOG.error("unhandled message received");
            unhandled(message);
        }
    }

    private void addToFinishedPhase(zAddToPhaseMessage message) {
        ICardStack stack = message.getStack();
        ICard card = message.getCard();
        IPlayer currentPlayer = message.getCurrentPlayer();
        if (stack.checkCardMatching(card)) {
            stack.addCardToStack(card);
            currentPlayer.getDeckOfCards().remove(card);
        }
    }


}
