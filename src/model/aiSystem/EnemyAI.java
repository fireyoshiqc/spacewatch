/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.aiSystem;

import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.entities.ships.PlayerShip;
import gameComponents.entities.ships.Ship;
import java.util.ArrayList;
import org.newdawn.slick.geom.Vector2f;

/**
 * Intelligence artificielle pour les vaisseaux ayant un comportement hostile.
 * @author Félix
 */
public class EnemyAI extends BaseAI {

    private PlayerShip player;

    /**
     * Crée une intelligence artificielle ennemie.
     *
     * @param activeShips Vaisseaux actifs en jeu.
     * @param aiShip Vaisseau à contrôler par l'intelligence.
     * @param race Race du vaisseau.
     * @param set Talent du vaisseau.
     * @param special Particularité du vaisseau.
     */
    public EnemyAI(ArrayList<Ship> activeShips, Ship aiShip, Race race, Skillset set, Special special) {
        super(activeShips, aiShip, race, set, special);
        for (Ship ship : potentialTargets) {
            if (ship instanceof PlayerShip) {
                player = (PlayerShip) ship;
            }
        }
        createRandomPath(10, 10000, true);

    }

    /**
     * Évaluation du risque d'attaque en fonction des paramètres définissant
     * l'IA.
     */
    private void evaluateAttackRisk() {
        risk = 0;

        if (weaponry > player.getBullets()) {
            risk -= ((float) weaponry / (float) player.getBullets()) - 1;

        }
        //else {
//            risk += ((float) player.getBullets() / (float) weaponry) - 1;
//        }
        if (speed > player.getMaxVelocity()) {
            risk -= (speed / player.getMaxVelocity()) - 1;
        } else {
            risk += (player.getMaxVelocity() / speed) - 1;
        }

        if (defense > player.getCurrentShield() + player.getCurrentHp() + player.getCurrentArmor()) {
            risk -= (defense / (player.getCurrentShield() + player.getCurrentHp())) - 1;
        } else {
            risk += ((player.getCurrentShield() + player.getCurrentHp() + player.getCurrentArmor()) / defense) - 1;
        }

    }

    /**
     * Choix de l'état à adopter pour l'IA.
     */
    private void evaluateAttack() {
        evaluateAttackRisk();
        risk -= aggro / 20;
        risk += intelligence / 20;
        if (risk > 0) {
            risk /= (skill / 10);
        } else {
            risk *= (skill / 10);
        }
        if (risk > 10) {
            state = State.FLEE;

        } else if (risk <= 10 && risk > 1) {
            state = State.DEFENSE;
        } else if (risk <= 1 && risk > -1) {
            state = State.NEUTRAL;
        } else {
            state = State.ATTACK;
        }
    }

    /**
     * Recherche d'un joueur (ennemi).
     */
    private void searchForPlayer() {

        Vector2f distance = new Vector2f((float) (player.getX() - aiShip.getX()), (float) (player.getY() - aiShip.getY()));
        inRadarRange = distance.length() < aiShip.getRadarRange();
        if (player.getCurrentHp() == 0) {
            inRadarRange = false;
        }

    }

    /**
     * Orientation vers le joueur.
     *
     * @param multi Ratio sur 60 IPS.
     */
    private void targetPlayer(float multi) {
        followingPath = false;
        Vector2f distance = new Vector2f((float) (player.getColCenterX() - aiShip.getColCenterX()), (float) (player.getColCenterY() - aiShip.getColCenterY()));
        double relativeAngle = distance.getTheta() + 90;
        double angle = aiShip.getAngle();
        double angularSpeed = aiShip.getAngularSpeed();

        if (angle >= 360 || angle <= -360) {
            angle = angle % 360;

        }

        if (angle < (relativeAngle - 2) % 360 || angle > (relativeAngle + 2) % 360) {

            if ((angle < (relativeAngle - 2) % 360) && angularSpeed <= 2.5) {

                angularSpeed += multi * (aiShip.getMaxForce() / aiShip.getMass());

            } else if (angle > (relativeAngle + 2) % 360 && angularSpeed >= -2.5) {

                angularSpeed -= multi * (aiShip.getMaxForce() / aiShip.getMass());

            }

        } else {
            angularSpeed /= 1.1;
            if ((angle > (relativeAngle - 0.75) % 360 || angle < (relativeAngle + 0.75) % 360)) {
                angularSpeed /= 1.2;
            }
        }
        aiShip.setAngularSpeed(angularSpeed);

    }

    /**
     * Attaque du joueur en portée de tir.
     *
     * @param multi Ratio sur 60 IPS.
     * @param prs Système de projectiles.
     */
    private void attackPlayer(float multi, ProjectileSystem prs) {
        if (player.getCurrentHp() > 0) {
            targetPlayer(multi);
            double playerShipX = player.getColCenterX();
            double x = aiShip.getColCenterX();
            double playerShipY = player.getColCenterY();
            double y = aiShip.getColCenterY();

            float xComp = (float) (playerShipX - x);

            float yComp = (float) (playerShipY - y);

            Vector2f distance = new Vector2f(xComp, yComp);
            double relativeAngle = distance.getTheta() + 90;

            if (distance.length() <= 2000) {

                aiShip.softBrake(multi);
                aiShip.setThrustersOn(0);
                double angle = aiShip.getAngle();

                if ((angle % 360 > (relativeAngle - 10) % 360 && angle % 360 < (relativeAngle + 10) % 360)) {
                    if (aiShip.hasBullets()) {
                        aiShip.firePrimary(multi, prs);
                    }

                    aiShip.fireSecondary(multi, prs);

                }

                if (distance.length() <= 1000 || aiShip.getVelocity().length() < 10) {
                    aiShip.brake(multi);
                    aiShip.setThrustersOn(0);
                    if (aiShip.hasAcid() && !aiShip.hasBullets()) {
                        aiShip.firePrimary(multi, prs);

                    } else {
                        if ((player.getCurrentShield() <= 0 || aiShip.getBullets() == 0)) {
                            if (aiShip.hasLasers()) {
                                if (distance.length() <= aiShip.getLaserRange()) {
                                    aiShip.fireSecondary(multi, prs);
                                }

                            }

                        }
                    }
                }

            } else if (distance.length() > 2000 && distance.length() < aiShip.getRadarRange()) {

                double angle = aiShip.getAngle();

                if ((angle % 360 > (relativeAngle - 10) % 360 && angle % 360 < (relativeAngle + 10) % 360)) {

                    aiShip.setThrustersOn(1);

                } else {

                    aiShip.setThrustersOn(0);
                    aiShip.softBrake(multi);

                }

            } else {
                aiShip.brake(multi);
                aiShip.setThrustersOn(0);
            }

        }
    }

    
    @Override
    public void update(float multi, ProjectileSystem prs) {

        weaponry = aiShip.getBullets();
        speed = aiShip.getMaxVelocity();
        defense = aiShip.getCurrentShield() + aiShip.getCurrentHp() + aiShip.getCurrentArmor();
        aiShip.stopLaserFire(prs);
        aiShip.cooldown(multi);

        searchForPlayer();
        if (inRadarRange) {

            evaluateAttack();
            if (state.equals(State.ATTACK)) {
                attackPlayer(multi, prs);
            } else if (state.equals(State.NEUTRAL)) {

                flyToNextPoint(multi);

            }

        } else {
            state = State.NEUTRAL;
            flyToNextPoint(multi);
        }

    }

}
