/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.weapons;

import gameComponents.entities.projectiles.Bomb;
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
 * Arme qui lance de puissants engins explosifs.
 * @author 1331037
 */
public class BombWeapon extends Weapon {

    private int fireCounter = 0;
    private Ship ship;

    private int attackInterval;
    private Vector2f relVect;
    private Image bombSprite;
    private Sound firingSound;

    /**
     * Type d'arme.
     */
    public enum WeaponType {

        /**
         * Lanceur de bombes de base.
         */
        B101
    }

    /**
     * Crée un lanceur de bombes.
     *
     * @param type Type d'arme.
     * @param ship Vaisseau associé à l'armes.
     * @throws SlickException
     */
    public BombWeapon(WeaponType type, Ship ship) throws SlickException {
        this.ship = ship;
        sprites = new SpriteSheet("Weapon_SpriteSheet.png", 64, 64);
        switch (type) {
            case B101:
                sprite = sprites.getSubImage(0, 3);
                emitX = 26;
                emitY = 0;
                this.damage = 5000;

                this.maxAmmo = 5;
                this.ammunition = this.maxAmmo;
                this.mass = 400;
                attackSpeed = 0.2f;
                break;
        }

        attackInterval = (int) Math.ceil(1000 / attackSpeed);
        relVect = new Vector2f((float) relX + emitX - ship.getSprite().getCenterOfRotationX(), (float) relY + emitY - ship.getSprite().getCenterOfRotationY());
        try {
            firingSound = new Sound("Big_Gun.ogg");
            bombSprite = new Image("Small_Bomb.png");
        } catch (SlickException e) {
        }
    }

    @Override
    public void fire(float delta, ProjectileSystem prs, ParticleSystem psy) {

        int realDelta = (int) Math.ceil(delta * (1000 / 60));

        relVect = new Vector2f((float) relX + emitX - ship.getSprite().getCenterOfRotationX(), (float) relY + emitY - ship.getSprite().getCenterOfRotationY());
        relVect.setTheta(relVect.getTheta() + ship.getAngle());
        fireCounter += realDelta;
        if ((ammunition == maxAmmo) || ((ammunition > 0) && (fireCounter > attackInterval))) {

            ammunition--;

            Vector2f bulletSpeed = new Vector2f(20, 0);

            bulletSpeed.setTheta(sprite.getRotation() - 90);
            bulletSpeed.add(ship.getVelocity());

            Bomb bomb;
            try {
                bomb = new Bomb((int) Math.floor(ship.getX()) + ship.getSprite()
                        .getCenterOfRotationX() + relVect.getX(),
                        (int) Math.floor(ship.getY() + ship.getSprite().getCenterOfRotationY()
                                + relVect.getY()), 0.1, bulletSpeed, new Vector2f(0, 0), 0, bombSprite);
                bomb.setAngle(sprite.getRotation());
                bomb.setProvenance(ship);
                bomb.setPuissance(damage);
                prs.add(bomb);

                fireCounter = 0;
                if (ship instanceof PlayerShip) {
                    firingSound.play(1f, 0.2f);
                }

            } catch (SlickException ex) {

            }
        }

    }

    /**
     * Réduit l'intervalle entre les tirs lorsque l'arme ne tire pas.
     *
     * @param delta Le ratio sur 60 IPS.
     */
    public void cooldown(float delta) {
        int realDelta = (int) Math.ceil(delta * (1000 / 60));
        fireCounter += realDelta;
    }

}
