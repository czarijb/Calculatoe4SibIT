import com.github.czarijb.InfixParser;
import com.github.czarijb.JokeCheatCode;
import com.github.czarijb.ParserException;
import org.junit.Assert;
import org.junit.Test;

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
}
