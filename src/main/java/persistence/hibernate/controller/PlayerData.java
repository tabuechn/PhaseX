package persistence.hibernate.controller;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tabuechn on 05.04.2016.
 */
@Entity
@Table(name = "Player")
public class PlayerData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "playerName")
    private String playerName;

    public PlayerData() {
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
