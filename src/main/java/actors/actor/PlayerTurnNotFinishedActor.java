package actors.actor;

import actors.message.DiscardMessage;
import actors.message.PlayPhaseMessage;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import model.deck.IDeckOfCards;
import model.phase.DeckNotFitException;
import model.player.IPlayer;
import model.roundState.IRoundState;
import model.roundState.StateEnum;
import model.stack.ICardStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tabuechn on 25.05.2016.
 */
public class PlayerTurnNotFinishedActor extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(ActorMaster.class);
    private final ActorRef discardActor = getContext().actorOf(Props.create(DiscardActor.class), "discardActor");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DiscardMessage) {
            discardActor.forward(message, getContext());
        } else if (message instanceof PlayPhaseMessage) {
            playPhase((PlayPhaseMessage) message);
            getSender().tell(true, getSelf());
        } else {
            LOG.error("unhandled message received");
            unhandled(message);
        }
    }

    private void playPhase(PlayPhaseMessage message) {
        IRoundState state = message.getRoundState();
        IPlayer currentPlayer = message.getCurrentPlayer();
        IDeckOfCards phase = message.getPhase();
        List<ICardStack> allStacks = message.getAllStacks();
        try {
            List<ICardStack> phases = currentPlayer.getPhase().splitAndCheckPhase(phase);
            currentPlayer.setPhaseDone(true);
            removePhasefromCurrentPlayer(phase, currentPlayer);
            putDownStacks(phases, allStacks);
            state.setState(StateEnum.PLAYER_TURN_FINISHED);
        } catch (DeckNotFitException dnfe) {
            LOG.debug("Deck Not Fit Exception was thrown");
        }
    }

    private void putDownStacks(List<ICardStack> phases, List<ICardStack> allStacks) {
        allStacks.addAll(phases.stream().collect(Collectors.toList()));
    }

    private void removePhasefromCurrentPlayer(IDeckOfCards phase, IPlayer currentPlayer) {
        IDeckOfCards playerDeck = currentPlayer.getDeckOfCards();
        phase.forEach(playerDeck::remove);
    }


}
