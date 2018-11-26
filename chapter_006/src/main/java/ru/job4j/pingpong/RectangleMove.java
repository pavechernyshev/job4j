package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

public class RectangleMove implements Runnable {
    private final Rectangle rect;
    private final Vector vector;
    private final int speed;
    private final int limitX;
    private final int limitY;

    public RectangleMove(Rectangle rect, HorizontalVector hv, VerticalVector vv, int speed, int limitX, int limitY) {
        this.rect = rect;
        this.speed = speed;
        this.limitX = limitX;
        this.limitY = limitY;
        this.vector = new Vector(hv, vv);
    }

    private void move() {
        directControl();
        double xPos = rect.getX();
        double yPos = rect.getY();
        if (vector.getHv() == HorizontalVector.Left) {
            rect.setX(xPos - speed);
        } else if (vector.getHv() == HorizontalVector.Right) {
            rect.setX(xPos + speed);
        }
        if (vector.getVv() == VerticalVector.Up) {
            rect.setY(yPos - speed);
        } else if (vector.getVv() == VerticalVector.Down) {
            rect.setY(yPos + speed);
        }
    }

    private void directControl() {
        if (vector.getHv() == HorizontalVector.Left && rect.getX() <= 0) {
            vector.setHv(HorizontalVector.Right);
        } else if (vector.getHv() == HorizontalVector.Right && rect.getX() >= limitX) {
            vector.setHv(HorizontalVector.Left);
        }
        if (vector.getVv() == VerticalVector.Up && rect.getY() <= 0) {
            vector.setVv(VerticalVector.Down);
        } else if (vector.getVv() == VerticalVector.Down && rect.getY() >= limitY) {
            vector.setVv(VerticalVector.Up);
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
    class Vector {
        private HorizontalVector hv;
        private VerticalVector vv;

        Vector(HorizontalVector hv, VerticalVector vv) {
            this.hv = hv;
            this.vv = vv;
        }

        public HorizontalVector getHv() {
            return hv;
        }

        public VerticalVector getVv() {
            return this.vv;
        }

        public void setHv(HorizontalVector hv) {
            this.hv = hv;
        }

        public void setVv(VerticalVector vv) {
            this.vv = vv;
        }
    }

    enum  HorizontalVector {
        Left, Right, Zero;
    }

    enum VerticalVector {
        Up, Down, Zero
    }
}