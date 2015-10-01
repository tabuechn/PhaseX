package controller.states.impl;

import controller.IController;
import controller.states.AbstractState;
import model.card.ICard;
import model.deckOfCards.IDeckOfCards;

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
        if (controller.deckMatchesCurrentPlayersPhase(phase)) {
            controller.addPhase(phase);
            controller.setRoundState(new PlayerTurnFinished());
        }
    }

    @Override
    public String toString() {
        return "PlayerTurnNotFinished";
    }
}
