package controller.states;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.states.impl.StartPhase;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * If everything works right this class was
 * created by konraifen88 on 07.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class SerializerAndDeserializerTest {

    private AbstractState testState;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        testState = new StartPhase();
        mapper = new ObjectMapper();
    }

    @Test
    public void serialize() throws Exception {
        String json = mapper.writeValueAsString(testState);
        System.out.println(json);
        assertTrue(json.contains("\"state\""));
    }

    @Test
    public void deserialize() throws Exception {
        AbstractState startPhase = mapper.readValue("{\"state\":\"StartPhase\"}", AbstractState.class);
        assertTrue(startPhase instanceof StartPhase);
    }

}