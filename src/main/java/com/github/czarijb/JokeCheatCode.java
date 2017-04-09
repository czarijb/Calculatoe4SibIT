package com.github.czarijb;

import javax.script.ScriptEngineManager;

public class JokeCheatCode {

    public Double cheatCode (final String example) throws Exception{
        return (Double) new ScriptEngineManager().getEngineByName("JavaScript").eval(example);
    }
}
