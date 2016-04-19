package persistence;

import controller.UIController;
import model.player.IPlayer;

/**
 * Created by tabuechn on 19.04.2016.
 */
public interface IControllerData {

    /**
     * creates the controller form the data
     * @return working controller in the saved state
     */
    UIController getController();
}
