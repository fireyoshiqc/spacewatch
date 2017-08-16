/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities.projectiles;

import gameComponents.entities.ships.Ship;
import model.particleSystem.ExplosionEmitter;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Bombe explosant après un certain délai et causant de puissants dégâts aux ennemis.
 * @author Jocelyn Bouchard
 */
public class Bomb extends Projectile {

    private int time;
    private int maxTime;
    private boolean hasPlayed = false;

    /**
     * Crée une nouvelle bombe.
     *
     * @param x Position initiale en X.
     * @param y Position initiale en Y.
     * @param mass Masse.
     * @param velocity Vitesse.
     * @param acceleration Accélération.
     * @param angularSpeed Vitesse angulaire.
     * @param sprite Image à utiliser pour la bombe.
     * @throws SlickException
     */
    public Bomb(double x, double y, double mass, Vector2f velocity, Vector2f acceleration, double angularSpeed, Image sprite) throws SlickException {
        super(x, y, mass, velocity, acceleration, angularSpeed);
        this.sprite = sprite;
        this.collisionRadius = sprite.getWidth() / 2;
        colCenterX = (float) (x + centerX);
        colCenterY = (float) (y + centerY);
        maxTime = 3000;
        this.time = maxTime;

        isDestroyed = false;

    }

    /**
     * Mise à jour.
     *
     * @param delta Ratio sur 60 IPS.
     * @param psy Système de particules.
     */
    public void update(float delta, ParticleSystem psy) {
        colCenterX = (float) (x + centerX);
        colCenterY = (float) (y + centerY);

        int realDelta = (int) Math.ceil(delta * (1000 / 60));

        if (time > 0 && time <= maxTime) {
            time = time - realDelta;

        } else {
            time = 0;
            isDestroyed = true;
            ExplosionEmitter explosionemit = new ExplosionEmitter((int) colCenterX, (int) colCenterY, 200);
            psy.addEmitter(explosionemit);

        }

    }

    /**
     * Méthode appelée lorsque la bombe entre en collision avec un vaisseau.
     *
     * @param psy Système de particules.
     */
    public void collision(ParticleSystem psy) {
        time = 0;
        isDestroyed = true;
        ExplosionEmitter explosionemit = new ExplosionEmitter((int) colCenterX, (int) colCenterY, 200);
        psy.addEmitter(explosionemit);

    }

    /**
     * Méthode de calcul des dommages causés par l'explosion de la bombe.
     *
     * @param vaisseau Le vaisseau avec lequel vérifier la distance de
     * l'épicentre.
     * @return Les dommages faits au vaisseau.
     */
    public float explode(Ship vaisseau) {
        Vector2f distance = new Vector2f(vaisseau.getColCenterX() - this.getColCenterX(), vaisseau.getColCenterY() - this.getColCenterY());
        double splash;
        float damage;
        if (distance.length() >= 0 && distance.length() <= 750) {
            splash = 1;

        } else if (distance.length() > 750 && distance.length() <= 1500) {
            splash = 0.5;
//            return 500;
        } else if (distance.length() > 1500 && distance.length() <= 2250) {
            splash = 0.2;
//            return 200;
        } else if (distance.length() > 2250 && distance.length() <= 3000) {
            splash = 0.1;
//            return 100;
        } else if (distance.length() > 3000 && distance.length() <= 4500) {
            splash = 0.05;
//            return 50;
        } else if (distance.length() > 45000 && distance.length() <= 6000) {
            splash = 0.02;
//            return 25;
        } else if (distance.length() > 6000 && distance.length() <= 8000) {
            splash = 0.01;
//            return 10;
        } else if (distance.length() > 8000 && distance.length() <= 10000) {
            splash = 0.005;
//            return 5;
        } else {
            splash = 0;
//            return 0;
        }
        damage = (float) splash * this.getPuissance();
        return damage;
    }

    /**
     * Méthode qui détermine si le son de l'horloge doit être joué.
     * @return Jouer le son si true.
     */
    public boolean playClock() {
        boolean play = false;

        for (int i = 1; i <= 5; i++) {
            if ((this.time >= ((i * 500) - 10)) && (this.time <= ((i * 500) + 10))) {
                play = true;
            }
        }

        return play;
    }

    /**
     * 
     * @param hasPlayed Le nouvel état pour le son de la bombe.
     */
    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    /**
     *
     * @return L'état du son de la bombe.
     */
    public boolean isHasPlayed() {
        return hasPlayed;
    }

    /**
     *
     * @return Le temps restant à la bombe.
     */
    public int getTime() {
        return time;
    }

}
