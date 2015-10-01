package model.player;

import model.deckOfCards.IDeckOfCards;
import model.phase.IPhase;

/**
 * If everything works right this class was
 * created by Konraifen88 on 22.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public interface IPlayer {
    /**
     * Getter for the display name of a player
     * @return the name of the current player
     */
    String getPlayerName();

    /**
     * Getter for the current deck of cards of the player.
     * @return all cards of the current player
     */
    IDeckOfCards getDeckOfCards();

    /**
     * Method to set the new deck for the current player
     * @param cards current deck of cards
     */
    void setDeckOfCards(IDeckOfCards cards);
    /**
     * Getter for the current phase of the player.
     * @return phase of the player.
     */
    IPhase getPhase();

    /**
     * Method to set the next phase for the current player.
     */
    void nextPhase() ;

    /**
     * Setter if phase is discarded by the current player.
     * @param phaseDone status if the phase is discarded for the current player
     */
    void setPhaseDone(boolean phaseDone);

    /**
     * Getter for the phaseDone, when a phase is discarded.
     * @return status of the phase of the current player.
     */
    boolean isPhaseDone();

    /**
     * Getter for the playerNumber
     * @return number of the current player.
     */
    int getPlayerNumber();

    /**
     * Add the Points to the Player
     * @param points the number of Points to add
     */
    void addPoints(int points);

}
