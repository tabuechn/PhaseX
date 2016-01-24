package model.player.impl;

import model.card.impl.CardColorComparator;
import model.card.impl.CardValueComparator;
import model.deckOfCards.IDeckOfCards;
import model.phase.IPhase;
import model.phase.impl.Phase1;
import model.player.IPlayer;

/**
 * If everything works right this class was
 * created by Konraifen88 on 22.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class Player implements IPlayer {

    private final String name;
    private final int playerNumber;
    private boolean phaseDone;
    private int points;
    private IPhase phase;
    private IDeckOfCards deck;

    public Player(String name, int number) {
        this.name = name;
        this.phase = new Phase1();
        this.phaseDone = false;
        this.playerNumber = number;
    }

    @Override
    public String getPlayerName() {
        return name;
    }

    @Override
    public IDeckOfCards getDeckOfCards() {
        return deck;
    }

    @Override
    public void setDeckOfCards(IDeckOfCards cards) {
        if (phase.isNumberPhase())
            cards.sort(new CardColorComparator());
        else
            cards.sort(new CardValueComparator());
        this.deck = cards;
    }

    @Override
    public IPhase getPhase() {
        return phase;
    }

    @Override
    public void nextPhase() {
        this.phase = phase.getNextPhase();
    }

    @Override
    public boolean isPhaseDone() {
        return phaseDone;
    }

    @Override
    public void setPhaseDone(boolean phaseDone) {
        this.phaseDone = phaseDone;
    }

    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public void addPoints(int points) {
        this.points += points;
    }

    @Override
    public int getPoints() {
        return points;
    }
}
