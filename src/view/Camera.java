/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;

/**
 * Un système simple pour suivre le joueur.
 * @author 1331037
 */
public class Camera extends Rectangle {

    /**
     * Crée une nouvelle caméra.
     *
     * @param x La position en X de la caméra.
     * @param y La position en Y de la caméra.
     * @param appgc Le conteneur de jeu.
     */
    public Camera(float x, float y, GameContainer appgc) {
        super(x - (appgc.getScreenWidth() / 2), y - (appgc.getHeight() / 2), appgc.getWidth(), appgc.getHeight());
    }

}
