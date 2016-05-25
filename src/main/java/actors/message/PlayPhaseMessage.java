package actors.message;

import model.roundState.IRoundState;

/**
 * Created by tabuechn on 25.05.2016.
 */
public class PlayPhaseMessage extends MasterMessage {

    public PlayPhaseMessage(IRoundState roundState) {
        super(roundState);
    }
}
