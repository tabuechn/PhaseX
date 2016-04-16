package persistence.hibernate;

import model.card.ICard;
import model.card.impl.Card;
import model.phase.IPhase;
import model.player.IPlayer;
import model.player.impl.Player;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by tabuechn on 05.04.2016.
 */
@Entity
@Table(name = "PhaseX_Controller13")
public class ControllerData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="PhaseX_Player1")
    private Player player1;
    @Column(name="PhaseX_Player1Cards", length = Integer.MAX_VALUE)
    private String player1Pile;
    @Column(name = "PhaseX_Player1Phase")
    private String player1PhaseString;

    @Column(name="PhaseX_Player2")
    private Player player2;
    @Column(name="PhaseX_Player2Cards", length = Integer.MAX_VALUE)
    private String player2Pile;
    @Column(name = "PhaseX_Player2Phase")
    private String player2PhaseString;

    @Column(name = "PhaseX_DiscardPile", length = Integer.MAX_VALUE)
    private String discardPile;

    @Column(name = "PhaseX_DrawPile", length = Integer.MAX_VALUE)
    private String drawPile;

    @Column(name = "PhaseX_RoundState")
    private String roundState;

    @Column(name = "PhaseX_StatusMessage")
    private String statusMessage;

    @Column(name = "PhaseX_Stack1", length = Integer.MAX_VALUE)
    private String stack1;

    @Column(name = "PhaseX_Stack2", length = Integer.MAX_VALUE)
    private String stack2;

    @Column(name = "PhaseX_Stack3", length = Integer.MAX_VALUE)
    private String stack3;

    @Column(name = "PhaseX_Stack4", length = Integer.MAX_VALUE)
    private String stack4;

    public ControllerData() {
    }



    public Integer getStringID() {
        return id;
    }

    public void setStringID(Integer stringID) {
        this.id = stringID;
    }


    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }


    public String getPlayer1Pile() {
        return player1Pile;
    }

    public void setPlayer1Pile(String player1Pile) {
        this.player1Pile = player1Pile;
    }

    public String getPlayer2Pile() {
        return player2Pile;
    }

    public void setPlayer2Pile(String player2Pile) {
        this.player2Pile = player2Pile;
    }

    public String getPlayer1PhaseString() {
        return player1PhaseString;
    }

    public void setPlayer1PhaseString(String player1PhaseString) {
        this.player1PhaseString = player1PhaseString;
    }

    public void setPlayer1PhaseString(IPhase phase) {
        this.player1PhaseString = phase.toString();
    }

    public String getPlayer2PhaseString() {
        return player2PhaseString;
    }

    public void setPlayer2PhaseString(String player2PhaseString) {
        this.player2PhaseString = player2PhaseString;
    }

    public void setPlayer2PhaseString(IPhase phase) {
        this.player2PhaseString = phase.toString();
    }

    public String getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(String discardPile) {
        this.discardPile = discardPile;
    }

    public String getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(String drawPile) {
        this.drawPile = drawPile;
    }

    public String getRoundState() {
        return roundState;
    }

    public void setRoundState(String roundState) {
        this.roundState = roundState;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStack1() {
        return stack1;
    }

    public void setStack1(String stack1) {
        this.stack1 = stack1;
    }

    public String getStack2() {
        return stack2;
    }

    public void setStack2(String stack2) {
        this.stack2 = stack2;
    }

    public String getStack3() {
        return stack3;
    }

    public void setStack3(String stack3) {
        this.stack3 = stack3;
    }

    public String getStack4() {
        return stack4;
    }

    public void setStack4(String stack4) {
        this.stack4 = stack4;
    }

    public void setStack(String stack, int stackNumber) {
        switch (stackNumber) {
            case 1:
                setStack1(stack);
                break;
            case 2:
                setStack2(stack);
                break;
            case 3:
                setStack3(stack);
                break;
            case 4:
                setStack4(stack);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}

