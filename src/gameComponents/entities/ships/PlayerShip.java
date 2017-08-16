/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities.ships;

import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.modules.Module;
import gameComponents.modules.engines.IonEngine;
import gameComponents.modules.engines.RocketEngine;
import model.items.ItemSystem;
import model.particleSystem.ExplosionEmitter;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Vaisseau du joueur.
 *
 * @author 1331037
 */
public class PlayerShip extends Ship {

    private Sound scream;
    private int sc;

    /**
     * Crée un nouveau vaisseau pour le joueur.
     *
     * @param x La position en X.
     * @param y La position en Y.
     * @param mass La masse de l'objet.
     * @param velocity Le vecteur vitesse de l'objet.
     * @param acceleration Le vecteur accélération de l'objet.
     * @param angularSpeed La vitesse rotationnelle de l'objet.
     * @param type Le type de vaisseau à créer.
     * @param team La faction dont fait partie le joueur.
     * @param skin La sous-image à utiliser pour le joueur (de 0 à 3).
     * @param psy Le système de particules.
     * @param its Le système d'objets.
     * @throws SlickException
     */
    public PlayerShip(double x, double y, double mass, Vector2f velocity, Vector2f acceleration, double angularSpeed, ShipType type, Team team, int skin, ParticleSystem psy, ItemSystem its) throws SlickException {
        super(x, y, mass, velocity, acceleration, angularSpeed, type, team, skin, psy, its);
        scream = new Sound("WilhelmScream.ogg");
        sprite.setRotation((float) velocity.getTheta());
    }

    /**
     * Détruit le vaisseau du joueur.
     *
     * @param psy Le système de particules.
     * @param prs Le système de projectiles.
     */
    @Override
    public void destroy(ParticleSystem psy, ProjectileSystem prs) {
        if (this.getRadar() != null) {
            this.getRadar().setScanEnabled(false);
        }
        this.currentHp = 0;
        this.currentShield = 0;
        this.fuel = 0;
        this.bullets = 0;
        this.velocity.sub(velocity);
        stopLaserFire(prs);
        stopAcidFire();
        for (Module module : modulesList) {
            if (module instanceof RocketEngine) {
                RocketEngine re = (RocketEngine) module;
                re.getEmitter().setEnabled(false);
                psy.removeEmitter(re.getEmitter());
            }
            if (module instanceof IonEngine) {
                IonEngine ie = (IonEngine) module;
                ie.getEmitter().setEnabled(false);
                psy.removeEmitter(ie.getEmitter());
            }
            module = null;
        }
        this.modulesList.clear();
        ExplosionEmitter explosionemit = new ExplosionEmitter((int) colCenterX, (int) colCenterY, 200);
        psy.addEmitter(explosionemit);
        explosion.play(1f, 0.2f);
        switch ((int) (Math.random() * 10)) {
            case 3:
                scream.play();
        }

    }

    /**
     * Détermine le nombre de Space Credits (argent) du joueur.
     *
     * @param sc Le nombre à donner.
     */
    public void setSc(int sc) {
        this.sc = this.sc + sc;
    }

    /**
     *
     * @return Le nombre de Space Credits du joueur.
     */
    public int getSc() {
        return sc;
    }

}
