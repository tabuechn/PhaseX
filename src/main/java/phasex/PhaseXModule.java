package phasex;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import controller.UIController;
import controller.impl.ActorController;


/**
 * Created by tabuechn on 07.10.2015. Be grateful for this code! ^(�.�)^
 */
public class PhaseXModule extends AbstractModule {

    @Override
    public void configure() {
        bind(UIController.class).to(ActorController.class).in(Singleton.class);
    }



}
