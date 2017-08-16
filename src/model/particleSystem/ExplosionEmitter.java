/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.particleSystem;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;
import view.states.OptionsMenu;

/**
 * CLASSE COPIÉE ET ÉDITÉE DE FIREEMITTER, UN TEST DE SLICK2D
 * @author Félix
 */
public class ExplosionEmitter implements ParticleEmitter {

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
    private int interval = 1;
    /**
     * Time til the next particle
     */
    private int timer = 0;
    /**
     * The size of the initial particles
     */
    private float size = 40;

    private boolean enabled = true;

    OptionsMenu particleOption;

    /**
     * Create a default fire effect at 0,0
     */
    public ExplosionEmitter() {
    }

    /**
     * Create a default fire effect at x,y
     *
     * @param x The x coordinate of the fire effect
     * @param y The y coordinate of the fire effect
     */
    public ExplosionEmitter(int x, int y) {
        this.x = x;
        this.y = y;

    }

    /**
     * Create a default fire effect at x,y
     *
     * @param x The x coordinate of the fire effect
     * @param y The y coordinate of the fire effect
     * @param size The size of the particle being pumped out
     */
    public ExplosionEmitter(int x, int y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;

    }

    /**
     * @see
     * org.newdawn.slick.particles.ParticleEmitter#update(org.newdawn.slick.particles.ParticleSystem,
     * int)
     */
    @Override
    public void update(ParticleSystem system, int delta) {

        if (timer == 0) {
            for (int i = 0; i < 25; i++) {

                Particle p = system.getNewParticle(this, (float) (10000 - (Math.random() * 5000)));

                p.setColor(1f, 1f, 1f, 1f);
                p.setPosition(x + (int) ((Math.random() - 0.5) * 300), y + (int) ((Math.random() - 0.5) * 300));

                float angle = (float) Math.toRadians(Math.random() * 360);
                float vx = (float) (Math.random() * 2 * Math.cos(angle));
                float vy = (float) (Math.random() * 2 * Math.sin(angle));
                p.setSize(size);
                //p.setSize((float) (size-((Math.sqrt(vx*vx+vy*vy)/(Math.sqrt(2)))*size*10)));

                p.setVelocity(vx, vy);

            }
        } else if (timer > 10000) {
            this.setEnabled(false);
            system.removeEmitter(this);
        }
        timer += delta;

    }

    /**
     * @see
     * org.newdawn.slick.particles.ParticleEmitter#updateParticle(org.newdawn.slick.particles.Particle,
     * int)
     */
    @Override
    public void updateParticle(Particle particle, int delta
    ) {

        particle.adjustSize((float) (-0.1f) * delta * (particle.getSize() / this.size));

        float c = 0.002f * delta;

        if (particle.getSize() < size / 1.2f) {
            particle.adjustColor(0, -c / 2, -c, -c / 4);
        }

//        if (particle.getColor().getRed() < 255) {
//            particle.adjustColor(4 * c, c / 2, 0, -c / 4);
//        } else {
//            particle.adjustColor(-c / 8, -c / 4, -c, -c / 4);
//        }
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
    public void setEnabled(boolean enabled
    ) {
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
    public boolean usePoints(ParticleSystem system
    ) {
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

}
