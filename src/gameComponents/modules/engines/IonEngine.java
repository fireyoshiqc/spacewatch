/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.engines;

import gameComponents.entities.PhysicsObject;
import model.particleSystem.IonEmitter;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Moteurs sans carburant. Écolos!
 * @author 1331037
 */
public class IonEngine extends Engine {

    /**
     * Type de moteur ionique.
     */
    public enum EngineType {

        /**
         * Moteur ionique de base.
         */
        ION75
    }

    /**
     * Type de moteur ionique.
     */
    private EngineType type;

    /**
     * Émetteur de particules du moteur.
     */
    private IonEmitter emitter;

    /**
     * Position d'émission en X.
     */
    private int emitX;

    /**
     * Position d'émission en Y.
     */
    private int emitY;

    /**
     * Crée un moteur ionique.
     *
     * @param type Le type de moteur.
     * @throws SlickException
     */
    public IonEngine(EngineType type) throws SlickException {
        sprites = new SpriteSheet("Engine_SpriteSheet.png", 128, 128);

        this.type = type;
        switch (type) {
            case ION75:
                this.maxFuel = 0;
                this.leftFuel = 0;
                this.mass = 2000;

                this.force = 7500;

                sprite = sprites.getSprite(0, 1);
                emitX = 64;
                emitY = 110;
                emitter = new IonEmitter(emitX, emitY, 100);
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
            if (thrustersOn == 1) {
                force = Math.abs(force);

            } else if (thrustersOn == -1) {
                force = -Math.abs(force);

            }

            if (thrustersOn != 0) {

                emitter.setThrustOn(true);

            } else {

                emitter.setThrustOn(false);

            }

        }

    }

    /**
     *
     * @return L'émetteur de particules.
     */
    public IonEmitter getEmitter() {
        return emitter;
    }

    /**
     *
     * @return La force du moteur.
     */
    public float getForce() {
        return force;
    }

}
