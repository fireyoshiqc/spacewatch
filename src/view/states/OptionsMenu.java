/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.states;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.FontUtils;

/**
 * Menu des options où le joueur peux modifier les paramêtres vidéos et de sons
 *
 * @author 1331037
 */
public class OptionsMenu extends BasicGameState {

    private Image options, tab, tabMouseOver;
    private Input input;
    private boolean isOver = false, hasPlayed = false;
    private Sound hover;
    private Sound select;
    private float selectedMusicVolume = 10, selectedSoundVolume = 10;
    private float scx;
    private float scy;
    private static int lastState;
    private Color textColor;
    private int selectedtab = 1;
    private MouseOverArea videotab, audiotab, backbutton;
    private MouseOverArea displayMode, res, antialiasing, anistropic, vSync, framerateCap, bloom, motionBlur, particles;
    private MouseOverArea music, sounds;

    private ArrayList<String> displayModes, resolutions, antialiasingOptions, anistropicOptions, framerateOptions, particlesOptions;
    private ArrayList<String> musicVolumes, soundVolumes;
    private boolean setVSync = true, setBloom = true, setBlur = true, isMouseButtonDown = false;
    private int selectedDisplayMode = 0, selectedResolution = 0, selectedAntialiasing = 0, selectedAnistropic = 0, selectedFPS = 0, selectedParticles = 0;

    private MainMenu menu;

    /**
     * Fonction qui retourne l'ID du GameState
     *
     * @return ID du GameState
     */
    @Override
    public int getID() {
        return 2;
    }

    /**
     * Initialise les variables de la classe pour future utilisation Les images,
     * les sons, les options ainsi que les boutons
     *
     * @param container Ce qui contient les éléments du jeu
     * @param game Le jeu en tant que tel
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        tab = new Image("Menu_Tab.png");
        tabMouseOver = new Image("menu_tab_2.png");
        textColor = Color.white;
        hover = new Sound("MenuHover.ogg");
        select = new Sound("MenuSelect.ogg");

        initialiseLists(container);

        options = new Image("OptionMenu_Back.png");
        input = new Input(Toolkit.getDefaultToolkit().getScreenSize().height);

        scx = (float) container.getWidth() / 1920;
        scy = (float) container.getHeight() / 1080;

        videotab = new MouseOverArea(container, null, (int) (321 * scx), (int) (70 * scy), (int) (426 * scx), (int) (106 * scy));
        displayMode = new MouseOverArea(container, null, (int) (120 * scx), (int) (324 * scy), (int) (780 * scx), (int) (24 * scy));
        res = new MouseOverArea(container, null, (int) (120 * scx), (int) (420 * scy), (int) (780 * scx), (int) (24 * scy));
        antialiasing = new MouseOverArea(container, null, (int) (120 * scx), (int) (516 * scy), (int) (780 * scx), (int) (24 * scy));
        anistropic = new MouseOverArea(container, null, (int) (120 * scx), (int) (612 * scy), (int) (780 * scx), (int) (24 * scy));
        vSync = new MouseOverArea(container, null, (int) (120 * scx), (int) (708 * scy), (int) (780 * scx), (int) (24 * scy));
        framerateCap = new MouseOverArea(container, null, (int) (1020 * scx), (int) (324 * scy), (int) (780 * scx), (int) (24 * scy));
        bloom = new MouseOverArea(container, null, (int) (1020 * scx), (int) (420 * scy), (int) (780 * scx), (int) (24 * scy));
        motionBlur = new MouseOverArea(container, null, (int) (1020 * scx), (int) (516 * scy), (int) (780 * scx), (int) (24 * scy));
        particles = new MouseOverArea(container, null, (int) (1020 * scx), (int) (612 * scy), (int) (780 * scx), (int) (24 * scy));

        audiotab = new MouseOverArea(container, null, (int) (1173 * scx), (int) (70 * scy), (int) (426 * scx), (int) (106 * scy));
        music = new MouseOverArea(container, null, (int) (120 * scx), (int) (516 * scy), (int) (780 * scx), (int) (24 * scy));
        sounds = new MouseOverArea(container, null, (int) (1020 * scx), (int) (516 * scy), (int) (780 * scx), (int) (24 * scy));

        backbutton = new MouseOverArea(container, null, (int) (90 * scx), (int) (976 * scy), (int) (96 * scx), (int) (24 * scy));
    }

    /**
     * Calcule si le joueur a effectuer une action dans l'interface Change les
     * paramètres lorsqu'on quitte le menu Change l'onglet actif si le joueur le
     * désir Gère les changement d'options
     *
     * @param container Ce qui contient les éléments du jeu
     * @param game Le jeu en tant que tel
     * @param delta Ratio sur 60 IPS
     * @throws SlickException
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        input.setScale(scx, scy);
        if (videotab.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
            selectedtab = 1;
            select.play();
        }
        if (audiotab.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
            selectedtab = 2;
            select.play();
        }
        if (backbutton.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            select.play();

            container.setVSync(setVSync);
            if (antialiasingOptions.get(selectedAntialiasing).equalsIgnoreCase("Off")) {
                container.setMultiSample(0);
            } else {
                container.setMultiSample(Integer.parseInt(antialiasingOptions.get(selectedAntialiasing).substring(0, 1)));
            }
            if (framerateOptions.get(selectedFPS).equalsIgnoreCase("Unlimited")) {
                container.setTargetFrameRate(900);
            } else {

                container.setTargetFrameRate(Integer.parseInt(framerateOptions.get(selectedFPS).substring(0, 3).trim()));
            }
            container.setSoundVolume((float) (selectedSoundVolume / 10));
            container.setMusicVolume((float) (selectedMusicVolume / 10));
            AppGameContainer gc = (AppGameContainer) container;
            gc.setDisplayMode(Integer.parseInt(resolutions.get(selectedResolution)
                    .substring(0, resolutions.get(selectedResolution).indexOf("x"))),
                    Integer.parseInt(resolutions.get(selectedResolution)
                            .substring(resolutions.get(selectedResolution).indexOf("x") + 1,
                                    resolutions.get(selectedResolution).length())), container.isFullscreen());

            MainMenu mainMenu = (MainMenu) game.getState(1);

            mainMenu.rescale(gc);
            Shop shop = (Shop) game.getState(4);
            shop.rescale(gc);
            MainGame maingame = (MainGame) game.getState(3);

            if (setBloom) {
                maingame.getPsy().setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
            } else {
                maingame.getPsy().setBlendingMode(ParticleSystem.BLEND_COMBINE);
            }
            maingame.rescale(gc);

            switch (displayModes.get(selectedDisplayMode).substring(0, 1)) {
                case "W":

                case "B":
                    container.setFullscreen(false);
                    break;
                case "F":
                    container.setFullscreen(true);
                    break;
            }

            game.enterState(lastState, new FadeOutTransition(), new FadeInTransition());
            this.rescale(container);

        }

        if (displayMode.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
            if (selectedDisplayMode == displayModes.size() - 1) {
                selectedDisplayMode = 0;
            } else {
                selectedDisplayMode++;
            }
            select.play();
        }
        if (selectedtab == 1) {
            if (res.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                if (selectedResolution == resolutions.size() - 1) {
                    selectedResolution = 0;
                } else {
                    selectedResolution++;
                }
                select.play();
            }
            if (antialiasing.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                if (selectedAntialiasing == antialiasingOptions.size() - 1) {
                    selectedAntialiasing = 0;
                } else {
                    selectedAntialiasing++;
                }
                select.play();
            }
            if (anistropic.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                if (selectedAnistropic == anistropicOptions.size() - 1) {
                    selectedAnistropic = 0;
                } else {
                    selectedAnistropic++;
                }
                select.play();
            }
            if (vSync.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                setVSync = !setVSync;
                select.play();
            }
            if (framerateCap.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                if (selectedFPS == framerateOptions.size() - 1) {
                    selectedFPS = 0;
                } else {
                    selectedFPS++;
                }
                select.play();
            }
            if (bloom.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                setBloom = !setBloom;
                select.play();
            }
            if (motionBlur.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                setBlur = !setBlur;
                select.play();
            }
            if (particles.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                if (selectedParticles == particlesOptions.size() - 1) {
                    selectedParticles = 0;
                } else {
                    selectedParticles++;
                }
                select.play();
            }
        }
        if (selectedtab == 2) {
            if (music.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                if (selectedMusicVolume == musicVolumes.size() - 1) {
                    selectedMusicVolume = 0;
                } else {
                    selectedMusicVolume++;
                }
                select.play();
            }
            if (sounds.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                if (selectedSoundVolume == soundVolumes.size() - 1) {
                    selectedSoundVolume = 0;
                } else {
                    selectedSoundVolume++;
                }
                select.play();
            }

        }
        if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            isMouseButtonDown = true;
        }
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            isMouseButtonDown = false;
        }
        if (isOver && !hasPlayed) {
            hover.play();
            hasPlayed = true;
        }
    }

    /**
     * S'occupe de dessiner les éléments graphiques Les boutons, les différents
     * écrans dépendament de ce que le joueur sélectionne
     *
     * @param container Ce qui contient les éléments du jeu
     * @param game Le jeu en tant que tel
     * @param g L'élément graphics qui va dessiner les éléments graphiques
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        g.scale(scx, scy);
        options.draw();
        g.setFont(Opening.font2);
        isOver = false;

        textColor = Color.white;
        if (backbutton.isMouseOver()) {
            textColor = Color.cyan;
            isOver = true;
        }
        g.setColor(textColor);
        g.drawString("Back", 90, 976);

        textColor = Color.white;
        if (videotab.isMouseOver() && selectedtab != 1) {
            tabMouseOver.draw(321, 70);
            textColor = Color.cyan;
            isOver = true;
        }
        g.setColor(textColor);
        g.drawString("Video", 473, 106);

        textColor = Color.white;
        if (audiotab.isMouseOver() && selectedtab != 2) {
            tabMouseOver.draw(1173, 70);
            textColor = Color.cyan;
            isOver = true;
        }
        g.setColor(textColor);
        g.drawString("Audio", 1325, 106);

        switch (selectedtab) {
            case 1:
                tab.draw(321, 70);
                textColor = Color.white;
                if (displayMode.isMouseOver()) {
                    textColor = Color.cyan;
                    isOver = true;
                }
                g.setColor(textColor);
                g.drawString("Display Mode", 120, 324);
                FontUtils.drawRight(Opening.font2, displayModes.get(selectedDisplayMode), 900, 324, 0, textColor);

                textColor = Color.white;
                if (res.isMouseOver()) {
                    textColor = Color.cyan;

                }
                g.setColor(textColor);
                g.drawString("Resolution", 120, 420);
                FontUtils.drawRight(Opening.font2, resolutions.get(selectedResolution), 900, 420, 0, textColor);

                textColor = Color.white;
                if (antialiasing.isMouseOver()) {
                    textColor = Color.cyan;
                    isOver = true;
                }
                g.setColor(textColor);
                g.drawString("Antialiasing", 120, 516);
                FontUtils.drawRight(Opening.font2, antialiasingOptions.get(selectedAntialiasing), 900, 516, 0, textColor);

                textColor = Color.white;
                if (anistropic.isMouseOver()) {
                    textColor = Color.cyan;
                    isOver = true;
                }
                g.setColor(textColor);
                g.drawString("Anistropic Filtering", 120, 612);
                FontUtils.drawRight(Opening.font2, anistropicOptions.get(selectedAnistropic), 900, 612, 0, textColor);

                textColor = Color.white;
                if (vSync.isMouseOver()) {
                    textColor = Color.cyan;
                    isOver = true;
                }
                g.setColor(textColor);
                g.drawString("Vertical Sync", 120, 708);
                FontUtils.drawRight(Opening.font2, setVSync + "", 900, 708, 0, textColor);

                textColor = Color.white;
                if (framerateCap.isMouseOver()) {
                    textColor = Color.cyan;
                    isOver = true;
                }
                g.setColor(textColor);
                g.drawString("Max Framerate", 1020, 324);
                FontUtils.drawRight(Opening.font2, framerateOptions.get(selectedFPS), 1800, 324, 0, textColor);

                textColor = Color.white;
                if (bloom.isMouseOver()) {
                    textColor = Color.cyan;
                    isOver = true;
                }
                g.setColor(textColor);
                g.drawString("Bloom", 1020, 420);
                FontUtils.drawRight(Opening.font2, setBloom + "", 1800, 420, 0, textColor);

                textColor = Color.white;
                if (motionBlur.isMouseOver()) {
                    textColor = Color.cyan;
                    isOver = true;
                }
                g.setColor(textColor);
                g.drawString("Motion Blur", 1020, 516);
                FontUtils.drawRight(Opening.font2, setBlur + "", 1800, 516, 0, textColor);

                textColor = Color.white;
                if (particles.isMouseOver()) {
                    textColor = Color.cyan;
                    isOver = true;
                }
                g.setColor(textColor);
                g.drawString("Particles", 1020, 612);
                FontUtils.drawRight(Opening.font2, particlesOptions.get(selectedParticles), 1800, 612, 0, textColor);

                break;
            case 2:
                tab.draw(1173, 70);

                textColor = Color.white;
                if (music.isMouseOver()) {
                    textColor = Color.cyan;
                    isOver = true;
                }
                g.setColor(textColor);
                g.drawString("Music Volume", 120, 516);
                FontUtils.drawRight(Opening.font2, musicVolumes.get((int) selectedMusicVolume), 900, 516, 0, textColor);

                textColor = Color.white;
                if (sounds.isMouseOver()) {
                    textColor = Color.cyan;
                    isOver = true;
                }
                g.setColor(textColor);
                g.drawString("Sound Volume", 1020, 516);
                FontUtils.drawRight(Opening.font2, soundVolumes.get((int) selectedSoundVolume), 1800, 516, 0, textColor);

                break;
        }
        if (!isOver) {
            hasPlayed = false;
        }

        g.resetTransform();
    }

    /**
     * Initialise les listes de String pour les différentes options vidéos
     *
     * @param container Le gamecontainer pour obtenir les différentes options
     * acutelles
     */
    private void initialiseLists(GameContainer container) {
        displayModes = new ArrayList<>();
        displayModes.add("Windowed");
        displayModes.add("Borderless fullscreen");
        displayModes.add("Fullscreen");

        if (container.isFullscreen()) {
            selectedDisplayMode = 2;

        } else {
            selectedDisplayMode = 0;
        }

        resolutions = new ArrayList<>();
        DisplayMode[] modes = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayModes();
        for (DisplayMode mode : modes) {
            if (!resolutions.contains(mode.getWidth() + "x" + mode.getHeight()) && mode.getWidth() >= 800) {
                resolutions.add(mode.getWidth() + "x" + mode.getHeight());
            }

        }

        selectedResolution = resolutions.indexOf(container.getWidth() + "x" + container.getHeight());

        antialiasingOptions = new ArrayList<>();
        antialiasingOptions.add("Off");
        if (container.supportsMultiSample()) {
            antialiasingOptions.add("2xMSAA");
            antialiasingOptions.add("4xMSAA");
            antialiasingOptions.add("8xMSAA");
        }

        anistropicOptions = new ArrayList<>();
        anistropicOptions.add("Bilinear");
        anistropicOptions.add("Trilinear");
        anistropicOptions.add("4x");
        anistropicOptions.add("8x");
        anistropicOptions.add("16x");

        setVSync = container.isVSyncRequested();

        framerateOptions = new ArrayList<>();
        framerateOptions.add("Unlimited");
        framerateOptions.add("20 fps");
        framerateOptions.add("30 fps");
        framerateOptions.add("60 fps");
        framerateOptions.add("100 fps");
        framerateOptions.add("200 fps");

        particlesOptions = new ArrayList<>();
        particlesOptions.add("All");
        particlesOptions.add("Minimal");
        particlesOptions.add("Off");

        musicVolumes = new ArrayList<>();
        musicVolumes.add("0");
        musicVolumes.add("10");
        musicVolumes.add("20");
        musicVolumes.add("30");
        musicVolumes.add("40");
        musicVolumes.add("50");
        musicVolumes.add("60");
        musicVolumes.add("70");
        musicVolumes.add("80");
        musicVolumes.add("90");
        musicVolumes.add("100");

        soundVolumes = new ArrayList<>();
        soundVolumes.add("0");
        soundVolumes.add("10");
        soundVolumes.add("20");
        soundVolumes.add("30");
        soundVolumes.add("40");
        soundVolumes.add("50");
        soundVolumes.add("60");
        soundVolumes.add("70");
        soundVolumes.add("80");
        soundVolumes.add("90");
        soundVolumes.add("100");
    }

    /**
     * Calcule le scale à appliquer aux méthodes draw pour que tout rentre dans
     * n'importe quelle taille d'écran Mets à jours les MouseOverAreas pour
     * qu'elles suivent les boutons apres le rescale
     *
     * @param container Le gamecontainer pour obtenir la taille de l'écran
     */
    private void rescale(GameContainer container) {
        scx = (float) container.getWidth() / 1920;
        scy = (float) container.getHeight() / 1080;

        videotab = new MouseOverArea(container, null, (int) (321 * scx), (int) (70 * scy), (int) (426 * scx), (int) (106 * scy));
        displayMode = new MouseOverArea(container, null, (int) (120 * scx), (int) (324 * scy), (int) (780 * scx), (int) (24 * scy));
        res = new MouseOverArea(container, null, (int) (120 * scx), (int) (420 * scy), (int) (780 * scx), (int) (24 * scy));
        antialiasing = new MouseOverArea(container, null, (int) (120 * scx), (int) (516 * scy), (int) (780 * scx), (int) (24 * scy));
        anistropic = new MouseOverArea(container, null, (int) (120 * scx), (int) (612 * scy), (int) (780 * scx), (int) (24 * scy));
        vSync = new MouseOverArea(container, null, (int) (120 * scx), (int) (708 * scy), (int) (780 * scx), (int) (24 * scy));
        framerateCap = new MouseOverArea(container, null, (int) (1020 * scx), (int) (324 * scy), (int) (780 * scx), (int) (24 * scy));
        bloom = new MouseOverArea(container, null, (int) (1020 * scx), (int) (420 * scy), (int) (780 * scx), (int) (24 * scy));
        motionBlur = new MouseOverArea(container, null, (int) (1020 * scx), (int) (516 * scy), (int) (780 * scx), (int) (24 * scy));
        particles = new MouseOverArea(container, null, (int) (1020 * scx), (int) (612 * scy), (int) (780 * scx), (int) (24 * scy));

        audiotab = new MouseOverArea(container, null, (int) (1173 * scx), (int) (70 * scy), (int) (426 * scx), (int) (106 * scy));
        music = new MouseOverArea(container, null, (int) (120 * scx), (int) (516 * scy), (int) (780 * scx), (int) (24 * scy));
        sounds = new MouseOverArea(container, null, (int) (1020 * scx), (int) (516 * scy), (int) (780 * scx), (int) (24 * scy));

        backbutton = new MouseOverArea(container, null, (int) (90 * scx), (int) (976 * scy), (int) (96 * scx), (int) (24 * scy));
    }

    /**
     * Permet aux autres classes de dire à ce State quel était le State
     * précédent
     *
     * @param lastState le ID du State qui appelle cette méthode
     */
    public static void setLastState(int lastState) {
        OptionsMenu.lastState = lastState;
    }

    /**
     * Retourne le state précédent pour y retourner lorsque l'on quitte le menu
     *
     * @return le dernier state avant d'entrer dans le menu
     */
    public static int getLastState() {
        return lastState;
    }

}
