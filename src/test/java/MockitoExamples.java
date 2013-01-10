import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class MockitoExamples {

    private CheeseMonger cheeseMonger;

    @Before
    public void setup(){
        cheeseMonger = mock(CheeseMonger.class);
    }







    @Test
    public void exactParameter(){

        Mockito.when(cheeseMonger.timeTilMouldy("Edam")).thenReturn(5);
        print(cheeseMonger.timeTilMouldy("Edam"));

    }







    @Test
    public void fuzzyParameter(){

        Mockito.when(cheeseMonger.timeTilMouldy(anyString())).thenReturn(6);
        print(cheeseMonger.timeTilMouldy("Cheddar"));

    }







    @Test
    public void moreMatchers(){

        Mockito.when(cheeseMonger.timeTilMouldy(startsWith("a"))).thenReturn(10);
        Mockito.when(cheeseMonger.timeTilMouldy(startsWith("b"))).thenReturn(20);
        print(cheeseMonger.timeTilMouldy("az"));
        print(cheeseMonger.timeTilMouldy("br"));

    }







    @Test
    public void customMatcher(){

        Mockito.when(cheeseMonger.timeTilMouldy(argThat(length(6)))).thenReturn(20);
        print(cheeseMonger.timeTilMouldy("brieee"));

    }







    @Test
    public void exceptions(){

        Mockito.when(cheeseMonger.timeTilMouldy(anyString())).thenThrow(new RuntimeException("ohhhh gooood"));
        cheeseMonger.timeTilMouldy("banana");

    }








    @Test
    public void voidMethods(){

        doThrow(new RuntimeException("no can do")).when(cheeseMonger).throwAwayAllTheCheese();
        cheeseMonger.throwAwayAllTheCheese();

    }








    @Test
    public void verification(){

        cheeseMonger.throwAwayAllTheCheese();
        verify(cheeseMonger).throwAwayAllTheCheese();

    }







    @Test
    public void verificationModes(){

        cheeseMonger.throwAwayAllTheCheese();
        cheeseMonger.throwAwayAllTheCheese();
        verify(cheeseMonger, times(2)).throwAwayAllTheCheese();

    }









    @Test
    public void verificationOfParameters(){

        cheeseMonger.timeTilMouldy("Gruyere");
        verify(cheeseMonger).timeTilMouldy("Gruyere");
        verify(cheeseMonger).timeTilMouldy(anyString());
        verify(cheeseMonger).timeTilMouldy(argThat(is(length(7))));

    }







    @Test
    public void argumentCaptors(){

        cheeseMonger.timeTilMouldy("Gruyere");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(cheeseMonger).timeTilMouldy(captor.capture());
        print(captor.getValue());

    }







    @Test
    public void aBitOfHamcrest(){
        assertThat("banana", is(length(7)));
    }









    @Test
    public void aBitMoreHamcrest(){
        assertThat("banana", is(not(length(7))));
    }












    private static Matcher<String> length(final int i) {
        return new TypeSafeDiagnosingMatcher<String>() {
            @Override
            protected boolean matchesSafely(String item, Description mismatchDescription) {
                if(item.length() < i){
                    mismatchDescription.appendText("was too short");
                }else if(item.length() > i){
                    mismatchDescription.appendText("was too long");
                }
                return item.length() == i;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("length " + i);
            }
        };
    }


    private void print(Object o) {
        System.out.println(o);
    }

}
