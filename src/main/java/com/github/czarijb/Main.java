package com.github.czarijb;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class Main {
    public static void main(String[] args) throws ParserException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        InfixParser infixParser = new InfixParser();

        for(;;)
        {
            try
            {
                System.out.print("Введите выражение для вычисления, или введите \"exit\" для выхода\n-> ");
                String str = reader.readLine();
                if(str.equals("exit"))
                    break;
                double result = infixParser.evaluate(str);

                DecimalFormatSymbols s = new DecimalFormatSymbols();
                s.setDecimalSeparator('.');
                DecimalFormat f = new DecimalFormat("#,###.00", s);


                System.out.printf("%s = %s%n", str, f.format(result));

            }
            catch(ParserException e)
            {
                System.out.println(e);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
