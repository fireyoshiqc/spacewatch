/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.weapons;

import gameComponents.entities.PhysicsObject;
import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.entities.ships.PlayerShip;
import gameComponents.entities.ships.Ship;
import model.particleSystem.AcidEmitter;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Arme qui lance de l'acide pour faire fondre les vaisseaux ennemis.
 * @author Félix
 */
public class AcidWeapon extends Weapon {

    /**
     * Type d'arme.
     */
    public enum WeaponType {

        /**
         * Arme à l'acide de base.
         */
        PX1;
    }

    private WeaponType type;

    private Ship ship;
    private Sound firingSound;

    private Vector2f relVect;
    private AcidEmitter emitter;
    private boolean firing = false;
    private ProjectileSystem prs;

    /**
     * Crée une arme à l'acide.
     *
     * @param type Le type d'arme.
     * @param ship Le vaisseau associé à l'arme.
     * @throws SlickException
     */
    public AcidWeapon(WeaponType type, Ship ship) throws SlickException {
        sprites = new SpriteSheet("Weapon_SpriteSheet.png", 64, 64);
        firingSound = new Sound("Acid_Fire.ogg");

        this.type = type;
        this.ship = ship;

        emitX = 32;
        emitY = 10;
        relVect = new Vector2f((float) relX + emitX - ship.getSprite().getCenterOfRotationX(), (float) relY + emitY - ship.getSprite().getCenterOfRotationY());

        switch (type) {
            case PX1:

                this.ammunition = 1000;
                this.maxAmmo = ammunition;
                this.mass = 100;

                this.damage = 5;

                sprite = sprites.getSubImage(0, 4);
                emitter = new AcidEmitter(emitX, emitX, 100, this.prs, this.ship, this.damage);
                break;

        }
    }

    /**
     * Mise à jour. Met à jour les particules d'acide déjà tirées.
     *
     * @param psy Le système de particules.
     * @param delta Le ratio sur 60 IPS.
     * @param angle L'angle de l'arme.
     * @param ship Le vaisseau associé à l'arme.
     */
    public void update(ParticleSystem psy, float delta, double angle, PhysicsObject ship) {

        emitter.setFiring(firing);
        if (ammunition <= 0) {
            emitter.setFiring(false);
        }
        if (emitter.isEnabled()) {

            emitter.update(psy, (int) (delta * (1000 / 60)));

            emitter.setAngle(Math.toRadians(sprite.getRotation() - 90));
            relVect = new Vector2f((float) relX + emitX - ship.getSprite().getCenterOfRotationX(), (float) relY + emitY - ship.getSprite().getCenterOfRotationY());

            relVect.setTheta(relVect.getTheta() + angle);

            emitter.setPosition((int) Math.floor(ship.getX() + ship.getSprite().getCenterOfRotationX() + relVect.getX()), (int) Math.floor(ship.getY() + ship.getSprite().getCenterOfRotationY() + relVect.getY()));

        }
    }

    @Override
    public void fire(float delta, ProjectileSystem prs, ParticleSystem psy) {
        if (ammunition > 0) {
            ammunition -= delta;
            if (ship instanceof PlayerShip) {
                if (!firingSound.playing()) {
                    firingSound.play(1f, 0.2f);
                }
            }
        }
        if (!firing) {
            firing = true;

        }

    }

    /**
     * Arrête le tir d'acide.
     */
    public void stopFire() {
        if (firing) {
            firing = false;
        }
    }

    /**
     *
     * @return L'émetteur de particules d'acide.
     */
    public ParticleEmitter getEmitter() {
        return emitter;
    }

    /**
     *
     * @return Le système de projectiles.
     */
    public ProjectileSystem getPrs() {
        return prs;
    }

    /**
     *
     * @param prs Le nouveau système de projectiles.
     */
    public void setPrs(ProjectileSystem prs) {
        this.prs = prs;
        emitter.setPrs(this.prs);
    }

    /**
     *
     * @return Les munitions restantes.
     */
    public int getAmmunition() {
        return this.ammunition;
    }

}
