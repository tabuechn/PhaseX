package model.roundState;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public enum StateEnum {
    START_PHASE(0),
    END_PHASE(1),
    DRAW_PHASE(2),
    PLAYER_TURN_FINISHED(3),
    PLAYER_TURN_NOT_FINISHED(4);

    private int valueOrdinal = 0;

    StateEnum(int ord) {
        this.valueOrdinal = ord;
    }

    public static StateEnum byOrdinal(int ord) {
        for (StateEnum m : StateEnum.values()) {
            if (m.valueOrdinal == ord) {
                return m;
            }
        }
        return null;
    }

    public static StateEnum getRoundNameByString(String color) {
        return StateEnum.valueOf(color);
    }
}
