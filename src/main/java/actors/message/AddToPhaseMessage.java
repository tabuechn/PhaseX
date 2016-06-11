package actors.message;

import model.card.ICard;
import model.player.IPlayer;
import model.stack.ICardStack;
import model.state.IRoundState;

import java.util.List;

/**
 * Created by tabuechn on 28.05.2016.
 */
public class AddToPhaseMessage extends MasterMessage {

    private List<ICard> card;

    private ICardStack stack;

    private IPlayer currentPlayer;

    public AddToPhaseMessage(IRoundState roundState, List<ICard> cards, ICardStack stack, IPlayer currentPlayer) {
        super(roundState);
        this.card = cards;
        this.stack = stack;
        this.currentPlayer = currentPlayer;
    }

    public List<ICard> getCard() {
        return card;
    }

    public ICardStack getStack() {
        return stack;
    }

    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }
}
