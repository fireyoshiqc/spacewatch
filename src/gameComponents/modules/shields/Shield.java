/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.shields;

import gameComponents.modules.Module;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Bouclier électromagnétique qui défend contre les projectiles ennemis et les collisions avec les autres vaisseaux.
 * @author 1331037
 */
public class Shield extends Module {

    /**
     * Le type de bouclier.
     */
    public enum ShieldType {

        /**
         * Bouclier de base.
         */
        WC1

    }

    /**
     * Le type de bouclier.
     */
    protected ShieldType type;

    /**
     * La valeur maximale du bouclier.
     */
    protected float maxBarrier;

    /**
     * La valeur actuelle du bouclier.
     */
    protected float currentBarrier;

    /**
     * Le délai de rechargement du bouclier.
     */
    protected float delay;

    /**
     * La vitesse de régénération du bouclier.
     */
    protected float regenRate;

    /**
     * Le compteur pour la régénération.
     */
    protected int regenCounter = 0;

    /**
     * Le facteur d'absorption du bouclier.
     */
    protected float absorption;

    /**
     * Crée un bouclier.
     *
     * @param type Type de bouclier.
     * @throws SlickException
     */
    public Shield(ShieldType type) throws SlickException {
        sprites = new SpriteSheet("Shield_SpriteSheet.png", 256, 256);

        this.type = type;
        switch (type) {
            case WC1:

                this.mass = 50;
                this.maxBarrier = 1000;
                this.currentBarrier = maxBarrier;
                this.delay = 2000;
                this.regenRate = 5;
                this.absorption = 1f;

                sprite = sprites.getSubImage(0, 0);

                break;

        }

    }

    /**
     * Régénère le bouclier.
     *
     * @param delta Le ratio sur 60 IPS.
     */
    public void regenerate(float delta) {
        sprite.setAlpha(currentBarrier / maxBarrier);
        if (sprite.getAlpha() < 0.1f) {
            sprite.setAlpha(0.1f);
        }
        int realDelta = (int) Math.ceil(delta * (1000 / 60));
        if (currentBarrier < maxBarrier) {

            regenCounter += realDelta;

            if (regenCounter >= delay) {
                currentBarrier += (regenRate * delta);
            }
            if (currentBarrier >= maxBarrier) {
                currentBarrier = maxBarrier;
                regenCounter = 0;
            }
        }
        if (currentBarrier < 0) {
            currentBarrier = 0;
            regenCounter = 0;
        }
    }

    /**
     *
     * @return La valeur du bouclier.
     */
    public float getCurrentBarrier() {
        return currentBarrier;
    }

    /**
     *
     * @return La valeur maximale du bouclier.
     */
    public float getMaxBarrier() {
        return maxBarrier;
    }

    /**
     *
     * @return L'absorption du bouclier.
     */
    public float getAbsorption() {
        return absorption;
    }

    /**
     *
     * @param currentBarrier La nouvelle valeur de bouclier.
     */
    public void setCurrentBarrier(float currentBarrier) {
        this.currentBarrier = currentBarrier;
    }

    /**
     * Remet le compteur de régénération à zéro après un coup.
     */
    public void resetHit() {

        regenCounter = 0;
    }

}
