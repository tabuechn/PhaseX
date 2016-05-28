package actors.message;

import model.card.ICard;
import model.player.IPlayer;
import model.roundState.IRoundState;
import model.stack.ICardStack;

/**
 * Created by tabuechn on 28.05.2016.
 */
public class AddToPhaseMessage extends MasterMessage {

    private ICard card;

    private ICardStack stack;

    private IPlayer currentPlayer;

    public AddToPhaseMessage(IRoundState roundState, ICard card, ICardStack stack, IPlayer currentPlayer) {
        super(roundState);
        this.card = card;
        this.stack = stack;
        this.currentPlayer = currentPlayer;
    }

    public ICard getCard() {
        return card;
    }

    public ICardStack getStack() {
        return stack;
    }

    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }
}
