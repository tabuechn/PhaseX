package controller.impl;

import actors.message.MasterMessage;
import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import controller.UIController;
import model.deck.IDeckOfCards;
import model.player.IPlayer;
import model.roundState.IRoundState;
import model.roundState.StateEnum;
import model.roundState.impl.RoundState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import scala.concurrent.Await;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.Future;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * If everything works right this class was
 * created by konraifen88 on 30.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Patterns.class, Await.class})
public class ActorControllerTest {

    private static final String TEST_PLAYER_1 = "Player1";

    @InjectMocks
    private ActorController testee = new ActorController();

    @Mock
    private IDeckOfCards drawPileMock;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void dummyTest(){
        assertTrue(true);
    }

    @Test
    public void drawHiddenShouldNotChangeThePlayersIfActorWorkedCorrectly() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        settingUpMocksForDraw();
        IPlayer playerBefore = testee.getCurrentPlayer();
        testee.drawHidden();
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getCurrentPlayer(), playerBefore);
    }

    private void verifyThatActorIsCalledCorrect() throws Exception {
        verifyStatic();
        Patterns.ask(any(ActorRef.class), any(MasterMessage.class), any(Timeout.class));
        verifyStatic();
        Await.result(any(), any(FiniteDuration.class));
    }

    private void settingUpMocksForDraw() throws Exception {
        PowerMockito.mockStatic(Patterns.class);
        PowerMockito.when(Patterns.ask(any(ActorRef.class), any(MasterMessage.class), any(Timeout.class))).thenReturn(null);
        PowerMockito.mockStatic(Await.class);
        PowerMockito.when(Await.result(any(), any(FiniteDuration.class))).thenReturn(true);
    }

}