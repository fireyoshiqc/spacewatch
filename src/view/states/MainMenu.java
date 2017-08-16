/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.states;

import java.awt.Toolkit;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Menu qui accueille le joueur lorsqu'il démarre le jeu Donne 4 options, jouer,
 * options, crédits, quitter
 *
 * @author 1331037
 */
public class MainMenu extends BasicGameState {

    private Image title, bg;
    private Input input;
    public static float /**
             * Ratio en x à appliquer aux éléments graphiques
             *
             */
            scx,
            /**
             * Ratio en y à appliquer aux éléments graphiques
             */
            /**
             *
             */
            scy;
    private float bgx;
    private MouseOverArea playButton, multiButton, optionButton, creditButton, exitButton;
    private boolean isOver = false;
    private boolean hasPlayed = false;
    public static Music theme;
    private Sound hover;
    private Sound select;

    /**
     * Fonction qui retourne l'ID du GameState
     *
     * @return ID du GameState
     */
    @Override
    public int getID() {
        return 1;
    }

    /**
     * Initialise les variables de la classe pour future utilisation Les images,
     * les sons ainsi que les boutons
     *
     * @param container Ce qui contient les éléments du jeu
     * @param game Le jeu en tant que tel
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        theme = new Music("MainMenu.ogg");
        hover = new Sound("MenuHover.ogg");
        select = new Sound("MenuSelect.ogg");

        title = new Image("title_screen.png");
        bg = new Image("stars.png");
        input = new Input(Toolkit.getDefaultToolkit().getScreenSize().height);

        scx = (float) container.getWidth() / 1920;
        scy = (float) container.getHeight() / 1080;
        playButton = new MouseOverArea(container, null, (int) (50 * scx), (int) (825 * scy), (int) (96 * scx), (int) (24 * scy));
        optionButton = new MouseOverArea(container, null, (int) (50 * scx), (int) (850 * scy), (int) (152 * scx), (int) (24 * scy));
        creditButton = new MouseOverArea(container, null, (int) (50 * scx), (int) (875 * scy), (int) (146 * scx), (int) (24 * scy));
        exitButton = new MouseOverArea(container, null, (int) (50 * scx), (int) (900 * scy), (int) (76 * scx), (int) (24 * scy));

    }

    /**
     * Calcule si le joueur a effectuer une action dans l'interface
     *
     * @param container Ce qui contient les éléments du jeu
     * @param game Le jeu en tant que tel
     * @param delta Ratio sur 60 IPS
     * @throws SlickException
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (OptionsMenu.getLastState() != this.getID()) {
            OptionsMenu.setLastState(this.getID());
        }
        if (!theme.playing()) {
            theme.play(1f, 0.2f);
        }
        input.setScale(scx, scy);

        if (bgx <= -1024) {
            bgx = 0;
        } else {
            bgx -= 0.3f * ((float) delta / 17);
        }

        if (isOver && !hasPlayed) {
            hover.play();
            hasPlayed = true;
        }
        if (exitButton.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            select.play();
            System.exit(0);
        }
        if (playButton.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            select.play();
            MainMenu.theme.fade(1000, 0, true);
            container.setMouseGrabbed(true);
            game.enterState(3, new FadeOutTransition(), new FadeInTransition());
        }
        if (optionButton.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            select.play();
            game.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }
    }

    /**
     * S'occupe de dessiner les éléments graphiques Les boutons, les différents
     * States selon ce que le joueur sélectionne
     *
     * @param container Ce qui contient les éléments du jeu
     * @param game Le jeu en tant que tel
     * @param g L'élément graphics qui va dessiner les éléments graphiques
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        g.scale(scx, scy);
        for (int x = 0; x <= (int) scx * 1920 + 2048; x += 1024) {
            for (int y = 0; y <= (int) scy * 1080 + 2048; y += 1024) {
                bg.draw(bgx + x, y);
            }
        }
        title.draw();
        g.setFont(Opening.font2);
        g.setColor(Color.white);
        isOver = false;

        if (playButton.isMouseOver()) {
            g.setColor(Color.cyan);
            isOver = true;
        }

        g.drawString("Play", 50, 825);
        g.setColor(Color.white);
        if (optionButton.isMouseOver()) {
            g.setColor(Color.cyan);
            isOver = true;
        }

        g.drawString("Options", 50, 850);
        g.setColor(Color.white);
        if (creditButton.isMouseOver()) {
            g.setColor(Color.cyan);
            isOver = true;
        }

        g.drawString("Credits", 50, 875);
        g.setColor(Color.white);
        if (exitButton.isMouseOver()) {
            g.setColor(Color.cyan);
            isOver = true;
        }

        if (!isOver) {
            hasPlayed = false;
        }
        g.drawString("Exit", 50, 900);

        g.resetTransform();

    }

    /**
     * Calcule le scale à appliquer aux méthodes draw pour que tout rentre dans
     * n'importe quelle taille d'écran Mets à jours les MouseOverAreas pour
     * qu'elles suivent les boutons apres le rescale
     *
     * @param container Le gamecontainer pour obtenir la taille de l'écran
     */
    public void rescale(AppGameContainer container) {
        scx = (float) container.getWidth() / 1920;
        scy = (float) container.getHeight() / 1080;
        playButton = new MouseOverArea(container, null, (int) (50 * scx), (int) (825 * scy), (int) (96 * scx), (int) (24 * scy));
        optionButton = new MouseOverArea(container, null, (int) (50 * scx), (int) (850 * scy), (int) (152 * scx), (int) (24 * scy));
        creditButton = new MouseOverArea(container, null, (int) (50 * scx), (int) (875 * scy), (int) (146 * scx), (int) (24 * scy));
        exitButton = new MouseOverArea(container, null, (int) (50 * scx), (int) (900 * scy), (int) (76 * scx), (int) (24 * scy));
    }

}
