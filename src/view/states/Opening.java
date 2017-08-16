/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.states;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

/**
 * État de jeu qui s'occupe d'initialiser le programme et de charger les
 * ressources.
 *
 * @author JeanSeb
 */
public class Opening extends BasicGameState {

    private String lastLoaded;
    private TrueTypeFont font;

    /**
     * La police utilisée à travers le jeu.
     */
    public static TrueTypeFont font2;

    /**
     * Initialise l'état de jeu.
     *
     * @param container Conteneur de jeu.
     * @param game Le jeu lui-même.
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, false);
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("Xperia.ttf");

            Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont2 = awtFont2.deriveFont(30f); // set font size
            font2 = new TrueTypeFont(awtFont2, true);

        } catch (FontFormatException | IOException e) {
        }

    }

    /**
     * Mise à jour unique qui calcule un écran de chargement lorsque les
     * ressources du jeu sont chargées dans la mémoire.
     *
     * @param container Conteneur de jeu.
     * @param game Le jeu lui-même.
     * @param delta Ratio sur 60 IPS.
     * @throws SlickException
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        if (LoadingList.get().getRemainingResources() > 0) {
            try {
                DeferredResource nextResource = LoadingList.get().getNext();
                nextResource.load();
                lastLoaded = nextResource.getDescription();

            } catch (IOException ex) {

            }

        } else {
            game.enterState(1, new FadeOutTransition(), new FadeInTransition());

        }
    }

    /**
     * Visualisation graphique de la progression du chargement des ressources.
     *
     * @param container Conteneur de jeu.
     * @param game Le jeu lui-même.
     * @param g Le contexte graphique pour dessiner.
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setFont(font2);
        g.drawString("Loading resources...", container.getWidth() / 2 - 200, container.getHeight() / 2 - 50);
        g.fillRect(container.getWidth() / 2 - 200, container.getHeight() / 2 - 10, ((float) (LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources()) / (float) LoadingList.get().getTotalResources()) * 400, 20);
        if (lastLoaded != null) {
            g.drawString("Loaded: " + lastLoaded, container.getWidth() / 2 - 200, container.getHeight() / 2 + 50);
        }
    }

    /**
     * Retourne l'ID de l'état de jeu.
     *
     * @return l'ID de l'état.
     */
    @Override
    public int getID() {
        return 0;
    }
}
