import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class MockitoWithAnnotation {

    @Mock
    CheeseMonger cheeseMonger;

    @Captor
    ArgumentCaptor<String> captor;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void examplerate(){

        cheeseMonger.timeTilMouldy("thing");
        verify(cheeseMonger).timeTilMouldy(captor.capture());
        assertEquals("thing", captor.getValue());

    }

}
