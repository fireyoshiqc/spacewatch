/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities.spaceObjets;

import gameComponents.entities.PhysicsObject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

/**
 * Une étoile!
 * @author 1331037
 */
public class Star extends PhysicsObject {

    private float heat;

    private float scale;
    private SpriteSheet sprites;

    /**
     * Crée une nouvelle étoile au centre d'un système solaire.
     *
     * @param x La position en X.
     * @param y La position en Y.
     * @param mass La masse de l'objet.
     * @param velocity Le vecteur vitesse de l'objet.
     * @param acceleration Le vecteur accélération de l'objet.
     * @param angularSpeed La vitesse rotationnelle de l'objet.
     * @param seed La graine pseudo-aléatoire utilisée pour la génération de
     * l'étoile.
     * @throws SlickException
     */
    public Star(double x, double y, double mass, Vector2f velocity, Vector2f acceleration, double angularSpeed, String seed) throws SlickException {
        super(x, y, mass, velocity, acceleration, angularSpeed);
        sprites = new SpriteSheet("Stars_Spritesheet.png", 1024, 1024);
        heat = Integer.parseInt(seed.substring(0, 5));
        if (heat < 2500) {
            sprite = sprites.getSprite(0, 0);

        } else if (heat < 6000) {
            sprite = sprites.getSprite(1, 0);
        } else if (heat < 20000) {
            sprite = sprites.getSprite(3, 0);
        } else {
            sprite = sprites.getSprite(2, 0);
        }

        scale = Integer.parseInt(seed.substring(7, 9));
        if (scale < 10) {
            scale = 10;
        }

        sprite = sprite.getScaledCopy(scale);
        sprite.setFilter(Image.FILTER_LINEAR);

    }

}
