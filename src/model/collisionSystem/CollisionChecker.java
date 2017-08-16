/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collisionSystem;

import gameComponents.entities.PhysicsObject;
import org.newdawn.slick.geom.Vector2f;

/**
 * Vérificateur de collisions. Boum boum.
 * @author 1331037
 */
public class CollisionChecker {

    /**
     * Crée un nouveau calculateur de collisions.
     */
    public CollisionChecker() {
    }

    /**
     * Teste une collision entre deux objets par leur rayon.
     *
     * @param obj1 Objet 1.
     * @param obj2 Objet 2.
     * @return Collision ou non.
     */
    public boolean simpleRadialCollision(PhysicsObject obj1, PhysicsObject obj2) {
        Vector2f distance = new Vector2f(obj2.getColCenterX() - obj1.getColCenterX(), obj2.getColCenterY() - obj1.getColCenterY());

        return distance.length() <= (obj1.getCollisionRadius() + obj2.getCollisionRadius());

    }

    /**
     * Calcule le résultat d'une collision entre deux objets.
     *
     * @param obj1 Objet 1.
     * @param obj2 Objet 2.
     */
    public void radialCollision(PhysicsObject obj1, PhysicsObject obj2) {

        if (simpleRadialCollision(obj1, obj2)) {

            float x1 = obj1.getVelocity().getX();
            float x2 = obj2.getVelocity().getX();
            float y1 = obj1.getVelocity().getY();
            float y2 = obj2.getVelocity().getY();

            float m1 = (float) obj1.getMass();
            float m2 = (float) obj2.getMass();

            float x1p = ((m1 - m2) / (m1 + m2)) * x1 + (2 * m2 / (m1 + m2)) * x2;
            float x2p = (2 * m1 / (m1 + m2)) * x1 + ((m2 - m1) / (m1 + m2)) * x2;
            float y1p = ((m1 - m2) / (m1 + m2)) * y1 + (2 * m2 / (m1 + m2)) * y2;
            float y2p = (2 * m1 / (m1 + m2)) * y1 + ((m2 - m1) / (m1 + m2)) * y2;

            obj1.setVelocity(new Vector2f(x1p, y1p));
            obj2.setVelocity(new Vector2f(x2p, y2p));

        }

    }

}
