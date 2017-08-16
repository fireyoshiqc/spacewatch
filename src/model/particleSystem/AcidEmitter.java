/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.particleSystem;

import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.entities.ships.Ship;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * CLASSE COPIÉE ET ÉDITÉE DE FIREEMITTER, UN TEST DE SLICK2D
 * @author Félix
 */
public class AcidEmitter implements ParticleEmitter {

    /**
     * The x coordinate of the center of the fire effect
     */
    private int x;
    /**
     * The y coordinate of the center of the fire effect
     */
    private int y;

    /**
     * The particle emission rate
     */
    private int interval = 100;
    /**
     * Time til the next particle
     */
    private int timer;
    /**
     * The size of the initial particles
     */
    private float size = 40;

    private boolean enabled = true;

    private double angle;

    private Vector2f velocity;

    private boolean firing = false;
    ProjectileSystem prs;
    Ship ship;
    float damage;

    /**
     * Create a default fire effect at 0,0
     */
    public AcidEmitter() {
    }

    /**
     * Create a default fire effect at x,y
     *
     * @param x The x coordinate of the fire effect
     * @param y The y coordinate of the fire effect
     */
    public AcidEmitter(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a default fire effect at x,y
     *
     * @param x The x coordinate of the fire effect
     * @param y The y coordinate of the fire effect
     * @param size The size of the particle being pumped out
     * @param prs
     * @param ship
     * @param damage
     */
    public AcidEmitter(int x, int y, float size, ProjectileSystem prs, Ship ship, float damage) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.prs = prs;
        this.ship = ship;
        this.damage= damage;
        velocity = new Vector2f(0, 0);
    }

    /**
     * @see
     * org.newdawn.slick.particles.ParticleEmitter#update(org.newdawn.slick.particles.ParticleSystem,
     * int)
     */
    @Override
    public void update(ParticleSystem system, int delta) {
        timer -= delta;
        if (timer <= 0) {
            timer = interval;

            if (firing) {
                Particle p = system.getNewParticle(this, 3000);
                AcidParticle ap = new AcidParticle(p);
                ap.setProvenance(ship);
                ap.setDamage(damage);

                p.setColor(0, 1f, 0, 1f);
                p.setPosition(x, y);

                p.setSize(size);
                float vx = (float) ((Math.random() - 0.5) * 0.1f + Math.cos(angle) * 0.2f);
                float vy = (float) ((Math.random() - 0.5) * 0.1f + Math.sin(angle) * 0.2f);

                p.setVelocity(vx, vy, 1.0f);
                
                prs.addParticle(ap);
                

            }

        }
    }

    /**
     * @see
     * org.newdawn.slick.particles.ParticleEmitter#updateParticle(org.newdawn.slick.particles.Particle,
     * int)
     */
    @Override
    public void updateParticle(Particle particle, int delta) {
        
        
        if (particle.getLife() > 1500) {
            particle.adjustSize(0.2f * delta);
        } else {
            particle.adjustSize(-0.4f * delta);
        }
        

        float c = 0.002f * delta;

        particle.adjustColor(c / 4, 0, 0, -c / 8);

    }

    /**
     * @see org.newdawn.slick.particles.ParticleEmitter#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @see org.newdawn.slick.particles.ParticleEmitter#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @see org.newdawn.slick.particles.ParticleEmitter#completed()
     */
    @Override
    public boolean completed() {
        return false;
    }

    /**
     * @see org.newdawn.slick.particles.ParticleEmitter#useAdditive()
     */
    @Override
    public boolean useAdditive() {
        return false;
    }

    /**
     * @see org.newdawn.slick.particles.ParticleEmitter#getImage()
     */
    @Override
    public Image getImage() {
        return null;
    }

    /**
     * @see
     * org.newdawn.slick.particles.ParticleEmitter#usePoints(org.newdawn.slick.particles.ParticleSystem)
     */
    @Override
    public boolean usePoints(ParticleSystem system) {
        return false;
    }

    /**
     * @see org.newdawn.slick.particles.ParticleEmitter#isOriented()
     */
    @Override
    public boolean isOriented() {
        return false;
    }

    /**
     * @see org.newdawn.slick.particles.ParticleEmitter#wrapUp()
     */
    @Override
    public void wrapUp() {
    }

    /**
     * @see org.newdawn.slick.particles.ParticleEmitter#resetState()
     */
    @Override
    public void resetState() {
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param angle
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     *
     * @param velocity
     */
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    /**
     *
     * @param firing
     */
    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    /**
     *
     * @param prs
     */
    public void setPrs(ProjectileSystem prs) {
        this.prs = prs;
    }
    
    

}
