package model.card;

/**
 * If everything works right this class was
 * created by Konraifen88 on 22.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public enum CardColor {
    YELLOW(0),
    RED(1),
    BLUE(2),
    GREEN(3),
    BACK(4),
    BLANK(5);

    private int valueOrdinal = 0;

    CardColor(int ord) {
        this.valueOrdinal = ord;
    }

    public static CardColor byOrdinal(int ord) {
        for (CardColor m : CardColor.values()) {
            if (m.valueOrdinal == ord) {
                return m;
            }
        }
        return null;
    }

    public static CardColor getColorByString(String color) {
        switch(color) {
            case "YELLOW":
                return YELLOW;
            case "RED":
                return RED;
            case "BLUE":
                return BLUE;
            case "GREEN":
                return GREEN;
            case "BACK":
                return BACK;
            case "BLANK":
                return BLANK;
            default:
                throw new IllegalStateException();
        }
    }
}
