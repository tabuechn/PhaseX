package actors.actor;

import actors.message.DrawHiddenMessage;
import actors.message.DrawOpenMessage;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class ActorMaster extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(ActorMaster.class);

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof DrawOpenMessage){
            LOG.debug("DrawOpenMessage received");
            System.out.println("got draw open message");
            getSender().tell(true,getSelf());
        } else if (message instanceof DrawHiddenMessage){
            LOG.debug("DrawHiddenMessage received");
            System.out.println("got draw hidden message");

        } else {
            LOG.error("unknown message received");
            System.out.println("unknown message received");
            unhandled(message);
        }
    }


}
