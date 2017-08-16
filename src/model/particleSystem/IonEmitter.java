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

/**
 * CLASSE COPIÉE ET ÉDITÉE DE FIREEMITTER, UN TEST DE SLICK2D
 * @author Félix
 */
public class IonEmitter implements ParticleEmitter {

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
    private int interval = 50;
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

    private Vector2f velocity = new Vector2f(0,0);

    private boolean thrustOn = false;

    /**
     * Create a default fire effect at 0,0
     */
    public IonEmitter() {
    }

    /**
     * Create a default fire effect at x,y
     *
     * @param x The x coordinate of the fire effect
     * @param y The y coordinate of the fire effect
     */
    public IonEmitter(int x, int y) {
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
    public IonEmitter(int x, int y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
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

            Particle p = system.getNewParticle(this, 1000);

            p.setColor(1f, 1f, 1f, 1f);
            p.setPosition(x, y);
            if (thrustOn) {
                p.setSize(size);
            } else {
                p.setSize(size / 5);
            }
            float vx = (float) (Math.cos(angle) * 0.2f);
            float vy = (float) (Math.sin(angle) * 0.2f);

            p.setVelocity(vx, vy);

        }
    }

    /**
     * @see
     * org.newdawn.slick.particles.ParticleEmitter#updateParticle(org.newdawn.slick.particles.Particle,
     * int)
     */
    @Override
    public void updateParticle(Particle particle, int delta) {
        if (particle.getLife() > 500) {
            particle.adjustSize(0.1f * delta);

        }
        else{
            particle.adjustSize(-0.1f*delta);
        }
        float c = 0.002f * delta;
        if (particle.getColor().getGreen() > 100) {
            particle.adjustColor(-c / 2, -c / 4, 0, -c / 8);
        }

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

//    public void replay() {
//		reset();
//		nextSpawn = 0;
//		leftToEmit = (int) emitCount.random();
//		timeout = (int) (length.random());
//	}
//
//	/**
//	 * Release all the particles held by this emitter
//	 */
//	public void reset() {
//	    completed = false; 
//		if (system != null) {
//			engine.releaseAll(this);
//		}
//	}
//
//	/**
//	 * Check if the replay has died out - used by the editor
//	 */
//	public void replayCheck() {
//		if (completed()) {
//			if (engine != null) {
//				if (engine.getParticleCount() == 0) {
//					replay();
//				}
//			}
//		}
//	}
    /**
     *
     * @param thrustOn
     */
    public void setThrustOn(boolean thrustOn) {
        this.thrustOn = thrustOn;
    }

}
