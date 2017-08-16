/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities.projectiles;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/**
 * Projectile de base tiré par les fusils.
 * @author 1331037
 */
public class Bullet extends Projectile {

    /**
     * Crée une nouvelle balle.
     * @param x La position en X.
     * @param y La position en Y.
     * @param mass La masse.
     * @param velocity La vitesse.
     * @param acceleration L'accélération.
     * @param angularSpeed La vitesse angulaire.
     * @param sprite L'image à utiliser pour la balle.
     * @throws SlickException
     */
    public Bullet(double x, double y, double mass, Vector2f velocity, Vector2f acceleration, double angularSpeed, Image sprite) throws SlickException {
        super(x, y, mass, velocity, acceleration, angularSpeed);
        this.sprite = sprite;
        this.collisionRadius = sprite.getWidth()/2;
        colCenterX = (float) (x + centerX);
        colCenterY = (float) (y + centerY);

    }

}
