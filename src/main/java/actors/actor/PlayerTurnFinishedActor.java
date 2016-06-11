package actors.actor;

import actors.message.AddToPhaseMessage;
import actors.message.DiscardMessage;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import model.card.ICard;
import model.card.impl.CardColorComparator;
import model.card.impl.CardValueComparator;
import model.player.IPlayer;
import model.stack.ICardStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by tabuechn on 24.05.2016.
 */
class PlayerTurnFinishedActor extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(PlayerTurnFinishedActor.class);
    private final ActorRef discardActor = getContext().actorOf(Props.create(DiscardActor.class), "discardActor");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DiscardMessage) {
            discardActor.forward(message, getContext());
        } else if (message instanceof AddToPhaseMessage) {
            addToFinishedPhase((AddToPhaseMessage) message);
            getSender().tell(message, getSelf());
        } else {
            LOG.error("unhandled message received");
            getSender().tell(false, getSelf());
        }
    }

    private void addToFinishedPhase(AddToPhaseMessage message) {
        ICardStack stack = message.getStack();
        List<ICard> cards = message.getCard();
        boolean canaries = true;
        sortCards(cards);
        IPlayer currentPlayer = message.getCurrentPlayer();
        for (ICard c : cards) {
            if (stack.checkCardMatching(c)) {
                stack.addCardToStack(c);
                currentPlayer.getDeckOfCards().remove(c);
            } else {
                canaries = false;
                getSender().tell(false, getSelf());
                break;
            }
        }
        if (canaries) {
            getSender().tell(message, getSelf());
        }
    }

    private void sortCards(List<ICard> cards) {
        cards.sort(new CardColorComparator());
        cards.sort(new CardValueComparator());
    }


}
