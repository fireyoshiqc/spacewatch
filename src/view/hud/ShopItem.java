/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.hud;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.MouseOverArea;
import view.states.MainMenu;
import view.states.Opening;

/**
 * Item graphique pour l'interface du magasin.
 * @author 1332327
 */
public class ShopItem {

    private Image image;
    private String name;
    private Image logo;
    private int cost;
    private MouseOverArea clickArea;
    private float scx = MainMenu.scx;
    private float scy = MainMenu.scy;

    /**
     * Crée un nouvel item dans le magasin (shop).
     *
     * @param shipImage Image à utiliser pour le vaisseau ou le module.
     * @param logo Logo de la compagnie manufacturière.
     * @param name Nom du vaisseau ou du module.
     * @param cost Coût du vaisseau ou du module.
     * @param gc Conteneur de jeu.
     */
    public ShopItem(Image shipImage, Image logo, String name, int cost, GameContainer gc) {
        this.cost = cost;
        this.image = shipImage;
        this.name = name;
        this.logo = logo;
        clickArea = new MouseOverArea(gc, image, 0, 0, (int) (scx * 100), (int) (scy * 100));
    }

    /**
     * Dessine l'item sur l'interface du magasin.
     *
     * @param x Position X.
     * @param y Position Y.
     */
    public void draw(int x, int y) {
        clickArea.setX((int) (scx * x));
        clickArea.setY((int) (scy * y));
        Graphics g = new Graphics();
        g.setFont(Opening.font2);
        g.drawImage(image.getScaledCopy(99, 99), x, y);
        g.drawImage(logo.getScaledCopy(144, 55), x + 100, y);
        g.drawString(name + "  " + cost + "S.C.", x + 100, y + 60);
    }

    /**
     *
     * @return La zone cliquable correspondant à l'item sélectionné.
     */
    public MouseOverArea getClickArea() {
        return clickArea;
    }

    /**
     *
     * @return Le coût de l'item sélectionné.
     */
    public int getCost() {
        return cost;
    }

}
