package view;

import model.stack.ICardStack;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code
 */
public abstract class UI {

    /**
     * Method to show the mainScreen
     */
    public abstract void start();

    /**
     * Get a card from the open card stack
     */
    public abstract void drawOpen();

    /**
     * Get a card from the hidden card stack.
     */
    public abstract void drawHidden();

    /**
     * Discard a card to the open card stack.
     */
    public abstract void discardCard();

    /**
     * Method to tell Controller, to check if chosen cards fit to the current phase.
     */
    public abstract void layPhase();

    /**
     * Method to add a card to the desired stack.
     *
     * @param desiredStack the stack, where the card should be added.
     */
    public abstract void addToStack(ICardStack desiredStack);

    /**
     * Method to shutdown the application.
     */
    public abstract void exit();
}
