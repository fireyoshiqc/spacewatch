/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.weapons;

import gameComponents.entities.projectiles.Missile;
import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.entities.ships.PlayerShip;
import gameComponents.entities.ships.Ship;
import gameComponents.modules.engines.RocketEngine.EngineType;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Lance des dizaines de fusées!
 * @author 1331037
 */
public class MissileWeapon extends Weapon {

    /**
     * Type d'arme.
     */
    public enum WeaponType {

        /**
         * Lanceur léger simple.
         */
        L311,
        /**
         * Lanceur léger double.
         */
        L321,
        /**
         * Lanceur lourd simple.
         */
        L331,
        /**
         * Lanceur lourd double.
         */
        L341,
        /**
         * Lanceur-pod.
         */
        L901
    }

    private WeaponType type;
    private int attackInterval;
    private int fireCounter = 0;
    private Vector2f relVect;
    private Ship ship;
    private Image missileSprite;
    private EngineType engine;
    private Sound firingSound;

    /**
     * Crée un lance-missiles.
     *
     * @param type Type d'arme.
     * @param ship Vaisseau associé.
     * @throws SlickException
     */
    public MissileWeapon(WeaponType type, Ship ship) throws SlickException {
        sprites = new SpriteSheet("Weapon_SpriteSheet.png", 64, 64);
        firingSound = new Sound("Missile_Fire.ogg");
        this.ship = ship;

        this.type = type;

        emitX = 26;
        emitY = 0;

        switch (type) {
            case L311:
                this.maxAmmo = 50;
                this.ammunition = this.maxAmmo;
                this.mass = 100;

                this.attackSpeed = 0.5f;
                this.damage = 500;
                sprite = sprites.getSubImage(0, 2);
                missileSprite = new Image("Small_Missile.png");
                engine = EngineType.MR001;
                break;
            case L321:
                this.maxAmmo = 50;
                this.ammunition = this.maxAmmo;
                this.mass = 200;

                this.attackSpeed = 1;
                this.damage = 500;
                sprite = sprites.getSubImage(1, 2);
                missileSprite = new Image("Small_Missile.png");
                engine = EngineType.MR001;
                break;
            case L331:
                this.maxAmmo = 10;
                this.ammunition = this.maxAmmo;
                this.mass = 300;

                this.attackSpeed = 0.2f;
                this.damage = 2000;
                sprite = sprites.getSubImage(2, 2);
                missileSprite = new Image("Big_Missile.png");
                engine = EngineType.MR002;
                break;
            case L341:
                this.maxAmmo = 10;
                this.ammunition = this.maxAmmo;
                this.mass = 600;

                this.attackSpeed = 0.4f;
                this.damage = 2000;
                sprite = sprites.getSubImage(3, 2);
                missileSprite = new Image("Big_Missile.png");
                engine = EngineType.MR002;
                break;
            case L901:
                this.maxAmmo = 200;
                this.ammunition = this.maxAmmo;
                this.mass = 1000;

                this.attackSpeed = 5;
                this.damage = 500;
                sprite = sprites.getSubImage(4, 2);
                missileSprite = new Image("Small_Missile.png");
                engine = EngineType.MR001;
                break;

        }
        attackInterval = (int) Math.ceil(1000 / attackSpeed);
        relVect = new Vector2f((float) relX + emitX - ship.getSprite().getCenterOfRotationX(), (float) relY + emitY - ship.getSprite().getCenterOfRotationY());
    }

    @Override
    public void fire(float delta, ProjectileSystem prs, ParticleSystem psy) {

        int realDelta = (int) Math.ceil(delta * (1000 / 60));
        relVect = new Vector2f((float) relX + emitX - ship.getSprite().getCenterOfRotationX(), (float) relY + emitY - ship.getSprite().getCenterOfRotationY());
        relVect.setTheta(relVect.getTheta() + ship.getAngle());
        fireCounter += realDelta;
        if ((ammunition == maxAmmo) || ((ammunition > 0) && (fireCounter > attackInterval))) {

            ammunition--;

            Vector2f bulletSpeed = new Vector2f(1, 0);

            bulletSpeed.setTheta(sprite.getRotation() - 90);
            bulletSpeed.add(ship.getVelocity());

            Missile missile;
            try {

                missile = new Missile((int) Math.floor(ship.getX()) + ship.getSprite()
                        .getCenterOfRotationX() + relVect.getX(),
                        (int) Math.floor(ship.getY() + ship.getSprite().getCenterOfRotationY()
                                + relVect.getY()), 100, bulletSpeed, new Vector2f(0, 0), 0, missileSprite, engine, psy);
                missile.setAngle(sprite.getRotation());
                missile.setProvenance(ship);
                missile.setPuissance(damage);
                prs.add(missile);

                fireCounter = 0;
                if (ship instanceof PlayerShip) {
                    firingSound.play(1f, 0.1f);
                }

            } catch (SlickException ex) {

            }
        }
    }

    /**
     * Réduit l'intervalle entre le tir lorsque le lanceur ne tire pas.
     *
     * @param delta Le ratio sur 60 IPS.
     */
    public void cooldown(float delta) {
        int realDelta = (int) Math.ceil(delta * (1000 / 60));
        fireCounter += realDelta;
    }

}
