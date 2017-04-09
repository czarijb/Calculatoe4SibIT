import com.github.czarijb.*;
import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;

public class TestMain {
    InfixParser infixParser = new InfixParser();
    JokeCheatCode jokeCheatCode = new JokeCheatCode();

    @Test
    public void testEvaluate() throws ParseException {
        double res = infixParser.evaluate("1-2+5*5/25");
        Assert.assertTrue(res == 0.00);
    }

    @Test
    public void testCheatCode() throws Exception {
        double res = jokeCheatCode.cheatCode("1.25-2.25+5*5/25");
        Assert.assertTrue(res < 0.0000001);
    }

    @Test
    public void testHandleErr() {
        Method method = null;
        String testString = "No Expression Present";

        try {
            method = InfixParser.class.getDeclaredMethod("handleErr");
            method.setAccessible(true);
            String str = (String) method.invoke(infixParser, ErrorType.NOEXP);
            Assert.assertTrue(str.equals(testString));
        } catch (NoSuchMethodException e) {

        }catch ( IllegalAccessException e){

        }catch (InvocationTargetException e){

        }
    }

    @Test(expected = ParseException.class)
    public void testEvaluateWithInvalidExpression() throws ParseException {
        infixParser.evaluate("invalid expression");
    }

    @Test(expected = ParseException.class)
    public void testEvaluateWithoutExpression() throws ParseException {
        infixParser.evaluate("");
    }
}
