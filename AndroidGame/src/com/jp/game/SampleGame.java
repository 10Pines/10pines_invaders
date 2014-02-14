package com.jp.game;

import com.jp.framework.Screen;
import com.jp.framework.implementation.AndroidGame;

public class SampleGame extends AndroidGame {
    @Override
    public Screen getInitScreen() {
        return new LoadingScreen(this); 
    }
    
}