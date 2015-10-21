package model.card;

/**
 * If everything works right this class was
 * created by Konraifen88 on 11.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public enum CardValue {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11),
    TWELVE(12);

/* Example to add points to the various cards
    Owl(2) {
        public String speak() {
            return "Hoo";
        }
    };
    abstract public String speak();*/

    private int valueOrdinal = 0;

    CardValue(int ord) {
        this.valueOrdinal = ord;
    }

    public static CardValue byOrdinal(int ord) {
        for (CardValue m : CardValue.values()) {
            if (m.valueOrdinal == ord) {
                return m;
            }
        }
        return null;
    }

}
