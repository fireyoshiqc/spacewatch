/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities.spaceObjets;

import gameComponents.entities.PhysicsObject;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

/**
 * Une planète!
 * @author 1331037
 */
public class Planet extends PhysicsObject {

    private boolean atmosphere;
    private boolean water;
    private double radius;
    private double density;
    private Vector2f orbitRadius;
    private SpriteSheet sprites;

    /**
     * La race associée à la planète
     */
    private int race;

    /**
     * Crée une planète.
     * 
     * @param x La position en X.
     * @param y La position en Y.
     * @param mass La masse de l'objet.
     * @param velocity Le vecteur vitesse de l'objet.
     * @param acceleration Le vecteur accélération de l'objet.
     * @param angularSpeed La vitesse rotationnelle de l'objet.
     * @param seed La graine pseudo-aléatoire utilisée pour la génération de la planète
     * @throws SlickException
     */
    public Planet(double x, double y, double mass, Vector2f velocity, Vector2f acceleration, double angularSpeed, String seed) throws SlickException {
        super(x, y, mass, velocity, acceleration, angularSpeed);

        sprites = new SpriteSheet("Planets_Spritesheet.png", 1024, 1024);
        radius = Integer.parseInt(seed.substring(2, 7));
        density = Integer.parseInt(seed.substring(7, 9)) / 5;
        if (density == 0) {
            density = 0.1 / 1000;
        }
        this.mass = (density / 10000) * 4 * Math.PI * Math.pow(radius, 3) / 3;

        if (density < 3.5) {
            atmosphere = true;

            water = false;

            sprite = sprites.getSprite(2, 0);
            race = 2;
        } else {
            if (Integer.parseInt(seed.substring(2, 3)) >= 0 && Integer.parseInt(seed.substring(2, 3)) <= 4) {
                water = true;

                sprite = sprites.getSprite(0, 0);
                race = 0;
            } else {
                water = false;

                sprite = sprites.getSprite(3, 0);
                race = 3;
            }
            if (Integer.parseInt(seed.substring(3, 4)) >= 0 && Integer.parseInt(seed.substring(3, 4)) <= 4) {
                atmosphere = true;

                sprite = sprites.getSprite(1, 0);
                race = 1;
            } else {
                atmosphere = false;

                sprite = sprites.getSprite(3, 0);
                race = 3;
            }
        }

    }

    /**
     * Détermine le rayon d'orbite de la planète.
     * @param orbitRadius Le rayon à utiliser, sous forme de vecteur.
     */
    public void setOrbitRadius(Vector2f orbitRadius) {
        this.orbitRadius = orbitRadius;
    }

    /**
     *
     * @return Le rayon d'orbite de la planète.
     */
    public Vector2f getOrbitRadius() {
        return orbitRadius;
    }

}
