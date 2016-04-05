package persistence.hibernate;

import model.player.IPlayer;
import persistence.hibernate.controller.PlayerData;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tabuechn on 05.04.2016.
 */
@Entity
@Table(name = "ControllerDataTest1234567891")
public class ControllerData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stringID;

    @Column(name="player1Data")
    private PlayerData player1Data;

    @Column(name="player2Data")
    private PlayerData player2Data;



    public ControllerData() {
    }



    public Integer getStringID() {
        return stringID;
    }

    public void setStringID(Integer stringID) {
        this.stringID = stringID;
    }

    public PlayerData getPlayer1Data() {
        return player1Data;
    }

    public void setPlayer1Data(PlayerData player1Data) {
        this.player1Data = player1Data;
    }

    public PlayerData getPlayer2Data() {
        return player2Data;
    }

    public void setPlayer2Data(PlayerData player2Data) {
        this.player2Data = player2Data;
    }
}

