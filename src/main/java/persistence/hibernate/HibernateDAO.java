package persistence.hibernate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.UIController;
import model.card.ICard;
import model.card.impl.CardDeserializer;
import model.player.IPlayer;
import model.player.impl.Player;
import model.stack.ICardStack;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.IPhaseXDao;

import java.util.List;

/**
 * Created by Tarek on 30.03.2016. Be grateful for this superior Code!
 */
public class HibernateDAO implements IPhaseXDao {

    Gson gson;
    Gson gsonNormal;

    public static  final int NUMBER_OF_STACKS = 4;

    public HibernateDAO() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ICard.class, new CardDeserializer());
        gson = gsonBuilder.create();
        gsonNormal = new Gson();
    }

    @Override
    public void saveGame(UIController controller) {
        ControllerData cd = new ControllerData();
        IPlayer[] players = controller.getPlayers();
        Player player1 = (Player)players[0];
        Player player2 = (Player)players[1];

        cd.setPlayer1(player1);
        cd.setPlayer1PhaseString(player1.getPhase().toString());
        String player1Hand = gsonNormal.toJson(player1.getDeckOfCards());
        cd.setPlayer1Pile(player1Hand);

        cd.setPlayer2(player2);
        cd.setPlayer2PhaseString(player2.getPhase().toString());
        String player2Hand = gsonNormal.toJson(player2.getDeckOfCards());
        cd.setPlayer2Pile(player2Hand);

        cd.setDiscardPile(gsonNormal.toJson(controller.getDiscardPile()));
        cd.setDrawPile(gsonNormal.toJson(controller.getDrawPile()));
        cd.setRoundState(controller.getRoundState().toString());
        cd.setStatusMessage(controller.getStatusMessage());

        setAllStacks(cd,controller);

        Session session = HibernateUtil.getInstance().getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.save(cd);
        trans.commit();

    }

    private void setAllStacks(ControllerData cd, UIController controller) {
        List<ICardStack> allStacks = controller.getAllStacks();
        for(int i = 0; i < NUMBER_OF_STACKS;++i) {
            if(allStacks.size() > i) {
                cd.setStack(gsonNormal.toJson(allStacks.get(i)),i+1);
            }
        }
    }

    @Override
    public boolean isGameExisting(IPlayer p1, IPlayer p2) {
        return false;
    }

    @Override
    public UIController loadGame(IPlayer p1, IPlayer p2) {
        return null;
    }

    @Override
    public void deleteGameForPlayers(IPlayer p1, IPlayer p2) {

    }

    @Override
    public void deleteAllGamesForPlayer(IPlayer p1) {

    }

    @Override
    public List<UIController> getAllSavedGamesForPlayer(IPlayer player) {
        return null;
    }

    @Override
    public boolean closeDBConnection() {
        return false;
    }
}
