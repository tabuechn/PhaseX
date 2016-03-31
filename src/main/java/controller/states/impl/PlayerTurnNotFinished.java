package controller.states.impl;

import controller.IController;
import controller.states.AbstractState;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.phase.DeckNotFitException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Tarek on 25.09.2015. Be grateful for this superior Code!
 */
public class PlayerTurnNotFinished extends AbstractState {

    private static final Logger LOGGER = LogManager.getLogger(PlayerTurnNotFinished.class);

    @Override
    public void discard(IController controller, ICard card) {
        controller.discardCard(card);
        controller.nextPlayer();
        controller.setRoundState(new DrawPhase());
    }


    @Override
    @SuppressWarnings("squid:S1166")
    public void playPhase(final IController controller, IDeckOfCards phase) {
        try {
            controller.addPhase(phase);
            controller.setRoundState(new PlayerTurnFinished());
        } catch (DeckNotFitException e) {
            LOGGER.debug("Deck Not Fit Exception was thrown");
        }
    }

    @Override
    public String toString() {
        return "PlayerTurnNotFinished";
    }
}
