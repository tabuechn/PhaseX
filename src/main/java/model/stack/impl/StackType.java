package model.stack.impl;

/**
 * If everything works right this class was
 * created by konraifen88 on 03.05.2016..
 * If it doesn't work I don't know who the hell wrote it.
 */
public enum StackType {
    PAIR(0),
    STREET(1),
    COLOR(2);

    private int valueOrdinal = 0;

    StackType(int ord) {
        this.valueOrdinal = ord;
    }

    public static StackType byOrdinal(int ord) {
        for (StackType m : StackType.values()) {
            if (m.valueOrdinal == ord) {
                return m;
            }
        }
        return null;
    }
}
