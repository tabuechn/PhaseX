package actors.actor;

import actors.message.AddToPhaseMessage;
import actors.message.DiscardMessage;
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
        } else if (message instanceof AddToPhaseMessage) {
            addToFinishedPhase((AddToPhaseMessage) message);
            getSender().tell(true, getSelf());
        } else {
            LOG.error("unhandled message received");
            getSender().tell(false, getSelf());
        }
    }

    private void addToFinishedPhase(AddToPhaseMessage message) {
        ICardStack stack = message.getStack();
        ICard card = message.getCard();
        IPlayer currentPlayer = message.getCurrentPlayer();
        if (stack.checkCardMatching(card)) {
            stack.addCardToStack(card);
            currentPlayer.getDeckOfCards().remove(card);
        }
    }


}
