/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.aiSystem;

import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.entities.ships.Ship;
import java.util.ArrayList;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Vector2f;

/**
 * Classe abstraite englobant les différents types d'intelligence artificielle.
 * @author Félix
 */
public abstract class BaseAI {

    /**
     * Vaisseau contrôlé par l'intelligence.
     */
    protected Ship aiShip;

    /**
     * Liste des cibles potentielles.
     */
    protected ArrayList<Ship> potentialTargets;
    /**
     * Aggressivité.
     */
    protected float aggro,
            /**
             * Intelligence.
             */
            intelligence,
            /**
             * Talent.
             */
            skill;
    /**
     * Armement.
     */
    protected float weaponry,
            /**
             * Vitesse.
             */
            speed,
            /**
             * Défense.
             */
            defense;

    /**
     * État de l'intelligence artificielle.
     */
    protected State state;

    /**
     * Race du vaisseau.
     */
    protected Race race;

    /**
     * Talent particulier du vaisseau.
     */
    protected Skillset set;

    /**
     * Particularité du vaisseau.
     */
    protected Special special;

    /**
     * Trajet suivi en mode patrouille.
     */
    protected Path aiPath;

    /**
     * État de suivi de trajet.
     */
    protected boolean followingPath = false;

    /**
     * Prochain point dans le trajet.
     */
    protected float[] nextPoint;

    /**
     * Index du prochain point.
     */
    protected int nextPointIndex;

    /**
     * Présence d'un ennemi en couverture radar.
     */
    protected boolean inRadarRange = false;

    /**
     * Risque évalué pré-attaque.
     */
    protected float risk = 0;

    /**
     * État de l'IA.
     */
    public enum State {

        /**
         * Attaque.
         */
        ATTACK,
        /**
         * Défense.
         */
        DEFENSE,
        /**
         * Neutre.
         */
        NEUTRAL,
        /**
         * Fuite.
         */
        FLEE
    }

    /**
     * Race.
     */
    public enum Race {

        /**
         * Humain.
         */
        HUMAN,
        /**
         * Avian.
         */
        AVIAN,
        /**
         * Canidé.
         */
        CANIDAE,
        /**
         * Lézard.
         */
        LIZARD,
        /**
         * Skrelch.
         */
        SKRELCH,
        /**
         * Zz'Rax.
         */
        ZZRAX,
        /**
         * Knaraku.
         */
        KNARAKU,
        /**
         * Aeces.
         */
        AECES;

    }

    /**
     * Trait de caractère.
     */
    public enum Skillset {

        /**
         * Guerrier.
         */
        WARRIOR,
        /**
         * Chasseur.
         */
        HUNTER,
        /**
         * Technicien.
         */
        TECHNICIAN,
        /**
         * Courseur.
         */
        RACER,
        /**
         * Travailleur.
         */
        WORKER
    }

    /**
     * Particularité.
     */
    public enum Special {

        /**
         * Agressif.
         */
        AGGRESSIVE,
        /**
         * Maladroit.
         */
        CLUNKY,
        /**
         * Calme.
         */
        CALM,
        /**
         * Intelligent.
         */
        SMART,
        /**
         * Imprudent.
         */
        RECKLESS,
        /**
         * Précis.
         */
        PRECISE
    }

    /**
     * Crée une intelligence de base (abstraite).
     *
     * @param activeShips Vaisseaux actifs en jeu.
     * @param aiShip Vaisseau à contrôler par l'intelligence.
     * @param race Race du vaisseau.
     * @param set Talent du vaisseau.
     * @param special Particularité du vaisseau.
     */
    public BaseAI(ArrayList<Ship> activeShips, Ship aiShip, Race race, Skillset set, Special special) {
        potentialTargets = new ArrayList<>();
        potentialTargets.addAll(activeShips);
        potentialTargets.remove(aiShip);
        this.aiShip = aiShip;
        this.state = State.NEUTRAL;
        this.race = race;
        switch (this.race) {
            case HUMAN:
                aggro = 50;
                intelligence = 50;
                skill = 50;
                break;
            case AVIAN:
                aggro = 30;
                intelligence = 40;
                skill = 80;
                break;
            case CANIDAE:
                aggro = 80;
                intelligence = 50;
                skill = 20;
                break;
            case LIZARD:
                aggro = 10;
                intelligence = 50;
                skill = 90;
                break;
            case SKRELCH:
                aggro = 5;
                intelligence = 5;
                skill = 5;
                break;
            case ZZRAX:
                aggro = 100;
                intelligence = 50;
                skill = 50;
                break;
            case KNARAKU:
                aggro = 100;
                intelligence = 0;
                skill = 0;
                break;
            case AECES:
                aggro = 0;
                intelligence = 100;
                skill = 100;
                break;
            default:
        }
        this.set = set;
        switch (this.set) {
            case WARRIOR:
                aggro += 30;
                intelligence -= 10;
                skill += 20;
                break;
            case HUNTER:
                aggro += 10;
                intelligence += 20;
                skill += 10;
                break;
            case TECHNICIAN:
                aggro -= 30;
                intelligence += 50;
                skill += 30;
                break;
            case RACER:
                aggro -= 50;
                intelligence -= 10;
                skill += 100;
                break;
            case WORKER:
                aggro -= 20;
                intelligence += 10;
                skill -= 10;
                break;

        }
        this.special = special;
        switch (this.special) {
            case AGGRESSIVE:
                aggro += 30;
                break;
            case CALM:
                aggro -= 30;
                break;
            case SMART:
                intelligence += 30;
                break;
            case RECKLESS:
                intelligence -= 30;
                break;
            case PRECISE:
                skill += 30;
                break;
            case CLUNKY:
                skill -= 30;
                break;

        }
        weaponry = aiShip.getMaxBullets();
        speed = aiShip.getMaxVelocity();
        defense = aiShip.getMaxShield() + aiShip.getMaxHp() + aiShip.getCurrentArmor();

    }

    /**
     *
     * @return Le risque calculé.
     */
    public float getRisk() {
        return risk;
    }

    /**
     * Crée un chemin de patrouille aléatoire sur x points pour le vaisseau IA.
     *
     * @param steps Le nombre de points à suivre.
     * @param size La distance maximale entre les points.
     * @param closed Boucle fermée ou non.
     */
    protected void createRandomPath(int steps, float size, boolean closed) {

        int x = (int) aiShip.getColCenterX();
        int y = (int) aiShip.getColCenterY();
        aiPath = new Path(x, y);

        int x2 = x;
        int y2 = y;
        for (int i = 1; i < steps; i++) {
            x2 += (Math.random() - 0.5) * size;
            y2 += (Math.random() - 0.5) * size;
            aiPath.lineTo(x2, y2);
        }
        if (closed) {
            aiPath.close();
        }

    }

    /**
     * Orientation vers le prochain point.
     *
     * @param multi Ratio sur 60 IPS.
     */
    protected void targetPoint(float multi) {

        Vector2f distance = new Vector2f((float) (nextPoint[0] - aiShip.getColCenterX()), (float) (nextPoint[1] - aiShip.getColCenterY()));
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
     * Suivi du trajet.
     *
     * @param multi Ratio sur 60 IPS.
     */
    protected void followPath(float multi) {

        if (!followingPath) {
            followingPath = true;
            nextPointIndex = 1;

        } else if (nextPointIndex > (aiPath.getPointCount() - 1) && (aiPath.closed())) {
            nextPointIndex = 0;

        }

        nextPoint = aiPath.getPoint(nextPointIndex);
        targetPoint(multi);
    }

    /**
     * Déplacement sur le trajet.
     *
     * @param multi Ratio sur 60 IPS.
     */
    protected void flyToNextPoint(float multi) {
        followPath(multi);
        Vector2f distance = new Vector2f((float) (nextPoint[0] - aiShip.getX()), (float) (nextPoint[1] - aiShip.getY()));

        double relativeAngle = distance.getTheta() + 90;

        if (aiShip.getVelocity().length() > 0) {

            if (distance.length() <= 2000) {
                aiShip.brake(multi);

                aiShip.setThrustersOn(1);
                if (distance.length() <= 500) {

                    aiShip.setThrustersOn(0);
                    nextPointIndex++;

                }

            } else {

                double angle = aiShip.getAngle();

                if ((angle % 360 > (relativeAngle - 10) % 360 && angle % 360 < (relativeAngle + 10) % 360)) {

                    aiShip.setThrustersOn(1);

                } else {

                    aiShip.setThrustersOn(0);
                    aiShip.softBrake(multi);

                }
            }
        } else {
            aiShip.setThrustersOn(1);
        }

    }

    /**
     *
     * @return Le chemin suivi par l'IA.
     */
    public Path getAiPath() {
        return aiPath;
    }

    /**
     * Mise à jour de l'IA.
     *
     * @param multi Le ratio sur 60 IPS.
     * @param prs Le système de projectiles.
     */
    public abstract void update(float multi, ProjectileSystem prs);
}
