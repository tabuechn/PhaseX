package controller.states;

import controller.IController;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.stack.ICardStack;

/**
 * Created by Tarek on 25.09.2015. Be grateful for this superior Code!
 */
public abstract class AbstractState {
    public void drawOpen(final IController controller) {
    }

    public void drawHidden(final IController controller) {
    }

    public void addToFinishedPhase(final IController controller, ICard card, ICardStack stack) {
    }

    public void discard(final IController controller, ICard card) {
    }

    public void playPhase(final IController controller, IDeckOfCards phase) {
    }

    public void start(final IController controller) {
    }

    @Override
    public abstract String toString();
}
