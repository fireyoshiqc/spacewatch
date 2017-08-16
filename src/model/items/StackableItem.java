/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.items;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Des items qu'on peut avoir en grande quantité!
 * @author 1373360
 */
public class StackableItem extends Item {

    private int quantity;
    private int value;

    /**
     * Type de ressource.
     */
    public enum Resources {

        /**
         * Fer.
         */
        IRON,
        /**
         * Carbone.
         */
        CARBON,
        /**
         * Cuivre.
         */
        COPPER,
        /**
         * Argent.
         */
        SILVER,
        /**
         * Or.
         */
        GOLD,
        /**
         * Platine.
         */
        PLATINUM,
        /**
         * Acide.
         */
        POISON,
        /**
         * Carburant.
         */
        FUEL,
        /**
         * Uranium.
         */
        URANIUM,
        /**
         * Balles.
         */
        BULLETS,
        /**
         * Missile.
         */
        MISSILE,
        /**
         * Bombe.
         */
        BOMB;
    }

    private Resources resource;
    private SpriteSheet sprites;

    /**
     * Crée un item empilable.
     *
     * @param resource Type de ressource.
     * @param x Position X.
     * @param y Position Y.
     * @param quantity Quantité de l'item.
     * @throws SlickException
     */
    public StackableItem(Resources resource, double x, double y, int quantity) throws SlickException {

        this.x = x;
        this.y = y;
        this.resource = resource;
        this.quantity = quantity;
        sprites = new SpriteSheet("Items_SpriteSheet.png", 64, 64);
        switch (resource) {

            case IRON:
                this.mass = 7.874f; // pour 1L
                sprite = sprites.getSprite(0, 0);
                this.value = 50;
                break;
            case CARBON:
                this.mass = 2.267f; // pour 1L
                sprite = sprites.getSprite(9, 0);
                this.value = 1;
                break;
            case COPPER:
                this.mass = 8.96f; // pour 1L
                sprite = sprites.getSprite(1, 0);
                this.value = 100;
                break;
            case SILVER:
                this.mass = 10.49f; // pour 1L
                sprite = sprites.getSprite(3, 0);
                this.value = 500;
                break;
            case GOLD:
                this.mass = 19.30f; // pour 1L
                sprite = sprites.getSprite(2, 0);
                this.value = 1000;
                break;
            case PLATINUM:
                this.mass = 21.45f; // pour 1L
                sprite = sprites.getSprite(4, 0);
                this.value = 5000;
                break;
            case POISON:
                this.mass = 0.2f; // pour 1L
                sprite = sprites.getSprite(7, 0);
                this.value = 10;
                break;
            case FUEL:
                this.mass = 0.8f; // pour 1L
                sprite = sprites.getSprite(6, 0);
                this.value = 5;
                break;
            case URANIUM:
                this.mass = 19.1f;
                sprite = sprites.getSprite(5, 0);
                this.value = 10000;
                break;
            case BULLETS:
                this.mass = 0f;
                sprite = sprites.getSprite(5, 0);
                this.value = 2;
                break;
            case MISSILE:
                this.mass = 0f;
                sprite = sprites.getSprite(5, 0);
                this.value = 100;
                break;
            case BOMB:
                this.mass = 0f;
                sprite = sprites.getSprite(5, 0);
                this.value = 250;
                break;
        }
        this.mass *= this.quantity;

    }

    /**
     *
     * @return Le type de ressource.
     */
    public Resources getResource() {
        return resource;
    }

    /**
     *
     * @param mass La masse à donner à l'item.
     */
    public void setMass(float mass) {
        this.mass = mass;
    }

    /**
     *
     * @return La quantité de l'item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity La quantité à donner à l'item.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
