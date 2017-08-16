/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities;

import gameComponents.entities.projectiles.ProjectileSystem;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Classe abstraite représentant les objets du jeu affectés par les lois de la physique.
 * @author 1331037
 */
public abstract class PhysicsObject {

    /**
     * La position en X de l'objet.
     */
    protected double x;

    /**
     * La position en Y de l'objet.
     */
    protected double y;

    /**
     * La masse de l'objet.
     */
    protected double mass;

    /**
     * Le vecteur de vitesse de l'objet.
     */
    protected Vector2f velocity;

    /**
     * L'accélération de l'objet.
     */
    protected Vector2f acceleration;

    /**
     * La vitesse rotationnelle de l'objet.
     */
    protected double angularSpeed;

    /**
     * L'angle de l'objet.
     */
    protected double angle = 0;

    /**
     * La feuille d'images de l'objet.
     */
    protected SpriteSheet images;

    /**
     * L'image de l'objet.
     */
    protected Image sprite;

    /**
     * Le centre de l'objet en X.
     */
    protected float centerX;

    /**
     * Le centre de l'objet en Y.
     */
    protected float centerY;

    /**
     * Le centre de collision de l'objet en X.
     */
    protected float colCenterX;

    /**
     * Le centre de collision de l'objet en Y.
     */
    protected float colCenterY;

    /**
     * Le rayon de collision circulaire de l'objet.
     */
    protected float collisionRadius;

    /**
     * L'état de destruction de l'objet.
     */
    protected boolean isDestroyed = false;

    /**
     * Constructeur à paramètres abstrait. Prend une position initiale, une vitesse, une
     * accélération et une vitesse rotationnelle.
     *
     * @param x La position en X.
     * @param y La position en Y.
     * @param mass La masse de l'objet.
     * @param velocity Le vecteur vitesse de l'objet.
     * @param acceleration Le vecteur accélération de l'objet.
     * @param angularSpeed La vitesse rotationnelle de l'objet.
     */
    public PhysicsObject(double x, double y, double mass, Vector2f velocity, Vector2f acceleration, double angularSpeed) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.angularSpeed = angularSpeed;

    }

    /**
     * Mise à jour de l'objet.
     *
     * @param force La force à appliquer à l'objet, en Newtons.
     * @param angularSpeed La vitesse angulaire à donne à l'objet.
     * @param delta Le ratio d'actualisation du jeu sur 60 IPS.
     * @param psy Le système de particules.
     * @param prs Le système de projectiles.
     */
    public void update(float force, float angularSpeed, float delta, ParticleSystem psy, ProjectileSystem prs) {
        float accTot;
        float accX;
        float accY;
        this.angularSpeed = angularSpeed;
        angle += (angularSpeed * delta) % 360;
        accTot = (float) (force / mass);
        accX = (float) (accTot * Math.cos(Math.toRadians(angle - 90)) * delta);
        accY = (float) (accTot * Math.sin(Math.toRadians(angle - 90)) * delta);

        acceleration.set(accX, accY);

        velocity.add(acceleration);

        x += velocity.getX() * delta;
        y += velocity.getY() * delta;

        colCenterX = (float) (x + centerX);
        colCenterY = (float) (y + centerY);

    }

    /**
     *
     * @return La vitesse de l'objet.
     */
    public Vector2f getVelocity() {
        return velocity;
    }

    /**
     * Dessin de l'objet.
     */
    public void draw() {
        sprite.setRotation((float) angle);
        sprite.draw((float) x, (float) y);

    }

    /**
     *
     * @return L'angle de l'objet.
     */
    public double getAngle() {
        return angle;
    }

    /**
     *
     * @param angle L'angle à donner à l'objet.
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     *
     * @return La position en X de l'objet.
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return La position en Y de l'objet.
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @return La hauteur de l'image de l'objet, en pixels.
     */
    public int getHeight() {
        return sprite.getHeight();
    }

    /**
     *
     * @return La largeur de l'image de l'objet, en pixels.
     */
    public int getWidth() {
        return sprite.getWidth();
    }

    /**
     *
     * @return La masse de l'objet, en kg.
     */
    public double getMass() {
        return mass;
    }

    /**
     * Méthode de mise à jour manuelle de la position.
     *
     * @param x La nouvelle position en X.
     * @param y La nouvelle position en Y.
     */
    public void manualUpdate(float x, float y) {
        this.x = x;
        this.y = y;

    }

    /**
     *
     * @return L'image de l'objet.
     */
    public Image getSprite() {
        return sprite;
    }

    /**
     *
     * @return L'accélération de l'objet.
     */
    public Vector2f getAcceleration() {
        return acceleration;
    }

    /**
     *
     * @param acceleration L'accélération à donner à l'objet.
     */
    public void setAcceleration(Vector2f acceleration) {
        this.acceleration = acceleration;
    }

    /**
     *
     * @return Le centre en X de l'objet.
     */
    public float getCenterX() {
        return centerX;
    }

    /**
     *
     * @return Le centre en Y de l'objet.
     */
    public float getCenterY() {
        return centerY;
    }

    /**
     *
     * @return Le rayon de collision de l'objet.
     */
    public float getCollisionRadius() {
        return collisionRadius;
    }

    /**
     *
     * @param velocity La vitesse à donner à l'objet.
     */
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    /**
     *
     * @return Le centre de collision en X de l'objet.
     */
    public float getColCenterX() {
        return colCenterX;
    }

    /**
     *
     * @return Le centre de collision en Y de l'objet.
     */
    public float getColCenterY() {
        return colCenterY;
    }

    /**
     *
     * @param sprite L'image à donner à l'objet.
     */
    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    /**
     *
     * @return L'état de destruction de l'objet
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }

    /**
     *
     * @param angularSpeed La vitesse angulaire à donner à l'objet.
     */
    public void setAngularSpeed(double angularSpeed) {
        this.angularSpeed = angularSpeed;
    }

    /**
     *
     * @param mass La masse à donner à l'objet.
     */
    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     *
     * @return Un rectangle correspondant au périmètre de l'image de l'objet.
     */
    public Rectangle getPos() {
        return new Rectangle((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
    }

}
