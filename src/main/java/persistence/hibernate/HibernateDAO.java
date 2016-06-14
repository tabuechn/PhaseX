package persistence.hibernate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.UIController;
import model.card.ICard;
import model.card.impl.CardDeserializer;
import model.player.IPlayer;
import model.player.impl.Player;
import model.stack.ICardStack;
import model.stack.json.GsonStackDeserializer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.SaveSinglePlayerDAO;

import java.util.List;

/**
 * Created by Tarek on 30.03.2016. Be grateful for this superior Code!
 */
public class HibernateDAO implements SaveSinglePlayerDAO {

    public static final int NUMBER_OF_STACKS = 4;
    private Gson gsonNormal;

    public HibernateDAO() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ICard.class, new CardDeserializer());
        gsonBuilder.registerTypeAdapter(ICardStack.class, new GsonStackDeserializer());
        gsonNormal = new Gson();
    }

    @Override
    public void saveGame(UIController controller) {

        HibernateControllerData cd = new HibernateControllerData();
        IPlayer[] players = controller.getPlayersArray();
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

        cd.setCurrentPlayerIndex(controller.getCurrentPlayerIndex());

        cd.setDiscardPile(gsonNormal.toJson(controller.getDiscardPile()));
        cd.setDrawPile(gsonNormal.toJson(controller.getDrawPile()));
        cd.setRoundState(controller.getRoundState().toString());
        cd.setStatusMessage(controller.getStatusMessage());

        setAllStacks(cd,controller);

        Session session = HibernateUtil.getInstance().getCurrentSession();

        Transaction trans = session.beginTransaction();
        deleteIfAlreadyExists(player1,session);
        session.save(cd);
        trans.commit();

    }

    private void deleteIfAlreadyExists(Player player1,Session session) {
        Criteria criteria = session.createCriteria(HibernateControllerData.class);
        List games = criteria.list();
        for (Object o : games) {
            HibernateControllerData hibernateControllerData = (HibernateControllerData)o;
            if(hibernateControllerData.getPlayer1().equals(player1)) {
                session.delete(o);
            }
        }
    }

    private void setAllStacks(HibernateControllerData cd, UIController controller) {
        List<ICardStack> allStacks = controller.getAllStacks();
        for(int i = 0; i < NUMBER_OF_STACKS;++i) {
            if(allStacks.size() > i) {
                cd.setStack(gsonNormal.toJson(allStacks.get(i)),i+1);
            }
        }
    }


    @Override
    public UIController loadGame(IPlayer player) {
        HibernateControllerData hibernateControllerData = getPlayersGame(player);
        if(hibernateControllerData != null)
            return hibernateControllerData.getController();
        else {
            return null;
        }
    }



    private HibernateControllerData getPlayersGame(IPlayer player) {
        Session session = HibernateUtil.getInstance().getCurrentSession();
        Transaction trans = session.beginTransaction();
        Criteria criteria = session.createCriteria(HibernateControllerData.class);
        List list = criteria.list();
        for (Object o : list) {
            HibernateControllerData hibernateControllerData = (HibernateControllerData)o;
            if(hibernateControllerData.getPlayer1().equals(player)) {
                trans.commit();
                return hibernateControllerData;
            }
        }
        return null;
    }

}
