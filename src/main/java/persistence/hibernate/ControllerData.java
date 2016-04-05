package persistence.hibernate;

import model.player.IPlayer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tabuechn on 05.04.2016.
 */
@Entity
@Table(name = "ControllerData")
public class ControllerData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stringID;

    @Column(name = "player1name")
    private String player1Name;

    @Column(name = "player2name")
    private String player2Name;



    public ControllerData() {
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public Integer getStringID() {
        return stringID;
    }

    public void setStringID(Integer stringID) {
        this.stringID = stringID;
    }
}

