package controller;

import model.card.ICard;
import model.deck.IDeckOfCards;
import model.player.IPlayer;
import model.stack.ICardStack;
import model.state.StateEnum;
import util.IObservable;

import java.util.List;
import java.util.Map;

/**
 * Created by tabuechn on 11.10.2015. Be grateful for this code! ^(�.�)^
 */
public interface UIController extends IObservable {
    /**
     * Event to start the Game for the UIs
     * @param firstPlayerName name of the first player
     */
    void startGame(String firstPlayerName);

    /**
     * Event to draw a card from the discard pile
     */
    void drawOpen();

    /**
     * Event to draw a card from the hidden pile
     */
    void drawHidden();

    /**
     * Event to Discard a Card for the UIs
     *
     * @param card the card to be discarded
     */
    void discard(ICard card);

    /**
     * Event to lay down a Phase for the UIs
     *
     * @param phase the phase to be check and played
     */
    void playPhase(IDeckOfCards phase);

    /**
     * Event for the UIs to add a card to finished phase
     *
     * @param card  the card to add to the stack
     * @param stackNumber the index of the stack, the card should be added to
     */
    void addToFinishedPhase(ICard card, int stackNumber);

    /**
     * Method to add multiple cards to a desired stack
     *
     * @param cards       the List of Cards
     * @param stackNumber the current index of the stack
     */
    void addMultipleCardsToFinishedPhase(List<ICard> cards, int stackNumber);

    /**
     * Returns the cards which the current Player has in his hand
     *
     * @return the cards of the current Player
     */
    IDeckOfCards getCurrentPlayersHand();

    /**
     * Returns the number of cards for the next player
     *
     * @return number of cards for the next player
     */
    Map<Integer, Integer> getNumberOfCardsForNextPlayer();

    /**
     * Getter for the UIs to get all stacks which are played in the current Round
     *
     * @return List of all stacks
     */
    List<ICardStack> getAllStacks();

    void setAllStacks(List<ICardStack> allStacks);

    /**
     * Getter for the UIs to let them know, in which state the game is right now
     *
     * @return the current State
     */
    StateEnum getRoundState();

    void setRoundState(StateEnum roundState);

    /**
     * Getter for the draw pile
     *
     * @return the draw pile
     */
    IDeckOfCards getDrawPile();

    void setDrawPile(IDeckOfCards deck);

    /**
     * Getter for the discard pile
     *
     * @return the discard pile
     */
    IDeckOfCards getDiscardPile();

    void setDiscardPile(IDeckOfCards deck);

    /**
     * Getter for the current player
     *
     * @return the current player
     */
    IPlayer getCurrentPlayer();

    void setCurrentPlayer(int index);

    void setCurrentPlayer(IPlayer player);

    /**
     * Getter for the opponent player
     *
     * @return the opponent player
     */
    IPlayer getOpponentPlayer();

    /**
     * Stops the application
     */
    void exitEvent();

    /**
     * Getter for the status message
     * @return the current status message
     */
    String getStatusMessage();

    void setStatusMessage(String statusMessage);

    /**
     * Getter for the phase description
     *
     * @return description of the current phase
     */
    String getCurrentPhaseDescription();

    IPlayer getWinner();

    /**
     * Set the playername for Player 1
     * @param name         name of player 1
     */
    void setSecondPlayerName(String name);

    /**
     * Returns the Array of all Players
     * @return Array of all Players
     */
    IPlayer[] getPlayersArray();

    void setPlayersArray(IPlayer[] players);

    int getCurrentPlayerIndex();

    void setCurrentPlayerIndex(int index);

    void setPlayer1(IPlayer player1);

    void setPlayer2(IPlayer player2);
}
