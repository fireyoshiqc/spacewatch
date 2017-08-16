/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.hud;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Une jauge colorée pour définir l'état du vaisseau.
 *
 * @author Félix
 */
public class UIBar {

    BaseUI ui;

    final private int SIZEX = 622;

    final private int SIZEY = 20;
    private Color color;
    private int baseValue;
    private int currentValue;
    private int relX;
    private int relY;
    private TrueTypeFont font;
    private TrueTypeFont font3;

    /**
     * Crée une nouvelle barre (jauge) à appliquer sur l'interface graphique du
     * jeu.
     *
     * @param ui L'interface graphique en question.
     * @param color La couleur de la jauge.
     * @param baseValue La valeur de départ de la jauge.
     * @param currentValue La valeur actuelle de la jauge.
     * @param relX La position relative de la jauge en X.
     * @param relY La position relative de la jauge en Y.
     */
    public UIBar(BaseUI ui, Color color, int baseValue, int currentValue, int relX, int relY) {
        this.ui = ui;
        this.color = color;
        this.baseValue = baseValue;
        this.currentValue = currentValue;
        this.relX = relX;
        this.relY = relY;
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, false);
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("drivebold.ttf");

            Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont2 = awtFont2.deriveFont(ui.getScy() * 18f); // set font size
            font3 = new TrueTypeFont(awtFont2, true);

        } catch (FontFormatException | IOException e) {
        }

    }

    /**
     * Mise à jour de la jauge avec une nouvelle valeur.
     *
     * @param newValue La nouvelle valeur à donner à la jauge.
     */
    public void update(int newValue) {
        currentValue = newValue;
    }

    /**
     * Dessin de la jauge sur l'interface graphique.
     */
    public void draw() {
        float scx = ui.getScx();
        float scy = ui.getScy();

        Graphics g = new Graphics();

        g.setFont(font3);
        g.setColor(color);
        if (baseValue == 0) {
            g.fillRect(scx * relX, scy * relY, scx * SIZEX, scy * SIZEY);
        } else if (currentValue > 0) {
            g.fillRect(scx * relX, scy * relY, (float) ((float) currentValue / (float) baseValue) * SIZEX * scx, scy * SIZEY);
        } else {
            currentValue = 0;
        }

        g.setColor(Color.black);

        g.drawString(currentValue + "/" + baseValue, scx * relX, scy * relY);
        g.setColor(Color.white);

        g.drawString(currentValue + "/" + baseValue, scx * relX, scy * (relY + 2));

    }

}
