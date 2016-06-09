package actors.message;

import model.deck.IDeckOfCards;
import model.player.IPlayer;
import model.state.IRoundState;
import model.stack.ICardStack;

import java.util.List;

/**
 * Created by tabuechn on 25.05.2016.
 */
public class PlayPhaseMessage extends MasterMessage {

    private IPlayer currentPlayer;

    private IDeckOfCards phase;

    private List<ICardStack> allStacks;

    public PlayPhaseMessage(IRoundState roundState, IDeckOfCards phase, IPlayer currentPlayer, List<ICardStack> allStacks) {
        super(roundState);
        this.phase = phase;
        this.currentPlayer = currentPlayer;
        this.allStacks = allStacks;
    }

    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public IDeckOfCards getPhase() {
        return phase;
    }

    public List<ICardStack> getAllStacks() {
        return allStacks;
    }
}
