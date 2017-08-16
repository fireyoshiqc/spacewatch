/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.weapons;

import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.modules.Module;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Classe abstraite qui englobe tous les types d'arme.
 * @author 1331037
 */
public abstract class Weapon extends Module {

    /**
     * Munitions de l'arme.
     */
    protected int ammunition;

    /**
     * Munitions maximales de l'arme.
     */
    protected int maxAmmo;

    /**
     * Vitesse de tir de l'arme.
     */
    protected float attackSpeed;

    /**
     * Puissance de l'arme.
     */
    protected int damage;

    /**
     * Position de tir de l'arme en X.
     */
    protected int emitX;

    /**
     * Position de tir de l'arme en Y.
     */
    protected int emitY;

    /**
     * Tire l'arme (méthode différente selon le type d'arme).
     *
     * @param delta Le ratio sur 60 IPS.
     * @param prs Le système de projectiles.
     * @param psy Le système de particules.
     */
    public abstract void fire(float delta, ProjectileSystem prs, ParticleSystem psy);

}
