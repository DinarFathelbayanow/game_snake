package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;
    private Snake snake;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;
    private  int turnDelay;

    private void createGame() {
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        drawScene();
    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i, j,Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive){
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);

            createNewApple();
        }
        if (!snake.isAlive){
            gameOver();
        }
        if (snake.getLength() > GOAL){
            win();
        }
        drawScene();
    }

    @Override
    public void initialize() {
        super.initialize();
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT){
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT){
            snake.setDirection(Direction.RIGHT);
        }  else if (key == Key.UP){
            snake.setDirection(Direction.UP);
        }  else if (key == Key.DOWN){
            snake.setDirection(Direction.DOWN);
        }
        if (isGameStopped && key == Key.SPACE){
            createGame();
        }
    }
    private void createNewApple(){
        Apple newApple;
        do {
            int a = getRandomNumber(WIDTH);
            int b = getRandomNumber(HEIGHT);
            newApple = new Apple(a, b);
        }
        while (snake.checkCollision(newApple));
            apple = newApple;



    }
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER! =)", Color.RED, 50);
    }
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WIN! =)", Color.GREEN, 50);
    }

}
