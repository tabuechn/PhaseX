package model.phase.splitter;

import model.deckOfCards.IDeckOfCards;
import model.phase.IPhaseSplitter;

import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 12.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class ColorSplitter implements IPhaseSplitter {

    private final int size;

    public ColorSplitter(int size) {
        this.size = size;
    }

    @Override
    public List<IDeckOfCards> split(IDeckOfCards cards) {
        return null;
    }
}
