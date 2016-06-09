package util;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by tabuechn on 09.06.2016.
 */
public class ObservableTest {
    private Observable observable;
    private IObserver observer;
    private IObserver observer2;
    private int eventCounter;

    private class TestObserver implements IObserver {

        @Override
        public void update(Event e) {
            eventCounter++;
        }
    }

    @Before
    public void setUp() {
        observable = new Observable();
        observer = new TestObserver();
        observer2 = new TestObserver();
        eventCounter = 0;
    }

    @Test
    public void addObserverTest() {
        observable.addObserver(observer);
        observable.notifyObservers();
        assertEquals(1, eventCounter);
    }

    @Test
    public void removeObserverTest() {
        observable.addObserver(observer);
        observable.addObserver(observer2);
        observable.notifyObservers(null);
        assertEquals(2, eventCounter);
        observable.removeObserver(observer);
        observable.notifyObservers(null);
        assertEquals(3, eventCounter);
    }

    @Test
    public void removeAllObserverTest() {
        observable.addObserver(observer);
        observable.addObserver(observer2);
        observable.notifyObservers(null);
        assertEquals(2, eventCounter);
        observable.removeAllObservers();
        observable.notifyObservers(null);
        assertEquals(2, eventCounter);
    }
}
