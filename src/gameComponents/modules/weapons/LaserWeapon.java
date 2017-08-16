/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.weapons;

import gameComponents.entities.projectiles.Laser;
import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.entities.ships.Ship;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Un puissant émetteur de rayons lasers destructifs.
 * @author 1331037
 */
public class LaserWeapon extends Weapon {

    /**
     * Type d'arme.
     */
    public enum WeaponType {

        /**
         * Laser simple.
         */
        ZRA,
        /**
         * Laser double.
         */
        ZRB,
        /**
         * Laser lourd simple.
         */
        ZRC,
        /**
         * Laser lourd double.
         */
        ZRD,
        /**
         * Artillerie simple.
         */
        ZRE,
        /**
         * Artillerie double.
         */
        ZRF,
        /**
         * Inexistant.
         */
        ZRG,
        /**
         * Inexistant.
         */
        ZRH,
        /**
         * Laser spécial du vaisseau-boss Skrelch.
         */
        ZRX
    }

    private WeaponType type;

    private Ship ship;
    private Sound firingSound;
    private Color color;
    private float range;
    private Ship target;
    private Vector2f laserTarget;
    private Vector2f relVect;
    private boolean laserFiring = false;
    private Laser laser;

    /**
     * Crée un canon laser.
     *
     * @param type Type d'arme.
     * @param ship Vaisseau associé.
     * @throws SlickException
     */
    public LaserWeapon(WeaponType type, Ship ship) throws SlickException {
        sprites = new SpriteSheet("Weapon_SpriteSheet.png", 64, 64);
        firingSound = new Sound("Laser_Fire.ogg");

        this.type = type;
        this.ship = ship;
        emitX = 32;
        emitY = 0;
        switch (type) {
            case ZRA:

                this.mass = 100;

                this.damage = 1;
                this.color = Color.red;
                this.range = 1000;
                sprite = sprites.getSubImage(0, 1);
                break;
            case ZRB:

                this.mass = 200;

                this.damage = 2;
                this.color = Color.red;
                this.range = 1000;
                sprite = sprites.getSubImage(1, 1);
                break;
            case ZRC:

                this.mass = 300;

                this.damage = 4;
                this.color = Color.cyan;
                this.range = 1200;
                sprite = sprites.getSubImage(2, 1);
                break;
            case ZRD:

                this.mass = 600;

                this.damage = 8;
                this.color = Color.cyan;
                this.range = 1200;
                sprite = sprites.getSubImage(3, 1);
                break;
            case ZRE:

                this.mass = 10000;

                this.damage = 10;
                this.color = Color.green;
                this.range = 1500;
                sprite = sprites.getSubImage(4, 1);
                break;
            case ZRF:

                this.mass = 2000;

                this.damage = 20;
                this.color = Color.green;
                this.range = 1500;
                sprite = sprites.getSubImage(5, 1);
                break;

            case ZRX:

                this.mass = 100000;
                this.damage = 1000;
                this.color = Color.magenta;
                this.range = 1500;
                sprite = sprites.getSubImage(7, 1);

        }
        this.maxAmmo = 0;
        this.ammunition = this.maxAmmo;
        laserTarget = new Vector2f(range + 1, 0);
    }

    @Override
    public void fire(float delta, ProjectileSystem prs, ParticleSystem psy) {

        relVect = new Vector2f((float) relX + emitX - ship.getSprite().getCenterOfRotationX(), (float) relY + emitY - ship.getSprite().getCenterOfRotationY());
        relVect.setTheta(relVect.getTheta() + ship.getAngle());

        Vector2f lockDistance;
        boolean targetFound = false;
        laserTarget = new Vector2f(range + 1, 0);

        for (Ship targetShip : prs.getNearbyShips()) {
            if (!ship.equals(targetShip) && targetShip.getTeam() != ship.getTeam()) {

                lockDistance = new Vector2f(targetShip.getColCenterX() - ship.getColCenterX(), targetShip.getColCenterY() - ship.getColCenterY());
                if (targetShip.getCurrentShield() > 0) {
                    Vector2f shieldRadius = new Vector2f(targetShip.getCollisionRadius(), 0);
                    shieldRadius.setTheta(lockDistance.getTheta());
                    lockDistance.sub(shieldRadius);
                }

                if (lockDistance.length() <= range && lockDistance.length() <= laserTarget.length()) {
                    laserTarget = lockDistance;
                    targetFound = true;
                    target = targetShip;

                }

            }
        }

        if (targetFound) {

            if (!laserFiring) {
                laserFiring = true;
                laser = new Laser((float) ship.getX() + ship.getSprite()
                        .getCenterOfRotationX() + relVect.getX(),
                        (float) ship.getY() + ship.getSprite().getCenterOfRotationY()
                        + relVect.getY(), ship.getColCenterX() + laserTarget.getX(),
                        ship.getColCenterY() + laserTarget.getY(), color, damage, ship, target);
                prs.addLaser(laser);
                if (!firingSound.playing()) {
                    firingSound.play(1f * (damage / 8), 0.2f);
                }

            } else {
                laser.update((float) ship.getX() + ship.getSprite()
                        .getCenterOfRotationX() + relVect.getX(),
                        (float) ship.getY() + ship.getSprite().getCenterOfRotationY()
                        + relVect.getY(), ship.getColCenterX() + laserTarget.getX(),
                        ship.getColCenterY() + laserTarget.getY(), target);
                if (!firingSound.playing()) {
                    firingSound.play(1f * (damage / 8), 0.2f);
                }

            }
        } else {

            stopFire(prs);

        }

    }

    /**
     * Arrêt du tir de laser.
     *
     * @param prs Système de projectiles.
     */
    public void stopFire(ProjectileSystem prs) {
        laserFiring = false;
        prs.removeLaser(laser);
        firingSound.stop();
    }

    /**
     *
     * @return La portée du canon laser.
     */
    public float getRange() {
        return range;
    }

}
