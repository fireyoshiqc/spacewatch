/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Classe abstraite représentant tous les modules pouvant être installés sur les vaisseaux.
 * @author 1331037
 */
public abstract class Module {

    /**
     * La masse du module.
     */
    protected float mass;

    /**
     * La feuille d'images à utiliser pour le module.
     */
    protected SpriteSheet sprites;

    /**
     * L'image à utiliser pour le module.
     */
    protected Image sprite;

    /**
     * Le X relatif du module par rapport au vaisseau.
     */
    protected float relX;

    /**
     * Le Y relatif du module par rapport au vaisseau.
     */
    protected float relY;

    /**
     * La position en X du module.
     */
    protected double x = 0;

    /**
     * La position en Y du module.
     */
    protected double y = 0;

    /**
     *
     */
    public Module() {
    }

    
    /**
     * Crée un module en position relative au vaisseau (classe abstraite).
     *
     * @param relX
     * @param relY
     */
    public Module(float relX, float relY) {
        this.relX = relX;
        this.relY = relY;
    }

    /**
     *
     * @return La masse du module.
     */
    public float getMass() {
        return mass;
    }

    /**
     * Dessin du module.
     *
     * @param posX La position en X du vaisseau.
     * @param posY La position en Y du vaisseau.
     */
    public void draw(float posX, float posY) {
        x = posX + relX;
        y = posY + relY;
        sprite.draw((float) x, (float) y);
    }

    /**
     *
     * @return L'image du module.
     */
    public Image getSprite() {
        return sprite;
    }

    /**
     *
     * @return La position relative en X du module.
     */
    public float getRelX() {
        return relX;
    }

    /**
     *
     * @return La position relative en Y du module.
     */
    public float getRelY() {
        return relY;
    }

    /**
     *
     * @return La position en X du module.
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return La position en Y du module.
     */
    public double getY() {
        return y;
    }

    /**
     * Définit manuellement la position relative du module sur le vaisseau.
     *
     * @param relX La position relative en X.
     * @param relY La position relative en Y.
     */
    public void setManualPosition(float relX, float relY) {
        this.relX = relX;
        this.relY = relY;
    }

    /**
     *
     * @param sprite La nouvelle image à utiliser pour le module.
     */
    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

}
