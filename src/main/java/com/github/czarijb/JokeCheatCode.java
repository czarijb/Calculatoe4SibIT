package com.github.czarijb;

import javax.script.ScriptEngineManager;


public class JokeCheatCode {


    public Double cheatCode (String example) throws Exception{
        return (Double) new ScriptEngineManager().getEngineByName("JavaScript").eval(example);
    }
}
