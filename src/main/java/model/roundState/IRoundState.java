package model.roundstate;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface IRoundState {

    StateEnum getState();

    void setState(StateEnum state);
}
