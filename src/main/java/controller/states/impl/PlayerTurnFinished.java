package controller.states.impl;

import controller.IController;
import controller.states.AbstractState;
import model.card.ICard;
import model.stack.ICardStack;

/**
 * Created by Tarek on 26.09.2015. Be grateful for this superior Code!
 */
public class PlayerTurnFinished extends AbstractState {

    private void checkFinishedDiscard(IController controller) {
        if (controller.currentPlayerHasNoCards()) {
            endOfTurn(controller);
        } else {
            controller.nextPlayer();
            controller.setRoundState(new DrawPhase());
        }
    }

    private void endOfTurn(IController controller) {
        if (controller.isGameFinished()) {
            controller.setRoundState(new EndPhase());
            controller.setStatusMessage("Player " + controller.getCurrentPlayer().getPlayerName() + " has won");
        } else {
            controller.endRound();
            controller.newRound();
            controller.setRoundState(new DrawPhase());
        }
        controller.notifyObservers();
    }

    @Override
    public void addToFinishedPhase(final IController controller, ICard card, ICardStack stack) {
        if (stack.checkCardMatching(card)) {
            stack.addCardToStack(card);
            controller.getCurrentPlayersHand().remove(card);
            checkFinishedPlayPhase(controller);
        }
    }

    @Override
    public void discard(final IController controller, ICard card) {
        controller.discardCard(card);
        checkFinishedDiscard(controller);
    }

    @Override
    public String toString() {
        return "PlayerTurnFinished";
    }

    private void checkFinishedPlayPhase(IController controller) {
        if (controller.currentPlayerHasNoCards()) {
            endOfTurn(controller);
        }
    }
}
