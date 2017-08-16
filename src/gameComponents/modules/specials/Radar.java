/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.specials;

import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.entities.ships.Ship;
import gameComponents.modules.Module;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

/**
 * Radar qui détecte les ennemis à proximité et émet des pings sonores.
 *
 * @author 1331037
 */
public class Radar extends Module {

    /**
     * Type de radar.
     */
    public enum RadarType {

        /**
         * Le radar de base.
         */
        UXA;

    }
    private RadarType type;

    private Ship ship;
    private Sound pingSound;

    private float radarRange;
    private float scanAngle;
    private Vector2f distance;
    private Vector2f lastDistance;
    private int lastFound = 5000;
    private boolean scanEnabled = true;

    /**
     * Crée un radar.
     *
     * @param type Le type de radar.
     * @param ship Le vaisseau associé au radar.
     * @throws SlickException
     */
    public Radar(Radar.RadarType type, Ship ship) throws SlickException {
        sprites = new SpriteSheet("Weapon_SpriteSheet.png", 64, 64);
        pingSound = new Sound("SonarBeep.ogg");
        scanAngle = 0;

        this.type = type;
        this.ship = ship;

        switch (type) {
            case UXA:

                this.radarRange = 20000;

                this.mass = 500;

                sprite = sprites.getSubImage(0, 0);

                break;

        }
    }

    /**
     * Mise à jour. Trouve les ennemis à l'intérieur du rayon de détection du
     * radar de façon circulaire.
     *
     * @param prs
     * @param delta
     */
    public void update(ProjectileSystem prs, float delta) {
        if (scanEnabled) {
            int realDelta = (int) Math.ceil(delta * (1000 / 60));
            scanAngle = (scanAngle + delta * 3) % 360;
            lastFound += realDelta;

            for (Ship shipCheck : prs.getNearbyShips()) {
                if (!shipCheck.equals(ship)) {

                    if (lastFound > 200) {
                        distance = new Vector2f(shipCheck.getColCenterX() - ship.getColCenterX(),
                                shipCheck.getColCenterY() - ship.getColCenterY());

                        if (distance.getTheta() > (scanAngle - (delta * 3)) % 360 && distance.getTheta() <= scanAngle && distance.length() <= radarRange) {

                            pingSound.play(1f + (radarRange - distance.length()) / radarRange, (ship.getSprite().getHeight() / 256) * 0.2f);

                            lastFound = 0;
                            lastDistance = distance;
                        }
                    }

                }
            }
        }

    }

    /**
     *
     * @return Le rayon de détection du radar.
     */
    public float getRadarRange() {
        return radarRange;
    }

    /**
     *
     * @return L'angle de scan du radar.
     */
    public float getScanAngle() {
        return scanAngle;
    }

    /**
     *
     * @return La distance entre le vaisseau et un ennemi détecté.
     */
    public Vector2f getLastDistance() {
        return lastDistance;
    }

    /**
     *
     * @return Le temps en millisecondes depuis le dernier ennemi trouvé
     */
    public int getLastFound() {
        return lastFound;
    }

    /**
     *
     * @param scanEnabled L'état à donner au scanner du radar.
     */
    public void setScanEnabled(boolean scanEnabled) {
        this.scanEnabled = scanEnabled;
    }

}
