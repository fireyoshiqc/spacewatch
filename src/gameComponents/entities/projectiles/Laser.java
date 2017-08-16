/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities.projectiles;

import gameComponents.entities.ships.Ship;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Rayon qui vise automatiquement les ennemis et leur fait du dégât sur le temps.
 * @author Félix
 */
public class Laser {

    private float xOrigin;
    private float yOrigin;
    private float xTarget;
    private float yTarget;
    private Color color;
    private float power;
    private Ship ship;
    private Ship target;

    /**
     * Crée un nouveau rayon laser.
     *
     * @param xOrigin Le X d'origine du laser.
     * @param yOrigin Le Y d'origine du laser.
     * @param xTarget Le X visé par le laser.
     * @param yTarget Le Y visé par le laser.
     * @param color La couleur du laser.
     * @param power La puissance du laser (dommage par tick).
     * @param ship Le vaisseau duquel est tiré le laser.
     * @param target Le vaisseau visé par le laser.
     */
    public Laser(float xOrigin, float yOrigin, float xTarget, float yTarget, Color color, float power, Ship ship, Ship target) {
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.color = color;
        this.power = power;
        this.ship = ship;
        this.target = target;

    }

    /**
     * Mise à jour.
     *
     * @param xOrigin Le X d'origine du laser.
     * @param yOrigin Le Y d'origine du laser.
     * @param xTarget Le X visé par le laser.
     * @param yTarget Le Y visé par le laser.
     * @param target Le vaisseau visé par le laser.
     */
    public void update(float xOrigin, float yOrigin, float xTarget, float yTarget, Ship target) {
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.target = target;
    }

    /**
     * Dessin du laser.
     * @param scale Le niveau du zoom du contexte graphique (1 étant le niveau normal).
     */
    public void draw(float scale) {
        Graphics g = new Graphics();
        g.setAntiAlias(true);
        g.setLineWidth(scale * power / 2);
        g.setColor(color);
        g.drawLine(xOrigin, yOrigin, xTarget, yTarget);
        g.setLineWidth(scale * power / 4);
        g.setColor(Color.white);
        g.drawLine(xOrigin, yOrigin, xTarget, yTarget);
        g.resetTransform();

    }

    /**
     * 
     * @return La provenance du laser.
     */
    public Ship getProvenance() {
        return ship;
    }

    /**
     *
     * @return La puissance du laser.
     */
    public float getPower() {
        return power;
    }

    /**
     *
     * @return La cible du laser.
     */
    public Ship getTarget() {
        return target;
    }

}
