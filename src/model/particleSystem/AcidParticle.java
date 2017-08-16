/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.particleSystem;

import gameComponents.entities.ships.Ship;
import org.newdawn.slick.particles.Particle;

/**
 * Des particules qu'on doit éviter à tout prix. Ajoute des variables à la
 * classe Particle de Slick sans toutefois en hériter.
 *
 * @author Félix
 */
public class AcidParticle {

    private Ship provenance;
    private float damage;
    private Particle particle;

    /**
     * Crée une nouvelle particule d'acide.
     *
     * @param particle
     */
    public AcidParticle(Particle particle) {
        this.particle = particle;

    }

    /**
     * Détermine le dommage de la particule.
     *
     * @param damage Le dommage à lui donner.
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     *
     * @return Le dommage de la particule.
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Détermine la provenance de la particule.
     *
     * @param provenance Le vaisseau dont provient la particule.
     */
    public void setProvenance(Ship provenance) {
        this.provenance = provenance;
    }

    /**
     *
     * @return La provenance de la particule.
     */
    public Ship getProvenance() {
        return provenance;
    }

    /**
     *
     * @return La position X de la particule.
     */
    public float getX() {
        return particle.getX();
    }

    /**
     *
     * @return La position Y de la particule.
     */
    public float getY() {
        return particle.getY();
    }

    /**
     *
     * @return La vie restante de la particule.
     */
    public float getLife() {
        return particle.getLife();
    }

    /**
     *
     * @return La taille de la particule.
     */
    public float getSize() {
        return particle.getSize();
    }

}
