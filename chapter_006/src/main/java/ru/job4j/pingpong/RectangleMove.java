package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

public class RectangleMove implements Runnable {
    private final Rectangle rect;
    private HorizontalVector hv;
    private VerticalVector vv;
    private int speed;
    private int limitX;
    private int limitY;

    public RectangleMove(Rectangle rect, HorizontalVector hv, VerticalVector vv, int speed, int limitX, int limitY) {
        this.rect = rect;
        this.hv = hv;
        this.vv = vv;
        this.speed = speed;
        this.limitX = limitX;
        this.limitY = limitY;
    }

    private void move() {
        directControl();
        double xPos = rect.getX();
        double yPos = rect.getY();
        if (hv == HorizontalVector.Left) {
            rect.setX(xPos - speed);
        } else if (hv == HorizontalVector.Right) {
            rect.setX(xPos + speed);
        }
        if (this.vv == VerticalVector.Up) {
            rect.setY(yPos - speed);
        } else if (this.vv == VerticalVector.Down) {
            rect.setY(yPos + speed);
        }
    }

    private void directControl() {
        if (this.hv == HorizontalVector.Left && rect.getX() <= 0) {
            this.hv = HorizontalVector.Right;
        } else if (this.hv == HorizontalVector.Right && rect.getX() >= limitX) {
            this.hv = HorizontalVector.Left;
        }
        if (this.vv == VerticalVector.Up && rect.getY() <= 0) {
            this.vv = VerticalVector.Down;
        } else if (this.vv == VerticalVector.Down && rect.getY() >= limitY) {
            this.vv = VerticalVector.Up;
        }
    }

    @Override
    public void run() {
        while (true) {
            this.move();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    enum  HorizontalVector {
        Left, Right, Zero;
    }

    enum VerticalVector {
        Up, Down, Zero
    }
}