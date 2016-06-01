package model.player.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import model.card.ICard;
import model.card.impl.CardColorComparator;
import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.phase.IPhase;
import model.phase.impl.*;
import model.player.IPlayer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;

/**
 * If everything works right this class was
 * created by Konraifen88 on 22.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
@Entity
@Table(name = "PhaseX_Player6")
public class Player implements IPlayer, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "PhaseX_PlayerNumber")
    private int playerNumber;

    @Column(name ="PhaseX_Name")
    private String name ="";

    @Column(name="PhaseX_PhaseDone")
    private boolean phaseDone;

    @Column(name = "PhaseX_PlayerPoints")
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
    public void setPhase(String phaseName) {
        switch (phaseName) {
            case "Phase1":
                this.phase = new Phase1();
                break;
            case "Phase2":
                this.phase = new Phase2();
                break;
            case "Phase3":
                this.phase = new Phase3();
                break;
            case "Phase4":
                this.phase = new Phase4();
                break;
            case "Phase5":
                this.phase = new Phase5();
                break;
            default:
                throw new IllegalStateException();
        }
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
        if (this.name == null || this.name.isEmpty() || this.name.equals("player2")) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Serializer extends JsonSerializer<Player> {

        @Override
        public void serialize(Player value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStartObject();
            jgen.writeNumberField("playerNumber", value.getPlayerNumber());
            jgen.writeStringField("name", value.getPlayerName());
            jgen.writeStringField("phase", value.getPhase().toString());
            jgen.writeArrayFieldStart("cards");
            for (ICard card : value.getDeckOfCards()) {
                jgen.writeObject(card);
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }


    public static class Deserializer extends JsonDeserializer<Player> {
        @Override
        public Player deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jp);
            Player tmp = new Player(node.get("playerNumber").asInt());
            tmp.setName(node.get("name").asText());
            tmp.setPhase(node.get("phase").asText());

            JsonNode cards = node.path("cards");
            IDeckOfCards deckOfCards = mapper.readValue(cards.traverse(), DeckOfCards.class);
            tmp.setDeckOfCards(deckOfCards);

            return tmp;
        }
    }
}
