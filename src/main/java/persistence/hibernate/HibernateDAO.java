package persistence.hibernate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.UIController;
import model.card.ICard;
import model.card.impl.CardDeserializer;
import model.player.IPlayer;
import model.player.impl.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.IPhaseXDao;

import java.util.List;

/**
 * Created by Tarek on 30.03.2016. Be grateful for this superior Code!
 */
public class HibernateDAO implements IPhaseXDao {

    Gson gson;

    public HibernateDAO() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ICard.class, new CardDeserializer());
        gson = gsonBuilder.create();
    }

    @Override
    public void saveGame(UIController controller) {
        ControllerData cd = new ControllerData();

        cd.setPlayer1((Player)controller.getCurrentPlayer());
        cd.setPlayer1PhaseString(controller.getCurrentPlayer().getPhase().toString());
        String player1Hand = gson.toJson(controller.getCurrentPlayersHand());
        cd.setPlayer1Pile(player1Hand);

        cd.setPlayer2((Player)controller.getOpponentPlayer());
        cd.setPlayer2PhaseString(controller.getOpponentPlayer().getPhase().toString());
        String player2Hand = gson.toJson(controller.getOpponentPlayer().getDeckOfCards());
        cd.setPlayer1Pile(player2Hand);

        cd.setDiscardPile(gson.toJson(controller.getDrawPile()));
        cd.setDrawPile(gson.toJson(controller.getDiscardPile()));

        Session session = HibernateUtil.getInstance().getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.save(cd);
        trans.commit();
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
