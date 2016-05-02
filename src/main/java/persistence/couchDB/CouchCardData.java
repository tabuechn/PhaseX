package persistence.couchDB;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;

/**
 * If everything works right this class was
 * created by Konraifen88 on 25.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CouchCardData extends CouchDbDocument {

    @JsonProperty("_id")
    private String id;

    @JsonDeserialize(as = DeckOfCards.class)
    private IDeckOfCards cards;

    private String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public IDeckOfCards getCards() {
        return cards;
    }

    public void setCards(IDeckOfCards cards) {
        this.cards = cards;
    }
}
