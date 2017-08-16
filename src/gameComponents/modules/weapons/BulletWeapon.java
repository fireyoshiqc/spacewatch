/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.weapons;

import gameComponents.entities.projectiles.Bullet;
import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.entities.ships.PlayerShip;
import gameComponents.entities.ships.Ship;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Le classique canon et la classique mitrailleuse.
 * @author 1331037
 */
public class BulletWeapon extends Weapon {

    /**
     * Type d'arme.
     */
    public enum WeaponType {

        /**
         * Fusil à canon simple.
         */
        M201,
        /**
         * Fusil à canon double.
         */
        M211,
        /**
         * Fusil à canon triple.
         */
        M221,
        /**
         * Fusil-tourelle.
         */
        M231,
        /**
         * Artillerie à canon simple.
         */
        M241,
        /**
         * Artillerie à canon double.
         */
        M251,
        /**
         * Fusil-mitrailleur à canon simple.
         */
        M261,
        /**
         * Fusil-mitrailleur à canon double.
         */
        M271
    }

    private WeaponType type;
    private int attackInterval;
    private int fireCounter = 0;
    private Vector2f relVect;
    private Ship ship;
    private Sound firingSound;
    private Image bulletSprite;

    /**
     * Crée un fusil.
     *
     * @param type Type d'arme.
     * @param ship Vaisseau auquel l'arme est associée.
     * @throws SlickException
     */
    public BulletWeapon(WeaponType type, Ship ship) throws SlickException {
        sprites = new SpriteSheet("Weapon_SpriteSheet.png", 64, 64);
        this.ship = ship;

        this.type = type;
        emitX = 26;
        emitY = 0;
        switch (type) {
            case M201:
                this.maxAmmo = 200;
                this.ammunition = this.maxAmmo;
                this.mass = 50;

                this.attackSpeed = 5;
                this.damage = 25;
                sprite = sprites.getSubImage(0, 0);
                emitX = 26;

                try {
                    firingSound = new Sound("Small_Gun.ogg");
                    bulletSprite = new Image("testBullet.png");
                } catch (SlickException e) {

                }
                break;
            case M211:
                this.maxAmmo = 400;
                this.ammunition = this.maxAmmo;
                this.mass = 100;

                this.attackSpeed = 10;
                this.damage = 25;
                sprite = sprites.getSubImage(1, 0);
                try {
                    firingSound = new Sound("Small_Gun.ogg");
                    bulletSprite = new Image("testBullet.png");
                } catch (SlickException e) {

                }
                break;
            case M221:
                this.maxAmmo = 600;
                this.ammunition = this.maxAmmo;
                this.mass = 150;

                this.attackSpeed = 15;
                this.damage = 25;
                sprite = sprites.getSubImage(2, 0);
                try {
                    firingSound = new Sound("Small_Gun.ogg");
                    bulletSprite = new Image("testBullet.png");
                } catch (SlickException e) {

                }
                break;
            case M231:
                this.maxAmmo = 500;
                this.ammunition = this.maxAmmo;
                this.mass = 150;

                this.attackSpeed = 10;
                this.damage = 25;
                sprite = sprites.getSubImage(3, 0);
                try {
                    firingSound = new Sound("Small_Gun.ogg");
                    bulletSprite = new Image("testBullet.png");
                } catch (SlickException e) {

                }
                break;
            case M241:
                this.maxAmmo = 100;
                this.ammunition = this.maxAmmo;
                this.mass = 300;

                this.attackSpeed = 2;
                this.damage = 100;
                sprite = sprites.getSubImage(4, 0);
                emitX = 18;
                try {
                    firingSound = new Sound("Big_Gun.ogg");
                    bulletSprite = new Image("Big_Bullet.png");
                } catch (SlickException e) {

                }
                break;
            case M251:
                this.maxAmmo = 200;
                this.ammunition = this.maxAmmo;
                this.mass = 600;

                this.attackSpeed = 4;
                this.damage = 100;
                sprite = sprites.getSubImage(5, 0);
                emitX = 18;
                try {
                    firingSound = new Sound("Big_Gun.ogg");
                    bulletSprite = new Image("Big_Bullet.png");
                } catch (SlickException e) {

                }
                break;
            case M261:
                this.maxAmmo = 1000;
                this.ammunition = this.maxAmmo;
                this.mass = 200;

                this.attackSpeed = 40;
                this.damage = 25;
                sprite = sprites.getSubImage(6, 0);
                try {
                    firingSound = new Sound("Chain_Gun.ogg");
                    bulletSprite = new Image("testBullet.png");
                } catch (SlickException e) {

                }
                break;
            case M271:
                this.maxAmmo = 2000;
                this.ammunition = this.maxAmmo;
                this.mass = 400;

                this.attackSpeed = 80;
                this.damage = 25;
                sprite = sprites.getSubImage(7, 0);
                try {
                    firingSound = new Sound("Chain_Gun.ogg");
                    bulletSprite = new Image("testBullet.png");
                } catch (SlickException e) {

                }
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

            Vector2f bulletSpeed = new Vector2f(50, 0);

            bulletSpeed.setTheta(sprite.getRotation() - 90);
            bulletSpeed.add(ship.getVelocity());

            Bullet bullet;
            try {
                bullet = new Bullet((int) Math.floor(ship.getX()) + ship.getSprite()
                        .getCenterOfRotationX() + relVect.getX(),
                        (int) Math.floor(ship.getY() + ship.getSprite().getCenterOfRotationY()
                                + relVect.getY()), 0.1, bulletSpeed, new Vector2f(0, 0), 0, bulletSprite);
                bullet.setAngle(sprite.getRotation());
                bullet.setProvenance(ship);
                bullet.setPuissance(damage);
                prs.add(bullet);

                fireCounter = 0;
                if (ship instanceof PlayerShip) {
                    firingSound.play(1f, 0.2f);
                }

            } catch (SlickException ex) {

            }
        }

    }

    /**
     *
     * @return Les munitions restantes.
     */
    public int getAmmunition() {
        return ammunition;
    }

}
