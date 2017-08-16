/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.hud;

import gameComponents.entities.ships.Ship;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Une barre de vie de petite taille pour les vaisseaux.
 * @author Jocelyn Bouchard
 */
public class LifeBar {

    private Ship ship;

    private int SIZEX = 150;

    final private int SIZEY = 8;

    private int relX;
    private int relY;

    /**
     * Crée une nouvelle barre de vie à placer au-dessus du vaisseau.
     *
     * @param ship Le vaisseau en question.
     * @param relX Position relative en X par rapport au vaisseau.
     * @param relY Position relative en Y par rapport au vaisseau.
     */
    public LifeBar(Ship ship, int relX, int relY) {

        this.relX = relX;
        this.relY = relY;
        this.ship = ship;
        SIZEX = ship.getSprite().getWidth();

    }

    /**
     * Dessin de la barre de vie.
     *
     * @param x Position X.
     * @param y Position Y.
     */
    public void draw(float x, float y) {

        Graphics g = new Graphics();

        Color color = new Color((float) (1 - ((float) ship.getCurrentHp() / (float) ship.getMaxHp())), (float) ((float) ship.getCurrentHp() / (float) ship.getMaxHp()), 0);

        g.setColor(color);

        g.fillRect(x + relX, y + relY, (float) ((float) ship.getCurrentHp() / (float) ship.getMaxHp()) * SIZEX, SIZEY);

        if (ship.getCurrentArmor() > 0) {
            g.setColor(Color.lightGray);

            g.fillRect(x + relX, y + relY + 7,
                    (float) ((float) ship.getCurrentArmor() / (float) ship.getMaxArmor()) * SIZEX, SIZEY);
        }

        g.setColor(Color.black);
        for (int i = 0; i < SIZEX; i += SIZEX / (0.001f * (ship.getCurrentHp()))) {
            g.drawLine(x + relX + i, y + relY, x + relX + i, y + relY + SIZEY);
        }
        if (ship.getCurrentArmor() > 0) {
            for (int i = 0; i < SIZEX; i += SIZEX / (0.001f * (ship.getCurrentArmor()))) {
                g.drawLine(x + relX + i, y + relY + 7, x + relX + i, y + relY + SIZEY);
            }
        }

        g.setColor(Color.white);

        g.drawRect(x + relX, y + relY, SIZEX, SIZEY);
        g.drawRect(x + relX, y + relY + 7, SIZEX, SIZEY);

    }
}
