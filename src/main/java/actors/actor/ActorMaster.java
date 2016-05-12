package actors.actor;

import actors.message.MasterMessage;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class ActorMaster extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(ActorMaster.class);

    private final ActorRef drawPhaseHandler = getContext().actorOf(Props.create(DrawPhaseActor.class), "drawPhaseHandler");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof MasterMessage) {
            stateSwitcher((MasterMessage) message);
        } else {
            LOG.error("unknown message received");
            unhandled(message);
        }
    }

    private void stateSwitcher(MasterMessage message) {
        switch (message.getRoundState().getState()) {
            case DRAW_PHASE:
                drawPhaseHandler.forward(message, getContext());
                break;
            default:
                throw new IllegalStateException();
        }
    }


}
