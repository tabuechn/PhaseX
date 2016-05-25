package actors.actor;

import actors.message.DiscardMessage;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tabuechn on 25.05.2016.
 */
public class PlayerTurnNotFinishedActor extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(ActorMaster.class);
    private final ActorRef discardActor = getContext().actorOf(Props.create(DiscardActor.class), "discardActor");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DiscardMessage) {
            discardActor.forward(message, getContext());
        } else {
            LOG.error("unhandled message received");
            unhandled(message);
        }
    }


}
