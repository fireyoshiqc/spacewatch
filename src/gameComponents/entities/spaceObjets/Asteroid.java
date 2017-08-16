/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameComponents.entities.spaceObjets;

import gameComponents.entities.PhysicsObject;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/**
 * CETTE CLASSE N'EST PAS UTILISÉE (à l'instant de la remise du projet).
 * @author 1331037
 */
public class Asteroid extends PhysicsObject {
    
    double width;
    double heigth;

    /**
     *
     */
    public enum Resource {

        /**
         *
         */
        
        IRON,

        /**
         *
         */
        COPPER,

        /**
         *
         */
        SILVER,

        /**
         *
         */
        GOLD,

        /**
         *
         */
        PLATINUM;
    }
    Resource resource;

    /**
     *
     * @param x
     * @param y
     * @param mass
     * @param velocity
     * @param acceleration
     * @param angularSpeed
     * @param seed
     * @throws SlickException
     */
    public Asteroid(double x, double y, double mass, Vector2f velocity, Vector2f acceleration, double angularSpeed, String seed) throws SlickException {
        super(x, y, mass, velocity, acceleration, angularSpeed);
        width = Integer.parseInt(seed.substring(2,4));
        if (Integer.parseInt(seed.substring(2,3)) == 0 || Integer.parseInt(seed.substring(2,3)) == 1) {
            width = 20;
        }
        heigth = 0.75*width;
        int valResource = Integer.parseInt(seed.substring(4,6));
        if (valResource < 50){
            resource = Resource.IRON;
           
        } else if (valResource < 75) {
            resource = Resource.COPPER;
            
        } else if (valResource < 90) {
            resource = Resource.SILVER;
            
        } else if (valResource < 95) {
            resource = Resource.GOLD;
            
        } else {
            resource = Resource.PLATINUM;
            
        }
        
    }

    
    
}
