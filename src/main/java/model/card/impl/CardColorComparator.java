package model.card.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.card.ICard;

import java.util.Comparator;

/**
 * If everything works right this class was
 * created by Konraifen88 on 11.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
@SuppressFBWarnings("SE_COMPARATOR_SHOULD_BE_SERIALIZABLE")
public class CardColorComparator implements Comparator<ICard> {

    @Override
    public int compare(ICard o1, ICard o2) {
        return o1.getColor().compareTo(o2.getColor());
    }
}
