package controller.states.impl;

import controller.IController;
import controller.states.AbstractState;

/**
 * Created by Tarek on 25.09.2015. Be grateful for this superior Code!
 */
public class DrawPhase extends AbstractState {

    @Override
    public void drawOpen(final IController controller) {
        if (controller.getDiscardPile().size() > 0) {
            controller.drawOpenCard();
        }
    }

    @Override
    public void drawHidden(final IController controller) {
        controller.drawHiddenCard();
    }

    @Override
    public String toString() {
        return "DrawPhase";
    }

}
