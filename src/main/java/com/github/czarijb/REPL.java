package com.github.czarijb;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class REPL {
    private static REPL ourInstance = new REPL();
    private static final String EXIT_COMAND = "exit";
private static final Logger LOG = LoggerFactory.getLogger(REPL.class);


    public static REPL getInstance() {
        return ourInstance;
    }

    private REPL() {
    }

    public void consoleREPL() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        InfixParser infixParser = new InfixParser();

        while (true) {
            try {
                System.out.print("Введите выражение для вычисления, или введите \"exit\" для выхода\n-> ");
                String str = reader.readLine();
                if (str.equals(EXIT_COMAND))
                    break;
                double result = infixParser.evaluate(str);

                DecimalFormatSymbols s = new DecimalFormatSymbols();
                s.setDecimalSeparator('.');
                DecimalFormat f = new DecimalFormat("#,###.00", s);


                System.out.printf("%s = %s%n", str, f.format(result));

            } catch (final ParseException e) {
                LOG.debug(e.getMessage());

            } catch (final Throwable e) {
                LOG.debug(e.getMessage());

            }
        }
    }
}
