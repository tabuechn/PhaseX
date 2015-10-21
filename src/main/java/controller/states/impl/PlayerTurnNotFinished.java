package controller.states.impl;

import controller.IController;
import controller.states.AbstractState;
import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import model.phase.DeckNotFitException;

/**
 * Created by Tarek on 25.09.2015. Be grateful for this superior Code!
 */
public class PlayerTurnNotFinished extends AbstractState {

    @Override
    public void discard(IController controller, ICard card) {
        controller.discardCard(card);
        controller.nextPlayer();
        controller.setRoundState(new DrawPhase());
    }


    @Override
    public void playPhase(final IController controller, IDeckOfCards phase) {
        try {
            controller.addPhase(phase);
            controller.setRoundState(new PlayerTurnFinished());
        } catch (DeckNotFitException e) {
            //Do not change roundState on exception and continue application normally.
        }
    }

    @Override
    public String toString() {
        return "PlayerTurnNotFinished";
    }
}
