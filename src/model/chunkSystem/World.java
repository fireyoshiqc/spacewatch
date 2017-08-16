/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chunkSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Le monde de jeu, le coeur de la génération de l'espace.
 * @author 1331037
 */
public class World implements Serializable {

    private long seed;
    private long a = 1664525;
    private long c = 1013904223;
    private long m = (long) Math.pow(2, 32);
    private long x;

    private ArrayList<Long> chunkList;
    private HashSet<Chunk> activeChunks;

    /**
     * Crée un nouveau monde de jeu.
     *
     * @param seed Graine pseudo-aléatoire.
     * @param ck Générateur de chunks.
     */
    public World(long seed, ChunkGenerator ck) {
        chunkList = new ArrayList<>();
        activeChunks = new HashSet<>();
        this.seed = seed;
        x = (a * seed + c) % m;
        for (int i = 0; i < 1000000; i++) {
            x = (a * x + c) % m;

            chunkList.add(x);
        }
    }

    /**
     *
     * @return La liste de chunks présents dans le monde.
     */
    public ArrayList<Long> getChunkList() {
        return chunkList;
    }

    /**
     *
     * @return Le set des chunks actifs (un seul exemplaire de chaque).
     */
    public HashSet<Chunk> getActiveChunks() {
        return activeChunks;
    }

    /**
     * Mise à jour. Met à jour tous les chunks actifs.
     *
     * @param delta Ratio sur 60 IPS.
     */
    public void update(float delta) {
        for (Chunk ck : activeChunks) {
            ck.update(delta);
        }
    }

    /**
     * Dessin des chunks actifs.
     */
    public void draw() {
        for (Chunk ck : activeChunks) {
            ck.draw();

        }
    }

}
