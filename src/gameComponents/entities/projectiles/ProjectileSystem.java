/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities.projectiles;

import gameComponents.entities.PhysicsObject;
import gameComponents.entities.ships.Ship;
import java.util.ArrayList;
import model.collisionSystem.CollisionChecker;
import model.particleSystem.AcidParticle;
import model.particleSystem.ExplosionEmitter;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Système de gestion des projectiles qui calcule toutes les collisions et
 * diverses interactions par rapport à ceux-ci.
 *
 * @author 1331037
 */
public class ProjectileSystem {

    private ArrayList<Projectile> projectileList;
    private ArrayList<Laser> laserList;
    private ArrayList<AcidParticle> acidList;
    private ArrayList<Ship> contents;

    private Sound explosion;
    private Sound clock;

    /**
     * Crée un nouveau système de gestion de projectiles.
     *
     * @throws org.newdawn.slick.SlickException
     */
    public ProjectileSystem() throws SlickException {
        projectileList = new ArrayList<>();
        laserList = new ArrayList<>();
        acidList = new ArrayList<>();
        explosion = new Sound("Normal_Explosion.ogg");
        clock = new Sound("MenuSelect.ogg");

    }

    /**
     * Ajoute un projectile au système.
     *
     * @param projectile Le nouveau projectile à ajouter au système de
     * projectiles.
     */
    public void add(Projectile projectile) {
        projectileList.add(projectile);
    }

    /**
     * Ajoute un laser au système.
     *
     * @param laser Le laser à ajouter au système.
     */
    public void addLaser(Laser laser) {
        laserList.add(laser);
    }

    /**
     * Ajoute une particule d'acide au système.
     *
     * @param p La particule d'acide à ajouter au système.
     */
    public void addParticle(AcidParticle p) {
        acidList.add(p);
    }

    /**
     * Enlève un laser du système.
     *
     * @param laser Le laser à enlever au système.
     */
    public void removeLaser(Laser laser) {
        if (laserList.contains(laser)) {
            laserList.remove(laser);
        }
    }

    /**
     * Enlève une particule d'acide du système.
     *
     * @param p La particule à enlever du système.
     */
    public void removeParticle(AcidParticle p) {
        if (acidList.contains(p)) {
            acidList.remove(p);
        }
    }

    /**
     * Mise à jour. Ceci est une méthode complexe, qui calcule la position des
     * différents projectiles et vérifie leurs collisions avec les différents
     * vaisseaux présents dans le jeu.
     *
     * @param delta Le ratio de mise à jour par rapport à 60 IPS.
     * @param contents Les vaisseaux présents dans le jeu.
     * @param clc Le système de collisions.
     * @param psy Le système de particules.
     */
    public void update(float delta, ArrayList<Ship> contents, CollisionChecker clc, ParticleSystem psy) {
        this.contents = contents;
        ArrayList<Projectile> toRemove = new ArrayList<>();

        ArrayList<Ship> deadShips = new ArrayList<>();
        for (Projectile projectile : projectileList) {
            if (projectile instanceof Missile) {
                Missile missile = (Missile) projectile;

                missile.getPropellant().update(psy, delta, missile.getAngle(), 1, missile);

                if (missile.getPropellant().getLeftFuel() > 0) {
                    missile.update(missile.getPropellant().getForce(), 0, delta, psy, this);

                } else {
                    missile.getPropellant().getEmitter().setEnabled(false);
                    missile.update(0, 0, delta, psy, this);

                }
            } else {
                if (projectile instanceof Bomb) {
                    Bomb bomb = (Bomb) projectile;
                    bomb.update(delta, psy);

                    if (bomb.playClock()) {
                        if (!clock.playing() && !bomb.isHasPlayed()) {

                            clock.play();

                        }

                    }

                    if (bomb.isDestroyed()) {

                        explosion.play();

                        for (Ship vaisseau : contents) {
                            if (bomb.getProvenance() != vaisseau || bomb.getProvenance().getTeam() != vaisseau.getTeam()) {
                                vaisseau.damageShield(bomb.explode(vaisseau));
                                vaisseau.damageHP((int) bomb.explode(vaisseau));
                            }

                        }
                        toRemove.add(bomb);
                    }

                }
                projectile.update(0, 0, delta, psy, this);
            }

            for (PhysicsObject obj1 : contents) {
                if (clc.simpleRadialCollision(obj1, projectile)) {

                    Ship vaisseau1 = projectile.getProvenance();

                    if (obj1 instanceof Ship) {
                        Ship vaisseau2 = (Ship) obj1;

                        if ((Ship) vaisseau2 != (Ship) vaisseau1) {
                            if (vaisseau2.getTeam() == vaisseau1.getTeam()) {
                                if (projectile instanceof Bomb) {
                                    Bomb bomb = (Bomb) projectile;
                                    bomb.collision(psy);
                                    explosion.play();
                                }
                                toRemove.add(projectile);
                            } else {
                                if (projectile instanceof Bomb) {
                                    Bomb bomb = (Bomb) projectile;
                                    bomb.collision(psy);
                                    explosion.play();
                                    for (Ship vaisseau : contents) {
                                        if (bomb.getProvenance() != vaisseau || bomb.getProvenance().getTeam() != vaisseau.getTeam()) {
                                            vaisseau.damageShield(bomb.explode(vaisseau));
                                            vaisseau.damageHP((int) bomb.explode(vaisseau));

                                        }

                                    }
                                } else if (projectile instanceof Missile) {
                                    Missile missile = (Missile) projectile;
                                    missile.getPropellant().getEmitter().setEnabled(false);

                                    psy.addEmitter(new ExplosionEmitter((int) missile.getColCenterX(), (int) missile.getColCenterY(), missile.getPuissance() / 20));

                                    vaisseau2.damageShield(projectile.getPuissance());
                                    vaisseau2.damageHP(projectile.getPuissance());
                                } else {
                                    vaisseau2.damageShield(projectile.getPuissance());
                                    vaisseau2.damageHP(projectile.getPuissance());
                                }
                                toRemove.add(projectile);
                            }

                            if (vaisseau2.isDestroyed()) {
                                deadShips.add(vaisseau2);

                            }

                        }
                    }

                }
            }

        }
        projectileList.removeAll(toRemove);

        for (Laser laser : laserList) {

            Ship vaisseau2 = laser.getTarget();

            if (vaisseau2 != laser.getProvenance()) {

                vaisseau2.damageShield((float) laser.getPower() / 100);
                vaisseau2.damageHP((int) (laser.getPower()));

                if (vaisseau2.isDestroyed()) {
                    deadShips.add(vaisseau2);

                }

            }
        }

        ArrayList<AcidParticle> deadAcid = new ArrayList<>();
        for (AcidParticle p : acidList) {
            for (PhysicsObject obj1 : contents) {

                Ship vaisseau1 = p.getProvenance();
                Object s2 = obj1;
                if (s2 instanceof Ship) {
                    Ship vaisseau2 = (Ship) s2;

                    if ((Ship) vaisseau2 != (Ship) vaisseau1) {

                        if (p.getX() > vaisseau2.getColCenterX() - vaisseau2.getCollisionRadius() - p.getSize() / 2
                                && p.getX() < vaisseau2.getColCenterX() + vaisseau2.getCollisionRadius() + p.getSize() / 2
                                && p.getY() > vaisseau2.getColCenterY() - vaisseau2.getCollisionRadius() - p.getSize() / 2
                                && p.getY() < vaisseau2.getColCenterY() + vaisseau2.getCollisionRadius() + p.getSize() / 2) {

                            vaisseau2.damageShield(10 * p.getDamage());
                            vaisseau2.damageHP((int) p.getDamage());
                        }

                        if (vaisseau2.isDestroyed()) {
                            deadShips.add(vaisseau2);

                        }

                    }

                }

            }
            if (p.getLife() < 10) {
                deadAcid.add(p);
            }

        }
        acidList.removeAll(deadAcid);

        contents.removeAll(deadShips);
        for (Ship ship : deadShips) {
            ship.destroy(psy, this);

        }

    }

    /**
     * Dessin du système de particules.
     *
     * @param worldClip Le rectangle délimitant la zone à dessiner.
     * @param scale Le ratio sur la taille (normalement 1).
     * @param psy Le système de particules.
     */
    public void draw(Rectangle worldClip, float scale, ParticleSystem psy) {
        ArrayList<Projectile> toRemove = new ArrayList<>();

        for (Projectile projectile : projectileList) {
            if (projectile.getX() > worldClip.getX() && projectile.getX() < worldClip.getMaxX()
                    && projectile.getY() > worldClip.getY() && projectile.getY() < worldClip.getMaxY()) {
                projectile.draw();
            } else {
                toRemove.add(projectile);

            }

        }

        projectileList.removeAll(toRemove);

        for (Laser laser : laserList) {
            laser.draw(scale);

        }

    }

    /**
     *
     * @return Les vaisseaux étant pris en compte par le système de projectiles.
     */
    public ArrayList<Ship> getNearbyShips() {
        return contents;

    }

    /**
     *
     * @return La liste de particules d'acide en vie.
     */
    public ArrayList<AcidParticle> getAcidList() {
        return acidList;
    }

}
