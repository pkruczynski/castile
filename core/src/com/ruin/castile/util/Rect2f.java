/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ruin.castile.util;

import com.badlogic.gdx.math.Vector2;

import java.util.Objects;

/**
 * @author Immortius
 */
public final class Rect2f {
    public static final Rect2f EMPTY = new Rect2f();

    // position
    private float posX;
    private float posY;

    // size
    private float w;
    private float h;

    private Rect2f() {
    }

    private Rect2f(float x, float y, float w, float h) {
        this.posX = x;
        this.posY = y;

        this.w = w;
        this.h = h;
    }

    public static Rect2f createFromMinAndSize(float x, float y, float width, float height) {
        if (width <= 0 || height <= 0) {
            return EMPTY;
        }
        return new Rect2f(x, y, width, height);
    }

    public static Rect2f createFromMinAndSize(Vector2 min, Vector2 size) {
        return createFromMinAndSize(min.x, min.y, size.x, size.y);
    }

    public static Rect2f createFromMinAndMax(float minX, float minY, float maxX, float maxY) {
        if (maxX <= minX || maxY <= minY) {
            return EMPTY;
        }
        return new Rect2f(minX, minY, maxX - minX, maxY - minY);
    }

    public static Rect2f createFromMinAndMax(Vector2 min, Vector2 max) {
        return createFromMinAndMax(min.x, min.y, max.x, max.y);
    }

    public static Rect2f createEncompassing(Vector2 a, Vector2 b) {
        return createEncompassing(a.x, a.y, b.x, b.y);
    }

    public static Rect2f createEncompassing(float ax, float ay, float bx, float by) {
        return createFromMinAndMax(Math.min(ax, bx), Math.min(ay, by), Math.max(ax, bx), Math.max(ay, by));
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    /**
     * @return The smallest vector in the region
     */
    public Vector2 min() {
        return new Vector2(posX, posY);
    }
    
    public Vector2 max() {
        return new Vector2(posX + w, posY + h);
    }

    /**
     * @return The size of the region
     */
    public Vector2 size() {
        return new Vector2(w, h);
    }

    public float maxX() {
        return posX + w;
    }

    public float minX() {
        return posX;
    }


    public float maxY() {
        return posY + h;
    }

    public float minY() {
        return posY;
    }

    public float width() {
        return w;
    }

    public float height() {
        return h;
    }

    /**
     * @return The area of the Rect2f - width * height
     */
    public float area() {
        return w * h;
    }

    /**
     * @param other
     * @return The Rect2f that is encompassed by both this and other. If they
     *         do not overlap then the Rect2i.EMPTY is returned
     */
    public Rect2f intersect(Rect2f other) {
        float minX = Math.max(posX, other.posX);
        float maxX = Math.min(maxX(), other.maxX());
        float minY = Math.max(posY, other.posY);
        float maxY = Math.min(maxY(), other.maxY());
        return createFromMinAndMax(minX, minY, maxX, maxY);
    }

    /**
     * @param pos
     * @return Whether this Rect2i includes pos
     */
    public boolean contains(Vector2 pos) {
        return contains(pos.x, pos.y);
    }

    public boolean contains(float x, float y) {
        return !isEmpty() && (x >= posX) && (y >= posY) && (x <= posX + w) && (y <= posY + h);
    }

    public boolean encompasses(Rect2f other) {
        return !isEmpty() && !other.isEmpty() && other.posX >= posX && other.posY >= posY && other.posX + other.w <= posX + w && other.posY + other.h <= posY + h;
    }

    public boolean overlaps(Rect2f other) {
        return !isEmpty() && !other.isEmpty() && other.posX < posX + w && other.posX + other.w > posX && other.posY < posY + h && other.posY + other.h > posY;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Rect2f) {
            Rect2f other = (Rect2f) obj;
            return other.posX == posX && other.posY == posY && other.w == w && other.h == h;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY, w, h);
    }

    @Override
    public String toString() {
        return String.format("(x=%f y=%f w=%f h=%f)", posX, posY, w, h);
    }
    
    public Rect2f translate(float x, float y) {
        return new Rect2f(this.posX + x, this.posY + y, this.w, this.h);
    }
}
