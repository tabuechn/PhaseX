package persistence;

import controller.UIController;
import model.player.IPlayer;

/**
 * Created by tabuechn on 19.04.2016.
 */
public interface SaveSinglePlayerDAO {

    /**
     * Method to save the current game instance
     * @param controller the controller of the current game.
     */
    void saveGame(UIController controller);

    /**
     * Checks if the player has saved a game and returns the saved Controller.
     * @param player who wants to load game
     * @return the saved controller or a new controller if the player has never saved a game
     */
    UIController loadGame(IPlayer player);
}
