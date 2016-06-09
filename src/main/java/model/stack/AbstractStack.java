package model.stack;

import model.deck.IDeckOfCards;
import model.stack.impl.StackType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by tabuechn on 09.06.2016.
 */
public abstract class AbstractStack implements ICardStack{

    protected final StackType type;

    protected final IDeckOfCards list;

    public AbstractStack(StackType type, IDeckOfCards deck) {
        this.type = type;
        this.list = deck;
    }

    @Override
    public IDeckOfCards getList() {
        return this.list;
    }

    @Override
    public StackType getStackType() {
        return this.type;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.type).append(this.list).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (o == this) {
            return true;
        }
        ICardStack other = (ICardStack) o;
        return new EqualsBuilder().append(this.type, other.getStackType()).append(this.list, other.getList())
                .isEquals();
    }
}
