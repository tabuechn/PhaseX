package model.card.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.card.ICard;

import java.util.Comparator;

/**
 * Created by Tarek on 22.09.2015. Be grateful for this superior Code
 */
@SuppressFBWarnings("SE_COMPARATOR_SHOULD_BE_SERIALIZABLE")
public class CardValueComparator implements Comparator<ICard> {

    @Override
    public int compare(ICard o1, ICard o2) {
        return o1.getNumber().ordinal() - o2.getNumber().ordinal();
    }
}
