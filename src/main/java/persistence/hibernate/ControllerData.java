package persistence.hibernate;

import model.card.ICard;
import model.card.impl.Card;
import model.player.IPlayer;
import model.player.impl.Player;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by tabuechn on 05.04.2016.
 */
@Entity
@Table(name = "PhaseX_Controller5")
public class ControllerData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="PhaseX_Player1")
    private Player player1;


    @Column(name="PhaseX_TestCard5")
    private String testCard;

    @Column(name="PhaseX_Player2")
    private Player player2;



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


    public String getTestCard() {
        return testCard;
    }

    public void setTestCard(String testCard) {
        this.testCard = testCard;
    }
}

