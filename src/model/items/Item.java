/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.items;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * Classe abstraite qui englobe tous les types d'items.
 * @author 1373360
 */
public abstract class Item {

    /**
     * Masse de l'item.
     */
    protected float mass;

    /**
     * Image utilisée par l'item.
     */
    protected Image sprite;

    /**
     * Position en X de l'item lâché.
     */
    protected double x;

    /**
     * Position en Y de l'item lâché.
     */
    protected double y;

    /**
     * La chance que l'item soit lâché.
     */
    protected float chance;

    /**
     *
     * @return La masse de l'item.
     */
    public float getMass() {
        return mass;
    }

    /**
     * Dessine l'item.
     */
    public void draw() {

        sprite.getScaledCopy(3f).draw((float) x, (float) y);
    }

    /**
     *
     * @param chance La chance de drop à donner à l'item.
     */
    public void setChance(float chance) {
        this.chance = chance;
    }

    /**
     *
     * @return La chance de drop de l'item.
     */
    public float getChance() {
        return chance;
    }

    /**
     *
     * @return Le rectangle correspondant au périmètre de l'image;
     */
    public Rectangle getPos() {
        return new Rectangle((int) x, (int) y, sprite.getScaledCopy(3f).getWidth(), sprite.getScaledCopy(3f).getHeight());
    }

    /**
     *
     * @return La position en X de l'item.
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return La position en Y de l'item.
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @param x Le nouveau X de l'item.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     *
     * @param y Le nouveau Y de l'item.
     */
    public void setY(double y) {
        this.y = y;
    }

}
