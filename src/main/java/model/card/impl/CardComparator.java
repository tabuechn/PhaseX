package model.card.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.card.ICard;

import java.util.Comparator;

/**
 * Created by Tarek on 22.09.2015. Be gratefull for this superior Code
 */
@SuppressFBWarnings("SE_COMPARATOR_SHOULD_BE_SERIALIZABLE")
public class CardComparator implements Comparator<ICard> {

    @Override
    public int compare(ICard o1, ICard o2) {
        return o1.getNumber() - o2.getNumber();
    }
}
