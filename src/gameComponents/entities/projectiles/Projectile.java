/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities.projectiles;

import gameComponents.entities.PhysicsObject;
import gameComponents.entities.ships.Ship;

import org.newdawn.slick.geom.Vector2f;

/**
 * Classe abstraite représentant tous les projectiles du jeu.
 * @author 1331037
 */
public abstract class Projectile extends PhysicsObject {

    /**
     * X initial du projectile.
     */
    protected double Xinit;

    /**
     * Y initial du projectile.
     */
    protected double Yinit;

    /**
     * Provenance du projectile.
     */
    protected Ship provenance;

    /**
     * La puissance du projectile.
     */
    protected int puissance;

    /**
     * Crée un projectile (abstrait).
     *
     * @param x La position en X.
     * @param y La position en Y.
     * @param mass La masse.
     * @param velocity La vitesse initiale.
     * @param acceleration L'accélération (habituellement 0).
     * @param angularSpeed La vitesse angulaire.
     */
    public Projectile(double x, double y, double mass, Vector2f velocity, Vector2f acceleration, double angularSpeed) {
        super(x, y, mass, velocity, acceleration, angularSpeed);

    }

    /**
     *
     * @param provenance Le vaisseau dont va provenir le projectile.
     */
    public void setProvenance(Ship provenance) {
        this.provenance = provenance;
    }

    /**
     *
     * @return Le vaisseau dont provient le projectile.
     */
    public Ship getProvenance() {
        return provenance;
    }

    /**
     *
     * @param Xinit Le nouveau X initial du projectile.
     */
    public void setXinit(double Xinit) {
        this.Xinit = Xinit;

    }

    /**
     *
     * @return Le X initial du projectile.
     */
    public double getXinit() {
        return Xinit;
    }

    /**
     *
     * @param Yinit Le nouveau Y initial du projectile.
     */
    public void setYinit(double Yinit) {
        this.Yinit = Yinit;

    }

    /**
     *
     * @return Le Y initial du projectile.
     */
    public double getYinit() {
        return Yinit;
    }

    /**
     *
     * @param puissance La nouvelle puissance à donner au projectile.
     */
    public void setPuissance(int puissance) {
        this.puissance = puissance;

    }

    /**
     *
     * @return La puissance du projectile.
     */
    public int getPuissance() {
        return puissance;
    }
}
