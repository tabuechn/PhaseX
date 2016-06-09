package actors.message;

import model.state.IRoundState;

/**
 * Created by tabuechn on 12.05.2016.
 */
public abstract class MasterMessage {

    private IRoundState roundState;

    public MasterMessage(IRoundState roundState) {
        this.roundState = roundState;
    }

    public IRoundState getRoundState() {
        return roundState;
    }
}
