package controller.container;

import model.player.IPlayer;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface IPlayerContainer {

    IPlayer[] getPlayers();

    void setPlayers(IPlayer[] players);

    void setCurrentPlayer(IPlayer player);

    void setOtherPlayer(IPlayer player);

    void setCurrentPlayerIndex(int index);

    int getCurrentPlayerIndex();

    IPlayer getCurrentPlayer();

    IPlayer getOtherPlayer();

    void nextPlayer();
}
