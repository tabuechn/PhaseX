package util;

/**
 * Created by Tarek on 29.09.2015. Be grateful for this superior Code!
 */
public interface IObservable {
    void addObserver(IObserver s);
    void removeObserver(IObserver s);
    void removeAllObservers();
    void notifyObservers();
    void notifyObservers(Event e);
}
