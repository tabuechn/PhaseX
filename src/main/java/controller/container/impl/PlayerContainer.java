package controller.container.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controller.container.IPlayerContainer;
import model.player.IPlayer;
import model.player.impl.Player;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PlayerContainer implements IPlayerContainer {

    private static final String DEFAULT_PLAYER_2_NAME = "player2";
    private static final int FIRST_PLAYER = 0;
    private static final int SECOND_PLAYER = 1;

    @JsonDeserialize(contentAs = IPlayer.class)
    @JsonSerialize(contentAs = IPlayer.class)
    private IPlayer[] players;


    private int currentPlayerIndex;


    @JsonCreator
    public PlayerContainer() {
        players = new Player[2];
    }

    public PlayerContainer(String firstPlayerName){
        currentPlayerIndex = FIRST_PLAYER;
        players = new Player[2];
        players[FIRST_PLAYER] = new Player(FIRST_PLAYER);
        players[FIRST_PLAYER].setName(firstPlayerName);
        players[SECOND_PLAYER] = new Player(SECOND_PLAYER);
        players[SECOND_PLAYER].setName(DEFAULT_PLAYER_2_NAME);
    }

    @JsonProperty("players")
    public IPlayer[] getPlayers() {
        return players;
    }

    @JsonProperty("players")
    public void setPlayers(IPlayer[] players) {
        this.players = players;
    }

    @Override
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    @Override
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    @Override
    @JsonIgnore
    public IPlayer getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    //TODO: Check if this is correct/needed, better way would be to keep the Array as is and only change the index...
    @Override
    public void setCurrentPlayer(IPlayer player) {
        players[0] = player;
    }

    @Override
    @JsonIgnore
    public IPlayer getOtherPlayer() {
        if (currentPlayerIndex == FIRST_PLAYER) {
            return players[SECOND_PLAYER];
        }
        return players[FIRST_PLAYER];
    }

    @Override
    public void setOtherPlayer(IPlayer player) {
        players[1] = player;
    }

    @Override
    public void nextPlayer() {
        if (currentPlayerIndex == SECOND_PLAYER) {
            currentPlayerIndex = FIRST_PLAYER;
        } else {
            currentPlayerIndex = SECOND_PLAYER;
        }
    }
}
