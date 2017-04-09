package com.github.czarijb;

import javax.script.ScriptEngineManager;


public class JokeCheatCode {


    public String cheatCode (String example) throws Exception{
        return String.valueOf(new ScriptEngineManager().getEngineByName("JavaScript").eval(example));
    }
}
