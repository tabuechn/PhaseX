package persistence.hibernate.controller;

import model.card.CardColor;
import model.card.CardValue;

import javax.persistence.*;

/**
 * Created by tabuechn on 05.04.2016.
 */
@Entity
@Table(name="Card")
public class CardData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="color")
    private CardColor color;

    @Column(name="number")
    private CardValue value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public CardValue getValue() {
        return value;
    }

    public void setValue(CardValue value) {
        this.value = value;
    }
}
