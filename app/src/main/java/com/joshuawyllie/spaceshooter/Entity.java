package com.joshuawyllie.spaceshooter;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Entity {

    static final String TAG = "Entity";
    static Game _game = null; //shared ref, managed by the Game-class!
    float _x = 0;
    float _y = 0;
    float _width = 0;
    float _height = 0;
    float _velX = 0;
    float _velY = 0;

    void update() {
        _x += _velX;
        _y += _velY;
    }
    void render(final Canvas canvas, final Paint paint) {}
    void onCollision(final Entity that) {}
    void destroy() {}

    float left() {
        return _x;
    }
    float right() {
        return _x + _width;
    }
    float top() {
        return _y;
    }
    float bottom() {
        return _y + _height;
    }
    float centerX() {
        return _x + (_width * 0.5f);
    }
    float centerY() {
        return _y + (_height * 0.5f);
    }

    void setLeft(final float leftEdgePosition) {
        _x = leftEdgePosition;
    }
    void setRight(final float rightEdgePosition) {
        _x = rightEdgePosition - _width;
    }
    void setTop(final float topEdgePosition) {
        _y = topEdgePosition;
    }
    void setBottom(final float bottomEdgePosition) {
        _y = bottomEdgePosition - _height;
    }
    void setCenter(final float x, final float y) {
        _x = x - (_width * 0.5f);
        _y = y - (_height * 0.5f);
    }

    boolean isColliding(final Entity that) {
        if (this == that) {
            throw new AssertionError("isColliding: You shouldn't test Entities against themselves!");
        }
        return Entity.isAABBOverlapping(this, that);
    }

    //Some good reading on bounding-box intersection tests:
    //https://gamedev.stackexchange.com/questions/586/what-is-the-fastest-way-to-work-out-2d-bounding-box-intersection
    static boolean isAABBOverlapping(final Entity a, final Entity b) {
        return !(a.right() <= b.left()
                || b.right() <= a.left()
                || a.bottom() <= b.top()
                || b.bottom() <= a.top());
    }


}
