package model.card.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
@Entity
@Table(name = "PhaseX_Card5")
@JsonSerialize(using = Card.Serializer.class)
@JsonDeserialize(using = Card.Deserializer.class)
public class Card implements ICard, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private String id;

    @JsonProperty("_rev")
    private String revision;

    @Column(name = "PhaseX_CardValue5")
    private CardValue number;
    @Column(name = "PhaseX_CardColor5")
    private CardColor color;

    public Card() {
    }

    public Card(CardValue cardNumber, CardColor cardColor) {
        this.number = cardNumber;
        this.color = cardColor;
    }

    @Override
    public CardValue getNumber() {
        return number;
    }

    @Override
    public void setNumber(CardValue value) {
        this.number = value;
    }

    @Override
    public CardColor getColor() {
        return color;
    }

    @Override
    public void setColor(CardColor color) {
        this.color = color;
    }

    @Override
    public int compareTo(@Nonnull ICard other) {
        return this.number.ordinal() - other.getNumber().ordinal();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.number).append(this.color).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (o == this) {
            return true;
        }
        Card other = (Card) o;
        return new EqualsBuilder().append(this.number, other.getNumber()).append(this.color, other.getColor())
                .isEquals();
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public static class Serializer extends JsonSerializer<Card> {


        @Override
        public void serialize(Card value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStartObject();
            jgen.writeNumberField("Color", value.getColor().ordinal());
            jgen.writeNumberField("Value", value.getNumber().ordinal());
            jgen.writeEndObject();
        }
    }

    public static class Deserializer extends JsonDeserializer<Card> {
        @Override
        public Card deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            CardColor color = CardColor.byOrdinal(Integer.parseInt(node.get("color").getTextValue()));
            CardValue value = CardValue.byOrdinal(Integer.parseInt(node.get("value").getTextValue()));
            return new Card(value, color);
        }
    }
}
