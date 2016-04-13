package model.player.impl;

import model.card.impl.Card;
import model.card.impl.CardColorComparator;
import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.phase.IPhase;
import model.phase.impl.Phase1;
import model.player.IPlayer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * If everything works right this class was
 * created by Konraifen88 on 22.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
@Entity
@Table(name = "PhaseX_Player5")
public class Player implements IPlayer, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "PhaseX_PlayerNumber5")
    private int playerNumber;
    @Column(name ="PhaseX_Name5")
    private String name;
    @Column(name="PhaseX_PhaseDone6")

    private boolean phaseDone;
    @Column(name = "PhaseX_PlayerPoints5")
    private int points;
    @Transient
    private transient IPhase phase;
    @Transient
    private transient IDeckOfCards deck;

    public Player(int number) {
        this.phase = new Phase1();
        this.phaseDone = false;
        this.playerNumber = number;
    }

    public Player() {
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

    @Override
    public void setName(String name) {
        if (this.name == null || this.name.isEmpty()) {
            this.name = name;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else {
            Player other = (Player) o;
            return new EqualsBuilder().append(name.toLowerCase(), other.getPlayerName().toLowerCase()).isEquals();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
