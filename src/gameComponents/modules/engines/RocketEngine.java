/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.engines;

import gameComponents.entities.PhysicsObject;
import model.particleSystem.RocketEmitter;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Moteur puissant à carburant liquide.
 * @author 1331037
 */
public class RocketEngine extends Engine {

    /**
     * Type de moteur-fusée.
     */
    public enum EngineType {

        /**
         * Moteur pour missile faible.
         */
        MR001,
        /**
         * Moteur pour missile fort.
         */
        MR002,
        /**
         * Moteur pour vaisseau de base.
         */
        PR005;
    }

    /**
     * Type de moteur.
     */
    private EngineType type;

    /**
     * Émetteur de particules du moteur.
     */
    private RocketEmitter emitter;

    /**
     * Position d'émission en X.
     */
    private int emitX;

    /**
     * Position d'émission en Y.
     */
    private int emitY;

    /**
     * Crée un moteur-fusée.
     *
     * @param type Type de moteur.
     * @throws SlickException
     */
    public RocketEngine(EngineType type) throws SlickException {
        sprites = new SpriteSheet("Engine_SpriteSheet.png", 128, 128);

        this.type = type;
        switch (type) {
            case MR001:
                this.maxFuel = 0.1f;
                this.leftFuel = 0.1f;
                this.mass = 50 + 0.8f * maxFuel;

                this.force = 50;

                sprite = new Image("Missile_Engine.png");
                emitX = 0;
                emitY = 0;
                emitter = new RocketEmitter(emitX, emitY, 1);
                break;
            case MR002:
                this.maxFuel = 5;
                this.leftFuel = 5;
                this.mass = 100 + 0.8f * maxFuel;

                this.force = 150;

                sprite = new Image("Missile_Engine.png");
                emitX = 0;
                emitY = 0;
                emitter = new RocketEmitter(emitX, emitY, 2);
                break;
            case PR005:
                this.maxFuel = 5000;
                this.leftFuel = 5000;
                this.mass = 1000 + 0.8f * maxFuel;

                this.force = 10000;

                sprite = sprites.getSubImage(0, 0);
                emitX = 64;
                emitY = 120;
                emitter = new RocketEmitter(emitX, emitY, 100);
                break;

        }

    }

    /**
     * Mise à jour. Méthode complexe qui calcule la poussée du moteur.
     *
     * @param psy Le système de particules.
     * @param delta Le ratio sur 60 IPS.
     * @param angle L'angle du moteur.
     * @param thrustersOn L'état du moteur (allumé ou pas).
     * @param ship Le vaisseau sur lequel est installé le moteur.
     */
    public void update(ParticleSystem psy, float delta, double angle, int thrustersOn, PhysicsObject ship) {

        if (emitter.isEnabled() && emitter != null) {

            emitter.update(psy, (int) (delta * (1000 / 60)));
            emitter.setAngle(Math.toRadians(sprite.getRotation() + 90));
            Vector2f relVect = new Vector2f((float) relX + emitX - ship.getSprite().getCenterOfRotationX(), (float) relY + emitY - ship.getSprite().getCenterOfRotationY());

            relVect.setTheta(relVect.getTheta() + angle);

            emitter.setPosition((int) Math.floor(ship.getX() + ship.getSprite().getCenterOfRotationX() + relVect.getX()), (int) Math.floor(ship.getY() + ship.getSprite().getCenterOfRotationY() + relVect.getY()));

//                   
            if (leftFuel > 0) {
                if (thrustersOn == 1) {
                    force = Math.abs(force);

                } else if (thrustersOn == -1) {
                    force = -Math.abs(force);

                }

                if (thrustersOn != 0) {
                    consumeFuel((Math.abs(force) / 100000) * delta);
                    ship.setMass(ship.getMass() - 0.8 * (Math.abs(force) / 100000) * delta);

                    emitter.setThrustOn(true);

                } else {
                    consumeFuel((Math.abs(force) / 100000) * delta / 10);
                    ship.setMass(ship.getMass() - 0.8 * (Math.abs(force) / 100000) * delta / 10);
                    emitter.setThrustOn(false);

                }
            } else {
                force = 0;
                emitter.setEnabled(false);
                psy.removeEmitter(emitter);
            }
        }

    }

    /**
     *
     * @return L'émetteur de particules.
     */
    public RocketEmitter getEmitter() {
        return emitter;
    }

    /**
     *
     * @return Le carburant restant.
     */
    public float getLeftFuel() {
        return leftFuel;
    }

    /**
     * Calcul de la consommation de carburant.
     *
     * @param consume Le carburant à consommer.
     */
    private void consumeFuel(float consume) {
        leftFuel -= consume;
    }

    /**
     *
     * @return La force du moteur.
     */
    public float getForce() {
        return force;
    }

}
