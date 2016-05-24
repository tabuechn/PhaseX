package actors.actor;

import actors.message.DiscardMessage;
import akka.actor.UntypedActor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tabuechn on 24.05.2016.
 */
public class PlayerTurnFinishedActor extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(ActorMaster.class);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DiscardMessage) {
            //TODO discard actor
        } else {
            LOG.error("unhandled message received");
            unhandled(message);
        }
    }
}
