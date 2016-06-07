package persistence;

/**
 * Created by tabuechn on 07.05.2016.
 */
public enum DBEnum {
    HIBERNATE(0),
    COUCHEDB(1);

    private  int valueOrdinal = 0;

    DBEnum(int ord) {
        this.valueOrdinal = ord;
    }

}
