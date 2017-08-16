/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chunkSystem;

import gameComponents.entities.PhysicsObject;
import gameComponents.entities.spaceObjets.Planet;
import gameComponents.entities.spaceObjets.Star;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/**
 * Un véritable système solaire avec une étoile et des planètes!
 * @author 1331037
 */
public class SolarSystem extends Chunk {

    private int nbPlanets;
    private Star star;

    /**
     * Crée un nouveau système solaire.
     *
     * @param seed Graine pseudo-aléatoire.
     * @param position Position du chunk.
     * @param type Type du chunk.
     * @throws SlickException
     */
    SolarSystem(String seed, long position, Type type) throws SlickException {
        super(seed, position, type);
        generateSystem();
    }

    /**
     * Génère le système solaire, en y plaçant une étoile et des planètes qui
     * l'orbitent.
     *
     * @throws SlickException
     */
    @Override
    protected void generateSystem() throws SlickException {
        star = new Star(posX * SIZEX + (SIZEX / 2), posY * SIZEY + (SIZEY / 2), 0, new Vector2f(0, 0), new Vector2f(0, 0), 0.1, seed);

        star.manualUpdate((float) star.getX() - star.getWidth() / 2, (float) star.getY() - star.getHeight() / 2);
        contents.add(star);
        nbPlanets = Integer.parseInt(seed.substring(3, 4));
        float speed;
        float scale;
        for (int j = 0; j < nbPlanets; j++) {
            int number = Integer.parseInt(seed.substring(j, j + 1));
            if (number % 5 == 0) {
                scale = 1f;
                speed = 5f;
            } else if (number % 5 == 1) {
                scale = 2f;
                speed = 4f;
            } else if (number % 5 == 2) {
                scale = 3f;
                speed = 3f;
            } else if (number % 5 == 3) {
                scale = 4f;
                speed = 2f;
            } else {
                scale = 5f;
                speed = 1f;
            }
            generatePlanet(j, scale, speed);
        }

    }

    /**
     * Crée une nouvelle planète pour le système solaire.
     *
     * @param i Niveau d'orbite de la planète (1-9).
     * @param scale Taille de la planète.
     * @param speed Vitesse de la planète.
     * @throws SlickException
     */
    private void generatePlanet(int i, float scale, float speed) throws SlickException {
        Planet planet = new Planet(0, 0, 0, new Vector2f(speed, 0), new Vector2f(0, 0), 0, seed);
        planet.setSprite(planet.getSprite().getScaledCopy(scale));
        planet.setOrbitRadius(new Vector2f(0, -50000));

        planet.manualUpdate((float) star.getX() + star.getWidth() / 2 - planet.getWidth() / 2 + planet.getOrbitRadius().getX(), (float) star.getY() + star.getHeight() / 2 - planet.getHeight() / 2 + planet.getOrbitRadius().getY() - 50000 * (i + 1));
        double accCentre = planet.getVelocity().lengthSquared() * planet.getMass() / planet.getOrbitRadius().length();
        Vector2f accVect = new Vector2f((float) accCentre, 0);
        accVect.setTheta(planet.getOrbitRadius().getTheta() + 180);
        planet.setAcceleration(accVect);
        contents.add(planet);

    }

    /**
     * Mise à jour. Fait orbiter les planètes, entre autres.
     *
     * @param delta Ratio sur 60 IPS.
     */
    @Override
    public void update(float delta) {
        for (PhysicsObject object : contents) {
            if (object instanceof Planet) {
                Planet planet = (Planet) object;
                planet.setOrbitRadius(new Vector2f((float) (planet.getX() + planet
                        .getWidth() / 2 - star.getX() - star.getWidth() / 2), (float) (planet.getY() + planet.getHeight() / 2 - star.getY() - star.getHeight() / 2)));

                double accCentre = planet.getVelocity().lengthSquared() / planet.getOrbitRadius().length();

                Vector2f accVect = new Vector2f((float) accCentre, 0);

                accVect.setTheta(planet.getOrbitRadius().getTheta() + 180);
                planet.setAcceleration(accVect);
                planet.setAngle(accVect.getTheta() + 90);

                planet.update(planet.getAcceleration().scale((float) planet.getMass()).length(), 0, delta, null, null);

            }
        }
    }

}
