/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chunkSystem;

import gameComponents.entities.PhysicsObject;
import java.util.ArrayList;
import org.newdawn.slick.SlickException;

/**
 * Classe abstraite qui englobe tous les systèmes présents dans l'espace du jeu.
 * @author 1331037
 */
public abstract class Chunk {

    /**
     * Taille d'un chunk en pixels, en X.
     */
    protected final double SIZEX = 1000000;

    /**
     * Taille d'un chunk en pixels, en Y.
     */
    protected final double SIZEY = 1000000;

    /**
     * Position dans la liste des chunks.
     */
    protected long arrayPos;

    /**
     * Position en X dans la liste.
     */
    protected long posX;

    /**
     * Position en Y dans la liste.
     */
    protected long posY;

    /**
     * Graine pseudo-aléatoire déterminant la génération du chunk.
     */
    protected String seed;

    /**
     * Type de chunk.
     */
    protected Type type;

    /**
     * Contenu du chunk.
     */
    protected ArrayList<PhysicsObject> contents;

    /**
     * Type de chunk.
     */
    public enum Type {

        /**
         * Système solaire.
         */
        SOLAR_SYSTEM,
        /**
         * Trou noir.
         */
        BLACKHOLE,
        /**
         * Nébula.
         */
        NEBULA,
        /**
         * Colonie de Knaraku.
         */
        KNARAKU,
        /**
         * Champ d'astéroides.
         */
        ASTEROID;
    }

    /**
     * Constructeur vide.
     */
    public Chunk() {
    }

    /**
     * Crée un chunk.
     *
     * @param seed La graine pseudo-aléatoire à utiliser.
     * @param position La position du chunk.
     * @param type Le type de chunk.
     */
    public Chunk(String seed, long position, Type type) {
        arrayPos = position;
        this.seed = seed;
        this.type = type;

        posX = (position % 1000) - 499;
        posY = (long) Math.floor(position / 1000) - 499;
        contents = new ArrayList<>();

    }

    /**
     * Mise à jour du contenu du chunk.
     *
     * @param delta
     */
    public abstract void update(float delta);

    /**
     * Dessin du contenu du chunk.
     */
    public void draw() {
        if (!contents.isEmpty()) {
            for (PhysicsObject obj1 : contents) {
                obj1.draw();

            }
        }
    }

    /**
     *
     * @return La graine pseudo-aléatoire.
     */
    public String getSeed() {
        return seed;
    }

    /**
     *
     * @return Le type de chunk, en String.
     */
    public String getType() {
        return type.toString();
    }

    /**
     *
     * @return La position en X du chunk.
     */
    public long getPosX() {
        return posX;
    }

    /**
     *
     * @return La position en Y du chunk;
     */
    public long getPosY() {
        return posY;
    }

    /**
     *
     * @return La position dans la liste du chunk.
     */
    public long getArrayPos() {
        return arrayPos;

    }

    /**
     *
     * @return La taille d'un chunk en X.
     */
    public double getSIZEX() {
        return SIZEX;
    }

    /**
     *
     * @return La taille d'un chunk en Y.
     */
    public double getSIZEY() {
        return SIZEY;
    }

    /**
     *
     * @return Le contenu du chunk.
     */
    public ArrayList<PhysicsObject> getContents() {
        return contents;
    }

    /**
     * Génère un nouveau système selon le type de chunk.
     *
     * @throws SlickException
     */
    protected abstract void generateSystem() throws SlickException;

}
