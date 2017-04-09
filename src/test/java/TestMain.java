import com.github.czarijb.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by aleksandr on 09.04.17.
 */
public class TestMain {
    InfixParser infixParser = new InfixParser();

    @Test
    public void testEvaluate() throws ParserException {
        double res = infixParser.evaluate("1-2+5*5/25");
        Assert.assertTrue(res == 0.00);
    }

    JokeCheatCode jokeCheatCode = new JokeCheatCode();

    @Test
    public void testCheatCode() throws Exception {
        double res = jokeCheatCode.cheatCode("1-2+5*5/25+1/2");
        Assert.assertTrue(res == 0.5);
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
        } catch (Exception e) {

        }
    }

    @Test(expected = ParserException.class)
    public void testEvaluateWithInvalidExpression() throws ParserException {

        infixParser.evaluate("invalid expression");
    }

    @Test(expected = ParserException.class)
    public void testEvaluateWithoutExpression() throws ParserException {

        infixParser.evaluate("");
    }
}
