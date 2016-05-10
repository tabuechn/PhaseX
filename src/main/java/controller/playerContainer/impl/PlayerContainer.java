package controller.playerContainer.impl;

import controller.playerContainer.IPlayerContainer;
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

    private IPlayer[] players;
    private int currentPlayer;

    public PlayerContainer(String firstPlayerName){
        currentPlayer = FIRST_PLAYER;
        players = new Player[2];
        players[FIRST_PLAYER] = new Player(FIRST_PLAYER);
        players[FIRST_PLAYER].setName(firstPlayerName);
        players[SECOND_PLAYER] = new Player(SECOND_PLAYER);
        players[SECOND_PLAYER].setName(DEFAULT_PLAYER_2_NAME);
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return players[currentPlayer];
    }

    @Override
    public IPlayer getOtherPlayer() {
        if (currentPlayer == FIRST_PLAYER){
            return players[SECOND_PLAYER];
        }
        return players[FIRST_PLAYER];
    }

    @Override
    public void nextPlayer() {
        if (currentPlayer == SECOND_PLAYER) {
            currentPlayer = FIRST_PLAYER;
        } else {
            currentPlayer = SECOND_PLAYER;
        }
    }
}
