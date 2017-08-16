/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.items;

/**
 * Des items beaucoup plus rares!
 * @author 1373360
 */
public class UniqueItem extends Item {

    /**
     * Type d'item.
     */
    public enum Items {

        /**
         * Arme à l'acide.
         */
        PX1,
        /**
         * Fusil.
         */
        M201,
        /**
         * Fusil.
         */
        M211,
        /**
         * Fusil.
         */
        M221,
        /**
         * Fusil.
         */
        M231,
        /**
         * Fusil.
         */
        M241,
        /**
         * Fusil.
         */
        M251,
        /**
         * Fusil.
         */
        M261,
        /**
         * Fusil.
         */
        M271,
        /**
         * Laser.
         */
        ZRA,
        /**
         * Laser.
         */
        ZRB,
        /**
         * Laser.
         */
        ZRC,
        /**
         * Laser.
         */
        ZRD,
        /**
         * Laser.
         */
        ZRE,
        /**
         * Laser.
         */
        ZRF,
        /**
         * Laser.
         */
        ZRG,
        /**
         * Laser.
         */
        ZRH,
        /**
         * Laser.
         */
        ZRX,
        /**
         * Missiles.
         */
        L311,
        /**
         * Missiles.
         */
        L321,
        /**
         * Missiles.
         */
        L331,
        /**
         * Missiles.
         */
        L341,
        /**
         * Missiles.
         */
        L901,
        /**
         * Bouclier.
         */
        WCl;

    }

    Items itemType;

    /**
     * Crée un item unique (exemple une arme).
     *
     * @param itemType Le type d'item.
     * @param x Position X.
     * @param y Position Y.
     */
    public UniqueItem(Items itemType, double x, double y) {
        this.x = x;
        this.y = y;

        this.itemType = itemType;
        switch (itemType) {
            case PX1:
                this.mass = 100;
                break;
            case M201:
                this.mass = 50;
                break;
            case M211:
                this.mass = 100;
                break;
            case M221:
                this.mass = 150;
                break;
            case M231:
                this.mass = 150;
                break;
            case M241:
                this.mass = 300;
                break;
            case M251:
                this.mass = 600;
                break;
            case M261:
                this.mass = 200;
                break;
            case M271:
                this.mass = 400;
                break;
            case ZRA:
                this.mass = 100;
                break;
            case ZRB:
                this.mass = 200;
                break;
            case ZRC:
                this.mass = 300;
                break;
            case ZRD:
                this.mass = 600;
                break;
            case ZRE:
                this.mass = 10000;
                break;
            case ZRF:
                this.mass = 2000;
                break;
            case ZRG:
                this.mass = 5000;
                break;
            case ZRH:
                this.mass = 10000;
                break;
            case ZRX:
                this.mass = 100000;
                break;
            case L311:
                this.mass = 100;
                break;
            case L321:
                this.mass = 200;
                break;
            case L331:
                this.mass = 300;
                break;
            case L341:
                this.mass = 600;
                break;
            case L901:
                this.mass = 1000;
                break;
            case WCl:
                this.mass = 50;
                break;
        }
    }

}
