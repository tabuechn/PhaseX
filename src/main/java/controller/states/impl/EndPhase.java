package controller.states.impl;

import controller.IController;
import controller.states.AbstractState;

/**
 * Created by Tarek on 25.09.2015. Be grateful for this superior Code!
 */
public class EndPhase extends AbstractState {

    @Override
    public void start(final IController controller) {
        controller.initGame();
        controller.newRound();
    }

    @Override
    public String toString() {
        return "EndPhase";
    }
}
