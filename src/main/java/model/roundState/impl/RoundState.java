package model.roundState.impl;

import model.roundState.IRoundState;
import model.roundState.StateEnum;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class RoundState implements IRoundState {

    private StateEnum state;

    public RoundState(){
        state = StateEnum.START_PHASE;
    }

    @Override
    public StateEnum getState() {
        return state;
    }

    @Override
    public void setState(StateEnum state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state.toString();
    }
}
