/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.items;

import gameComponents.entities.ships.PlayerShip;
import gameComponents.entities.ships.Ship;
import java.util.ArrayList;

/**
 * Table de récompenses. Yé!
 * @author 1331037
 */
public class DropTable {

    private ArrayList<Item> listItem = new ArrayList<>();
    private ItemSystem its;

    /**
     * Crée une nouvelle table de récompenses pour un vaisseau.
     *
     * @param its Système d'items.
     */
    public DropTable(ItemSystem its) {
        this.its = its;
    }

    /**
     * Ajoute un item à la table, avec une certaine chance de drop.
     *
     * @param item Item à ajouter.
     * @param chance Chance de drop (0-100).
     */
    public void addItem(Item item, float chance) {
        item.setChance(chance);
        listItem.add(item);
    }

    /**
     * Drop un ou des items afin qu'il puissent être ramassés par le joueur.
     *
     * @param ship Le vaisseau qui drop l'item.
     */
    public void dropItem(Ship ship) {
        if (!(ship instanceof PlayerShip)) {
            float dropChoice = (float) (Math.random() * 100);
            Item secondaryDrop = null;
            for (Item item : listItem) {
                if (item.getChance() == 100) {
                    item.setX(ship.getX() + (Math.random() - 0.5) * 300);
                    item.setY(ship.getY() + (Math.random() - 0.5) * 300);
                    its.addItem(item);

                } else if (item.getChance() > dropChoice) {
                    secondaryDrop = item;
                    secondaryDrop.setX(ship.getX() + (Math.random() - 0.5) * 300);
                    secondaryDrop.setY(ship.getY() + (Math.random() - 0.5) * 300);
                    its.addItem(secondaryDrop);

                }
            }
        }

    }

}
