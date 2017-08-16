/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chunkSystem;

import gameComponents.entities.ships.PlayerShip;
import model.chunkSystem.Chunk.Type;
import org.newdawn.slick.SlickException;

/**
 * Un générateur de chunks!
 * @author 1331037
 */
public class ChunkGenerator {

    private World world;
    private long seed;
    private Chunk currentChunk;

    /**
     * Crée un générateur de chunks, et la graine pseudo-aléatoire ainsi que le
     * monde qui y sont associés.
     */
    public ChunkGenerator() {
        seed = System.currentTimeMillis();

        world = new World(seed, this);

    }

    /**
     * Transforme la graine pseudo-aléatoire en chiffres en une chaine de
     * caractères.
     *
     * @param seed
     * @return
     */
    public String transformSeed(long seed) {
        String strSeed = "" + seed;
        while (strSeed.length() < 10) {
            strSeed = "0" + strSeed;
        }
        return strSeed;
    }

    /**
     * Génère un nouveau type de système.
     *
     * @param seed Graine pseudo-aléatoire.
     * @param position Position du chunk.
     * @return Le nouveau chunk d'un certain type généré.
     * @throws SlickException
     */
    public Chunk generateSystemType(String seed, long position) throws SlickException {
        Type type = null;

        int subseed = Integer.parseInt(seed.substring(3, 5));

        if (subseed < 60) {
            type = Type.SOLAR_SYSTEM;

        } else if (subseed < 80) {
            type = Type.ASTEROID;
        } else if (subseed < 90) {
            type = Type.NEBULA;
        } else if (subseed < 95) {
            type = Type.BLACKHOLE;
        } else if (subseed < 100) {
            type = Type.KNARAKU;
        }

        switch (type) {
            case SOLAR_SYSTEM:
                currentChunk = new SolarSystem(seed, position, type);
                break;
            case BLACKHOLE:
                currentChunk = new Blackhole(seed, position, type);
                break;
            case KNARAKU:
                currentChunk = new KnarakuColony(seed, position, type);
                break;
            case ASTEROID:
                currentChunk = new AsteroidField(seed, position, type);
                break;
            case NEBULA:
                currentChunk = new Nebula(seed, position, type);
                break;
            default:
                break;

        }

        this.world.getActiveChunks().add(currentChunk);

        return currentChunk;
    }

    /**
     * Charge un chunk déjà créé et enregistré en mémoire, si existant.
     *
     * @param posX La position en X du chunk à charger.
     * @param posY La position en Y du chunk à charger.
     * @param newPos La position du chunk évalué pour être chargé.
     * @throws SlickException
     */
    public void loadChunk(int posX, int posY, int newPos) throws SlickException {
        boolean chunkExists = false;
        for (Chunk ck : this.world.getActiveChunks()) {
            if (ck.getPosX() == posX && ck.getPosY() == posY) {
                currentChunk = ck;
                chunkExists = true;
            }
        }
        if (!chunkExists) {
            currentChunk = this.generateSystemType(this.transformSeed(this.getWorld().getChunkList().get(newPos)), newPos);
        }

    }

    /**
     * Vérifie la position du joueur et génère de nouveaux chunks selon sa
     * position.
     *
     * @param ps Vaisseau du joueur.
     * @throws SlickException
     */
    public void checkPlayerPosition(PlayerShip ps) throws SlickException {
        if (ps.getX() < currentChunk.getPosX() * currentChunk.SIZEX) {
            this.loadChunk((int) currentChunk.getPosX() - 1,
                    (int) currentChunk.getPosY(), (int) currentChunk.getArrayPos() - 1);

        } else if (ps.getX() > (currentChunk.getPosX() + 1) * currentChunk.SIZEX) {
            this.loadChunk((int) currentChunk.getPosX() + 1,
                    (int) currentChunk.getPosY(), (int) currentChunk.getArrayPos() + 1);

        } else if (ps.getY() < currentChunk.getPosY() * currentChunk.SIZEY) {
            this.loadChunk((int) currentChunk.getPosX(),
                    (int) currentChunk.getPosY() - 1, (int) currentChunk.getArrayPos() - 1000);

        } else if (ps.getY() > (currentChunk.getPosY() + 1) * currentChunk.SIZEY) {
            this.loadChunk((int) currentChunk.getPosX(),
                    (int) currentChunk.getPosY() + 1, (int) currentChunk.getArrayPos() + 1000);

        }

    }

    /**
     *
     * @return Le monde associé au générateur de chunks.
     */
    public World getWorld() {
        return world;
    }

    /**
     *
     * @return Le chunk dans lequel le joueur se trouve actuellement.
     */
    public Chunk getCurrentChunk() {
        return currentChunk;
    }

}
