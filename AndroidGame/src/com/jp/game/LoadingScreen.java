package com.jp.game;


import com.jp.framework.Game;
import com.jp.framework.Graphics;
import com.jp.framework.Screen;


public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }


    @Override
    public void update(float deltaTime) {
    	
        Graphics canvas = game.getGraphics();
       // Assets.menu = canvas.newImage("menu.png", ImageFormat.RGB565);
       // Assets.click = game.getAudio().createSound("menutheme.mp3");
        
        game.setScreen(new MainMenuScreen(game));


    }


    @Override
    public void paint(float deltaTime) {


    }


    @Override
    public void pause() {


    }


    @Override
    public void resume() {


    }


    @Override
    public void dispose() {


    }


    @Override
    public void backButton() {


    }
}