package phasex;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import controller.IController;
import controller.impl.Controller;


/**
 * Created by tabuechn on 07.10.2015.
 */
public class PhaseXModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IController.class).to(Controller.class).in(Singleton.class);

    }



}
