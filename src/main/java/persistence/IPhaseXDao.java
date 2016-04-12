package persistence;

import controller.UIController;
import model.player.IPlayer;

import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 23.03.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface IPhaseXDao {

    /**
     * Method to save the current game instance
     *
     * @param controller the controller of the current game.
     */
    void saveGame(UIController controller);

    /**
     * Checks if a game with the given Players is stored in db
     *
     * @param p1 name of the first player
     * @param p2 name of the second player
     * @return true if a game with the two players was found (doesn't matter if p1 and p2 is switched) or false if no game was found.
     */
    boolean isGameExisting(IPlayer p1, IPlayer p2);

    /**
     * Method to load a already existing game.
     *
     * @param p1 Playername of first Player
     * @param p2 Playername of second Player
     * @return The controller of the current game or null when game isn't found.
     */
    UIController loadGame(IPlayer p1, IPlayer p2);

    /**
     * Method to remove an active Game between 2 Players
     * @param p1 name of first player
     * @param p2 name of second player
     */
    void deleteGameForPlayers(IPlayer p1, IPlayer p2);

    /**
     * Method to remove all running games for the given Player
     * @param p1 name of the player
     */
    void deleteAllGamesForPlayer(IPlayer p1);

    /**
     * Method to get all active game of the current player
     *
     * @param player the player Object to find the active games for.
     * @return all active games of player
     */
    List<UIController> getAllSavedGamesForPlayer(IPlayer player);

    /**
     * Method to close the DB connection
     *
     * @return true if connection successfully closed, false if not.
     */
    boolean closeDBConnection();
}
