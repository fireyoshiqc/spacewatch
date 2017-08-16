/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.hud;

import gameComponents.modules.specials.Radar;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Une interface pour la détection du radar du joueur.
 * @author 1331037
 */
public class RadarUI {

    private BaseUI ui;
    private int relX;
    private int relY;
    private Radar radar;

    /**
     * Crée une nouvelle interface de radar.
     *
     * @param ui Interface graphique associée.
     * @param relX Position relative en X.
     * @param relY Position relative en Y.
     * @param radar Radar du vaisseau du joueur.
     */
    public RadarUI(BaseUI ui, int relX, int relY, Radar radar) {
        this.ui = ui;
        this.radar = radar;

        this.relX = relX;
        this.relY = relY;

    }

    /**
     * Dessine les ennemis perçus par le radar du vaisseau du joueur sur
     * l'interface correspondante.
     */
    public void draw() {
        float scx = ui.getScx();
        float scy = ui.getScy();

        Graphics g = new Graphics();
        g.setColor(Color.cyan);
        g.drawLine(scx * (relX + 200), scy * (relY + 200), scx * (relX + 200) - (float) (scx * (-190 * Math.cos(Math.toRadians(radar.getScanAngle())))), scy * (relY + 200) - (float) (scy * (-190 * Math.sin(Math.toRadians(radar.getScanAngle())))));
        g.setColor(Color.red);

        if (radar.getLastFound() < 2050) {
            g.fillOval(scx * (relX + 200 + (radar.getLastDistance().getX() / radar.getRadarRange()) * 150), scy * (relY + 196 + (radar.getLastDistance().getY() / radar.getRadarRange()) * 150), scx * 8, scy * 8);

        }
    }

}
