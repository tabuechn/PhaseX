package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarek on 29.09.2015. Be grateful for this superior Code!
 */
public class Observable implements IObservable {
    private final List<IObserver> subscribers = new ArrayList<>(2);

    @Override
    public void addObserver(IObserver s) {
        subscribers.add(s);
    }

    @Override
    public void removeObserver(IObserver s) {
        subscribers.remove(s);
    }

    @Override
    public void removeAllObservers() {
        subscribers.clear();
    }

    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }

    @Override
    public void notifyObservers(Event e) {
        for (IObserver observer : subscribers) {
            observer.update(e);
        }
    }
}

