package actors.actor;

import akka.japi.Creator;

/**
 * Created by tabuechn on 11.05.2016.
 */
public class ActorMasteCreator implements Creator<ActorMaster> {
    @Override
    public ActorMaster create() throws Exception {
        return new ActorMaster();
    }
}
