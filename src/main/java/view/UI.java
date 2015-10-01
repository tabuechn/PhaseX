package view;

import model.stack.ICardStack;

/**
 * Created by Tarek on 24.09.2015. Be gratefull for this superior Code
 */
public interface UI {

    /**
     * Metod to show the mainscreen
     */
    void start();

    /**
     * Get a card from the open card stack
     */
    void drawOpen();

    /**
     * Get a card from the hidden card stack.
     */
    void drawHidden();

    /**
     * Discard a card to the open card stack.
     */
    void discardCard();

    /**
     * Method to tell Controller, to check if chosen cards fit to the current phase.
     */
    void layPhase();

    /**
     * Method to add a card to the desired stack.
     * @param desiredStack the stack, where the card should be added.
     */
    void addToStack(ICardStack desiredStack);

    /**
     * Method to shutdown the application.
     */
    void exit();
}
