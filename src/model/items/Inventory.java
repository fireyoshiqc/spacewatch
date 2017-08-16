/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.items;

import java.util.ArrayList;

/**
 * Un inventaire plus ou moins fonctionnel à la remise du projet.
 * @author 1373360
 */
public class Inventory {

    private ArrayList<Item> listItem = new ArrayList<>();
    private float size;
    private float mass;

    /**
     * Crée un inventaire pour un vaisseau.
     *
     * @param size Masse maximale.
     * @param mass Masse actuelle.
     */
    public Inventory(float size, float mass) {

        this.size = size;
        this.mass = mass;

    }

    /**
     * Ajoute un item à l'inventaire.
     *
     * @param item Item à ajouter.
     */
    public void addItem(Item item) {
        boolean itemExists = false;
        if ((mass += item.getMass()) > size) {

        } else {
            for (Item foundItem : listItem) {

                if (foundItem instanceof StackableItem) {
                    StackableItem sti = (StackableItem) foundItem;
                    StackableItem st2 = (StackableItem) item;
                    if (sti.getResource().equals(st2.getResource())) {
                        sti.setMass(sti.getMass() + item.getMass());
                        itemExists = true;
                    }

                }

            }
            if (!itemExists) {
                listItem.add(item);
            }

//            mass += item.getMass();
        }
    }

    /**
     * Enlève un item de l'inventaire.
     *
     * @param item Item à enlever.
     */
    public void removeItem(Item item) {
        if ((mass -= item.getMass()) < 0) {

        } else {
            listItem.remove(item);
            mass -= item.getMass();
        }
    }

    /**
     *
     * @return La liste des items dans l'inventaire.
     */
    public ArrayList<Item> getListItem() {
        return listItem;
    }

}
