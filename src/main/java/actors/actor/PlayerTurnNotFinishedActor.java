package actors.actor;

import actors.message.DiscardMessage;
import actors.message.PlayPhaseMessage;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import model.deck.IDeckOfCards;
import model.phase.DeckNotFitException;
import model.player.IPlayer;
import model.stack.ICardStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by tabuechn on 25.05.2016.
 */
class PlayerTurnNotFinishedActor extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(ActorMaster.class);
    private final ActorRef discardActor = getContext().actorOf(Props.create(DiscardActor.class), "discardActor");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DiscardMessage) {
            discardActor.forward(message, getContext());
        } else if (message instanceof PlayPhaseMessage) {
            playPhase((PlayPhaseMessage) message);
        } else {
            LOG.error("unhandled message received");
            getSender().tell(false, getSelf());
        }
    }

    private void playPhase(PlayPhaseMessage message) {
        IPlayer currentPlayer = message.getCurrentPlayer();
        IDeckOfCards possiblePhase = message.getPhase();
        List<ICardStack> allStacks = message.getAllStacks();
        try {
            List<ICardStack> phases = currentPlayer.getPhase().splitAndCheckPhase(message.getPhase());
            currentPlayer.setPhaseDone(true);
            currentPlayer = removePhaseFromCurrentPlayer(possiblePhase, currentPlayer);
            allStacks = putDownStacks(phases, allStacks);
        } catch (DeckNotFitException dnfe) {
            LOG.debug("Deck Not Fit Exception was thrown");
        }
        getSender().tell(new PlayPhaseMessage(message.getRoundState(), null, currentPlayer, allStacks), getSelf());
    }

    private List<ICardStack> putDownStacks(List<ICardStack> phases, List<ICardStack> allStacks) {
        //return phases.stream().collect(Collectors.toList());
        allStacks.addAll(phases);
        return allStacks;
    }

    private IPlayer removePhaseFromCurrentPlayer(IDeckOfCards phase, IPlayer currentPlayer) {
        IDeckOfCards playerDeck = currentPlayer.getDeckOfCards();
        phase.forEach(playerDeck::remove);
        currentPlayer.setDeckOfCards(playerDeck);
        return currentPlayer;
    }


}
