/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities.projectiles;

import gameComponents.modules.engines.RocketEngine;
import gameComponents.modules.engines.RocketEngine.EngineType;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Projectile auto-propulsé qui explose pour de puissants dégâts sur les ennemis.
 * @author Félix
 */
public class Missile extends Projectile {

    RocketEngine propellant;

    /**
     * Crée un nouveau missile auto-propulsé.
     *
     * @param x La position en X.
     * @param y La position en Y.
     * @param mass La masse.
     * @param velocity La vitesse.
     * @param acceleration L'accélération.
     * @param angularSpeed La vitesse angulaire.
     * @param sprite L'image à utiliser pour la balle.
     * @param engine Le moteur-fusée à utiliser pour le missile.
     * @param psy Le système de particules.
     * @throws SlickException
     */
    public Missile(double x, double y, double mass, Vector2f velocity, Vector2f acceleration, double angularSpeed, Image sprite, EngineType engine, ParticleSystem psy) throws SlickException {
        super(x, y, mass, velocity, acceleration, angularSpeed);
        this.sprite = sprite;
        sprite.setCenterOfRotation(sprite.getWidth() / 2, sprite.getHeight() / 2);
        centerX = sprite.getWidth() / 2;
        centerY = sprite.getHeight() / 2;
        colCenterX = (float) (x + centerX);
        colCenterY = (float) (y + centerY);
        propellant = new RocketEngine(engine);
        propellant.setManualPosition(sprite.getCenterOfRotationX(), sprite.getHeight());
        propellant.getEmitter().setEnabled(false);

    }

    /**
     *
     * @return Le moteur-fusée associé au missile.
     */
    public RocketEngine getPropellant() {
        return propellant;
    }

}
