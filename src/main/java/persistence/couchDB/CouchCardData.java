package persistence.couchDB;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import model.card.ICard;
import model.card.impl.Card;
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

    @JsonDeserialize(as = Card.class)
    private ICard card;

    private String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ICard getCard() {
        return card;
    }

    public void setCard(ICard card) {
        this.card = card;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
