/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.hud;

import gameComponents.entities.ships.PlayerShip;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * La magnifique interface graphique du jeu, qui affiche en chiffres et en jauges l'état du vaisseau du joueur.
 * @author Félix
 */
public class BaseUI {

    private Image sprite;
    private ArrayList<UIBar> uiBars;
    private UIBar hp, armor, shield, fuel, ammo;
    private PlayerShip ship;
    private float scx;
    private float scy;
    private RadarUI radar;

    /**
     * Crée une interface graphique pour donner les caractéristiques du
     * vaisseau.
     *
     * @param container Le conteneur de jeu.
     * @param ship Le vaisseau du joueur.
     * @throws SlickException
     */
    public BaseUI(GameContainer container, PlayerShip ship) throws SlickException {
        sprite = new Image("Base_UI.png");
        scx = (float) container.getWidth() / 1920;
        scy = (float) container.getHeight() / 1080;

        sprite = sprite.getScaledCopy((int) (scx * 1920), (int) (scy * 1080));
        this.ship = ship;
        uiBars = new ArrayList<>();
        hp = new UIBar(this, Color.red, (int) ship.getMaxHp(), (int) ship.getCurrentHp(), 652, 920);

        uiBars.add(hp);
        armor = new UIBar(this, Color.lightGray, (int) ship.getMaxArmor(), (int) ship.getCurrentArmor(), 652, 950);
        uiBars.add(armor);
        shield = new UIBar(this, Color.cyan, (int) ship.getMaxShield(), (int) ship.getCurrentShield(), 652, 980);
        uiBars.add(shield);
        fuel = new UIBar(this, Color.yellow, (int) ship.getMaxFuel(), (int) ship.getFuel(), 652, 1010);
        uiBars.add(fuel);
        ammo = new UIBar(this, Color.green, ship.getMaxBullets(), ship.getBullets(), 652, 1040);
        uiBars.add(ammo);
        if (ship.getRadar() != null) {
            radar = new RadarUI(this, 1520, 680, ship.getRadar());
        }

    }

    /**
     * Mise à jour de l'interface.
     */
    public void update() {
        hp.update((int) ship.getCurrentHp());
        armor.update((int) ship.getCurrentArmor());
        shield.update((int) ship.getCurrentShield());
        fuel.update((int) ship.getFuel());
        ammo.update(ship.getBullets());

    }

    /**
     * Dessin de l'interface et de ce qu'elle comprend (barres et radar).
     */
    public void draw() {
        sprite.draw();

        for (UIBar bar : uiBars) {

            bar.draw();
        }
        if (radar != null) {
            radar.draw();
        }
    }

    /**
     *
     * @return Le ratio de taille en X.
     */
    public float getScx() {
        return scx;
    }

    /**
     *
     * @return Le ratio de taille en Y.
     */
    public float getScy() {
        return scy;
    }

}
