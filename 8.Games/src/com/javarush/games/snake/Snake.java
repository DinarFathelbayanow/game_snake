package com.javarush.games.snake;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    private final List<GameObject> snakeParts = new ArrayList<>();

    public void setDirection(Direction direction) {

        boolean x = snakeParts.get(0).x == snakeParts.get(1).x;
        boolean y = snakeParts.get(0).y == snakeParts.get(1).y;

        if ((this.direction == Direction.DOWN || this.direction == Direction.UP) && (y)){
            return;
        }
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && (x)){
            return;
        }

        if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        } else if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;
        } else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        } else if (direction == Direction.DOWN && this.direction == Direction.UP) {
            return;
        }

        this.direction = direction;
    }

    public Snake(int x, int y) {
        for (int i = 0; i < 3; i++) {
            snakeParts.add(new GameObject(x + i, y));
        }
    }

    public void draw(Game game) {
        for (GameObject gameObject : snakeParts) {
            if (snakeParts.indexOf(gameObject) == 0) {
                if (!isAlive) {
                    game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                } else {
                    game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                }
            } else if (0 < snakeParts.indexOf(gameObject)) {
                if (!isAlive) {
                    game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, BODY_SIGN, Color.RED, 75);
                } else {
                    game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                }
            }
        }
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x >= SnakeGame.WIDTH
                || newHead.x < 0
                || newHead.y >= SnakeGame.HEIGHT
                || newHead.y < 0) {
            isAlive = false;
            return;
        }
        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        }
        snakeParts.add(0, newHead);
        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
        } else {
            removeTail();
        }
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public GameObject createNewHead() {
        GameObject gameObject = snakeParts.get(0);
        if (direction == Direction.LEFT) {
            gameObject = new GameObject(gameObject.x - 1, gameObject.y);
        } else if (direction == Direction.DOWN) {
            gameObject = new GameObject(gameObject.x, gameObject.y + 1);
        } else if (direction == Direction.UP) {
            gameObject = new GameObject(gameObject.x, gameObject.y - 1);
        } else if (direction == Direction.RIGHT) {
            gameObject = new GameObject(gameObject.x + 1, gameObject.y);
        }
        return gameObject;
    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject part : snakeParts) {
            if (part.x == gameObject.x && part.y == gameObject.y) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }
}
