/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.items;

import gameComponents.entities.ships.PlayerShip;
import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;

/**
 * Système de gestion des items présents physiquement dans le jeu, et s'occupant de leur collecte.
 * @author 1331037
 */
public class ItemSystem {

    private ArrayList<Item> itemsList;

    /**
     * Crée un système d'items. Gère les items lâchés dans le monde.
     */
    public ItemSystem() {
        itemsList = new ArrayList<>();
    }

    /**
     * Ajoute un item au système.
     *
     * @param item L'item à ajouter.
     */
    public void addItem(Item item) {
        itemsList.add(item);
    }

    /**
     * Mise à jour.
     *
     * @param ship Le vaisseau du joueur.
     */
    public void update(PlayerShip ship) {
        ArrayList<Item> collectedItems = new ArrayList();
        for (Item item : itemsList) {
            if (item.getPos().intersects(ship.getPos())) {
                ship.getInventory().addItem(item);
                collectedItems.add(item);
            }
        }
        itemsList.removeAll(collectedItems);

    }

    /**
     * Dessin des items.
     *
     * @param worldClip Le rectangle délimitant la zone de dessin.
     */
    public void draw(Rectangle worldClip) {
        ArrayList<Item> toRemove = new ArrayList<>();

        for (Item item : itemsList) {
            if (item.getX() > worldClip.getX() && item.getX() < worldClip.getMaxX()
                    && item.getY() > worldClip.getY() && item.getY() < worldClip.getMaxY()) {
                item.draw();

            } else {
                toRemove.add(item);

            }

        }

        itemsList.removeAll(toRemove);

    }

}
