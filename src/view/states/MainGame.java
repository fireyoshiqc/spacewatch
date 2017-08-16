/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.states;

import controller.DopplerSound;
import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.entities.ships.PlayerShip;
import gameComponents.entities.ships.Ship;
import gameComponents.entities.ships.Ship.ShipType;
import gameComponents.entities.ships.Ship.Team;
import gameComponents.entities.spaceObjets.Planet;
import java.awt.Toolkit;
import java.util.ArrayList;
import model.chunkSystem.Chunk;
import model.chunkSystem.ChunkGenerator;
import model.collisionSystem.CollisionChecker;
import model.items.Item;
import model.items.ItemSystem;
import model.items.StackableItem;
import model.particleSystem.RocketEmitter;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.FontUtils;
import view.Camera;
import view.hud.BaseUI;
import static view.states.MainMenu.scx;
import static view.states.MainMenu.scy;

/**
 * GameState du jeu en tant que tel S'occupe d'appeler les classes nécessaires
 * au fonctionnement du programme
 *
 * @author 1331037
 */
public class MainGame extends BasicGameState {

    private Image farbackground;
    private float fbgx = 0, fbgy = 0;
    private Image background;
    private float bgx = 0, bgy = 0;
    private Image foreground;
    private float fgx = 0, fgy = 0;
    private Image pauseMenu;

    private Sound hover;
    private Sound select;
    private Sound gameOverSound;

    private ArrayList<Music> songList;
    private int currentMusicIndex = 0;
    private Music currentMusic;

    private Color color;

    private BaseUI bui;

    private Input input;

    private float movx = 0;
    private float movy = 0;

    private float angularSpeed = 0;

    private boolean keyDown;
    private boolean f3Pressed;
    private boolean escapePressed;
    private boolean isNearPlanet;
    private boolean iPressed;

    private int compteur;
    private int compteur2;
    private int compteur3;
    private int compteurZ;
    private int compteurI;
    private Camera camera;
    private ChunkGenerator ck;
    private Chunk base;
    private float scale = 1f;

    private PlayerShip ship;

    private RocketEmitter re;
    private ParticleSystem psy;

    private ProjectileSystem prs;
    private ItemSystem its;

    private DopplerSound rocket;
    private int soundIndex;

    private CollisionChecker clc;

    private ArrayList<Ship> ships;

    private MouseOverArea resumeButton, optionsButton, quitButton, exitButton, menuButton;
    private boolean isOver;
    private boolean hasPlayed = true;
    private boolean gameOver;

    /**
     * @throws org.newdawn.slick.SlickException
     *
     *
     */
    public MainGame() throws SlickException {

    }

    /**
     * Initialise les Ressources lorsque l'on crée une nouvelle partie
     * Initialise les son, images, compteurs, les systèmes de particules,
     * d'items Crée le vaisseau du joueur et les vaisseaux ennemis, ainsi que
     * les boutons pour le menu pause et l'écran game over
     *
     * @param gc
     * @param game
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        gameOver = false;
        compteur = 0;
        compteur2 = 0;
        compteur3 = 0;
        compteurZ = 0;
        compteurI = 0;
        keyDown = false;
        f3Pressed = false;
        escapePressed = false;
        isNearPlanet = false;
        iPressed = false;

        hover = new Sound("MenuHover.ogg");
        select = new Sound("MenuSelect.ogg");
        gameOverSound = new Sound("GameOver_Sound.ogg");

        clc = new CollisionChecker();
        psy = new ParticleSystem("rocket_particle.png", 100000);
        psy.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
        farbackground = new Image("backstars.png");

        farbackground.setFilter(Image.FILTER_NEAREST);

        pauseMenu = new Image("Escape_Menu.png");

        background = new Image("stars.png");
        background = background.getScaledCopy(2.5f);
        background.setFilter(Image.FILTER_LINEAR);
        foreground = new Image("frontstars.png");
        foreground = foreground.getScaledCopy(1.25f);
        foreground.setFilter(Image.FILTER_LINEAR);

        input = new Input(Toolkit.getDefaultToolkit().getScreenSize().height);

        ship = new PlayerShip(500000, 400000, 9001, new Vector2f(0, 0), new Vector2f(0, 0), angularSpeed, ShipType.R02, null, 0, psy, its);

        camera = new Camera((float) (ship.getX() - (ship.getWidth() / 2)), (float) (ship.getY() - (ship.getHeight() / 2)), gc);
        ck = new ChunkGenerator();
        base = ck.generateSystemType(ck.transformSeed(ck.getWorld().getChunkList().get(499499)), 499499);

        rocket = new DopplerSound("RocketEngine.ogg");

        prs = new ProjectileSystem();
        its = new ItemSystem();
        bui = new BaseUI(gc, ship);

        ships = new ArrayList<>();
        ships.add(ship);

        resumeButton = new MouseOverArea(gc, null, (int) (scx * 850), (int) (340 * scy), 144, 24);
        optionsButton = new MouseOverArea(gc, null, (int) (scx * 830), (int) (520 * scy), 168, 24);
        quitButton = new MouseOverArea(gc, null, (int) (scx * 900), (int) (690 * scy), 78, 24);
        menuButton = new MouseOverArea(gc, null, (int) (scx * 650), (int) (600 * scy), 96, 24);
        exitButton = new MouseOverArea(gc, null, (int) (scx * 1140), (int) (600 * scy), 76, 24);

        //TEST
        for (int i = 0; i < 5; i++) {
            ShipType type;
            switch ((int) (Math.random() * 4)) {
                case 0:
                    type = ShipType.R01;
                    break;
                case 1:
                    type = ShipType.R02;
                    break;
                case 2:
                    type = ShipType.AFA;
                    break;
                case 3:
                    type = ShipType.ZAX;
                    break;
                case 4:
                    type = ShipType.STOMPER;
                    break;
                default:
                    type = ShipType.R01;
            }

            Ship enemyShip = new Ship(497000 + (Math.random() - 0.5) * 200000, 400000 + (Math.random() - 0.5) * 200000, 12500, new Vector2f(0, 0), new Vector2f(0, 0), angularSpeed, type, Team.Team1, 0, psy, its);

            ships.add(enemyShip);

            //
        }
        Ship boss = new Ship(505000, 400000, 9001, new Vector2f(0, 0), new Vector2f(0, 0), angularSpeed, ShipType.SKRELCH_CAPITAL, Team.Team1, 0, psy, its);
        ships.add(boss);
        for (Ship shipai : ships) {
            if (!(shipai instanceof PlayerShip)) {
                shipai.activateAI(ships);
            }
        }
        prs.update(1, ships, clc, psy);

        songList = new ArrayList<>();

        songList.add(new Music("BGM_Asteroid.ogg"));
        songList.add(new Music("BGM_Nebula.ogg"));
        songList.add(new Music("BGM_SolarSystem.ogg"));

    }

    /**
     * S'occupe de calculer la position de tout après chaque intervalle de temps
     * S'occupe de gérer les inputs du joueurs Gère la position des objets dans
     * le jeu Gère les collisions
     *
     * @param gc Ce qui contient les éléments du jeu
     * @param game Le jeu en tant que tel
     * @param delta Ratio sur 60 IPS
     * @throws SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if (currentMusicIndex > songList.size() - 1) {
            currentMusicIndex = 0;
        }
        currentMusic = songList.get(currentMusicIndex);
        if (!currentMusic.playing()) {
            currentMusic.play(1f, 0.5f);
            currentMusic.addListener(new MusicListener() {

                @Override
                public void musicEnded(Music music) {
                    currentMusicIndex++;
                }

                @Override
                public void musicSwapped(Music music, Music newMusic) {

                }
            });

        }

        if (OptionsMenu.getLastState() != this.getID()) {
            OptionsMenu.setLastState(this.getID());
        }
        float multi = (float) ((float) delta / (1000 / 60));
        if (escapePressed && !gameOver) {
            gc.setMouseGrabbed(false);
            if (!input.isKeyDown(Input.KEY_ESCAPE)) {
                compteur3 = 0;

            }
            if (input.isKeyDown(Input.KEY_ESCAPE) && compteur3 == 0) {

                escapePressed = !escapePressed;
                compteur3++;
            }

            if (resumeButton.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                select.play();
                escapePressed = !escapePressed;
            }
            if (optionsButton.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                select.play();
                game.enterState(2, new FadeOutTransition(), new FadeInTransition());
            }
            if (quitButton.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                select.play();
                game.enterState(0, new FadeOutTransition(), new FadeInTransition());
                this.init(gc, game);

            }
            if (isOver && !hasPlayed) {
                hover.play();
                hasPlayed = true;
            }

        } else {

            //fe.update(psy, delta);
            movx = 0;
            movy = 0;

            for (Ship shipai : ships) {
                if (!(shipai instanceof PlayerShip)) {
                    shipai.update(shipai.getForce(), (float) shipai.getAngularSpeed(), multi, psy, prs);
                } else {
                    shipai.update(shipai.getForce(), (float) angularSpeed, multi, psy, prs);
                    if (shipai.isDestroyed()) {
                        if (!gameOver) {
                            gameOverSound.play(1f, 1f);
                        }
                        gameOver = true;
                    }
                }

            }

            for (int i = 0; i < ships.size(); i++) {
                for (int j = i + 1; j < ships.size(); j++) {
                    clc.radialCollision(ships.get(i), ships.get(j));
                }
            }
            if (ship.isDestroyed() && !gameOver) {
                gameOver = true;
                gameOverSound.play(1f, 1f);
                rocket.stop();

            }

            ck.checkPlayerPosition(ship);
            ck.getWorld().update(multi);
            prs.update(multi, ships, clc, psy);
            its.update(ship);
            try {
                AL10.alSourcef(soundIndex, AL10.AL_PITCH, ((ship.getVelocity().length()) / 20) + 1.5f);
            } catch (UnsatisfiedLinkError e) {

            }

            checkIfNearPlanet();

            psy.update(delta);

            ship.setThrustersOn(0);

            if (keyDown) {

                keyDown = false;

            }

            if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)) {
                if (angularSpeed >= -2.5) {
                    angularSpeed -= 0.2 * multi;
                }

                keyDown = true;

            }

            if (input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)) {

                keyDown = true;

                ship.setThrustersOn(1);
                if (!rocket.playing()) {
                    soundIndex = rocket.pitchLoop(1.0f, 0.2f);
                }

            }
            if (input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN)) {

                keyDown = true;

                ship.setThrustersOn(-1);

            }

            if (input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)) {
                if (angularSpeed <= 2.5) {
                    angularSpeed += 0.2 * multi;
                }

                keyDown = true;

            }
            if (input.isKeyDown(Input.KEY_X)) {
                ship.brake(delta);
                angularSpeed /= 1.1;
            }

            if (input.isKeyDown(Input.KEY_SPACE)) {
                //TEST TEST TEST TEST
                ship.firePrimary(multi, prs);

                //
            } else {
                ship.stopAcidFire();
            }
            if (input.isKeyDown(Input.KEY_RSHIFT) || input.isKeyDown(Input.KEY_LSHIFT)) {
                ship.fireSecondary(multi, prs);
            } else {
                ship.stopLaserFire(prs);

                ship.cooldown(multi);
            }
            if (!input.isKeyDown(Input.KEY_F3)) {
                compteur = 0;
            }
            if (input.isKeyDown(Input.KEY_F3) && compteur == 0) {

                f3Pressed = !f3Pressed;
                compteur++;
            }
            if (!input.isKeyDown(Input.KEY_ESCAPE)) {
                compteur3 = 0;
            }
            if (input.isKeyDown(Input.KEY_ESCAPE) && compteur3 == 0) {
                rocket.stop();
                escapePressed = !escapePressed;
                compteur3++;
            }
            if (!input.isKeyDown(Input.KEY_Z)) {
                compteur2 = 0;

            }
            if (compteurZ == 0) {
                scale = 1f * gc.getHeight() / 1080;
            }
            if (input.isKeyDown(Input.KEY_Z) && compteur2 == 0) {

                compteurZ++;
                if (compteurZ % 5 == 0) {
                    scale = 1f * gc.getHeight() / 1080;
                } else if (compteurZ % 5 == 1) {
                    scale = 0.66f * gc.getHeight() / 1080;
                } else if (compteurZ % 5 == 2) {
                    scale = 0.33f * gc.getHeight() / 1080;
                } else if (compteurZ % 5 == 3) {
                    scale = 0.1f * gc.getHeight() / 1080;
                } else if (compteurZ % 5 == 4) {
                    scale = 2f * gc.getHeight() / 1080;
                }
                compteur2++;
            }

            if (isNearPlanet && input.isKeyDown(Input.KEY_F)) {
                gc.setMouseGrabbed(false);
                rocket.stop();
                game.enterState(4, new FadeOutTransition(), new FadeInTransition());

            }
            if (!input.isKeyDown(Input.KEY_I)) {
                compteurI = 0;
            }
            if (input.isKeyDown(Input.KEY_I) && compteurI == 0) {

                iPressed = !iPressed;
                compteurI++;
            }

            bui.update();

            fbgx -= (ship.getVelocity().getX() * multi) * 0.01f;
            fbgx %= 2048;

            fbgy -= (ship.getVelocity().getY() * multi) * 0.01f;
            fbgy %= 2048;

            bgx -= (ship.getVelocity().getX() * multi) * 0.1f;
            bgx %= 2560;

            bgy -= (ship.getVelocity().getY() * multi) * 0.1f;
            bgy %= 2560;

            fgx -= ship.getVelocity().getX() * multi;
            fgx %= 1280;

            fgy -= ship.getVelocity().getY() * multi;
            fgy %= 1280;

            if (gameOver) {
                songList.get(currentMusicIndex).stop();
                rocket.stop();
                gc.setMouseGrabbed(false);
                if (menuButton.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                    this.init(gc, game);
                    game.enterState(0, new FadeOutTransition(), new FadeInTransition());
                }
                if (exitButton.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                    System.exit(0);
                }
                if (isOver && !hasPlayed) {
                    hover.play();
                    hasPlayed = true;
                }
            }

        }

    }

    /**
     * Dessine les éléments visuels du jeu par rapports aux positions envoyés
     * par Update Gère l'écran de GameOver ou de Pause Donne l'information par
     * rapport au vaisseau au joueur
     *
     * @param gc Ce qui contient les éléments du jeu
     * @param game Le jeu en tant que tel
     * @param g L'élément graphics qui va dessiner les éléments graphiques
     * @throws SlickException
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        for (int x = -2048; x <= (int) scx * 1920 + 2048; x += 2048) {
            for (int y = -2048; y <= (int) scy * 1080 + 2048; y += 2048) {

                farbackground.draw(fbgx + x, fbgy + y);

            }
        }
        for (int x = -2560; x <= (int) scx * 1920 + 2560; x += 2560) {
            for (int y = -2560; y <= (int) scy * 1080 + 2560; y += 2560) {

                background.draw(bgx + x, bgy + y);

            }
        }

        g.scale(scale, scale);
        isOver = false;
        for (int x = (int) (1 / scale * -1280); x <= (int) (1 / scale * scx * 1920 + 1280); x += 1280) {
            for (int y = (int) (1 / scale * -1280); y <= (int) (1 / scale * scy * 1080 + 1280); y += 1280) {

                foreground.draw(fgx + x, fgy + y);

            }
        }

        g.setWorldClip(0, 0, gc.getWidth() / scale, gc.getHeight() / scale);
        camera.setLocation((float) ship.getX() + ((ship.getWidth()) / 2)
                - (g.getWorldClip().getWidth() / 2), (float) ship.getY()
                + ((ship.getHeight()) / 2)
                - (g.getWorldClip().getHeight() / 2));

        g.translate(-camera.getX(), -camera.getY());

        g.setColor(Color.white);

        ck.getWorld().draw();
        psy.render();

        Rectangle prsZone = new Rectangle(camera.getX() - 10000, camera.getY() - 10000,
                g.getWorldClip().getWidth() + 20000, g.getWorldClip().getHeight() + 20000);
        prs.draw(prsZone, scale, psy);
        its.draw(prsZone);

        for (Ship shipRender : ships) {
            shipRender.draw();
        }

        g.resetTransform();
        if (escapePressed && !gameOver) {
            g.drawImage(pauseMenu.getScaledCopy((int) (1920 * scx), (int) (1080 * scy)), 0, 0);
            color = Color.white;
            if (resumeButton.isMouseOver()) {
                color = Color.cyan;
                isOver = true;
            }
            FontUtils.drawCenter(Opening.font2, "Resume", 0, (int) (scy * 340), gc.getWidth(), color);
            color = Color.white;
            if (optionsButton.isMouseOver()) {
                color = Color.cyan;
                isOver = true;
            }
            FontUtils.drawCenter(Opening.font2, "Options", 0, (int) (scy * 520), gc.getWidth(), color);
            color = Color.white;
            if (quitButton.isMouseOver()) {
                color = Color.cyan;
                isOver = true;
            }
            FontUtils.drawCenter(Opening.font2, "Quit", 0, (int) (scy * 690), gc.getWidth(), color);
            color = Color.white;
        }

        if (gameOver) {
            FontUtils.drawCenter(Opening.font2, "Game over", 0, (int) (scy * 450), gc.getWidth());
            color = Color.white;
            if (menuButton.isMouseOver()) {
                color = Color.cyan;
                isOver = true;
            }
            FontUtils.drawCenter(Opening.font2, "Menu", (int) (scx * 480), (int) (scy * 600), (int) (scx * 480), color);
            color = Color.white;
            if (exitButton.isMouseOver()) {
                color = Color.cyan;
                isOver = true;
            }
            FontUtils.drawCenter(Opening.font2, "Exit", (int) (scx * 960), (int) (scy * 600), (int) (scx * 480), color);
        }

        if (!isOver) {
            hasPlayed = false;
        }
        bui.draw();
        if (isNearPlanet && !gameOver) {
            FontUtils.drawCenter(Opening.font2, "press f to land on planet", 0, 50, gc.getWidth());
        }
        if (f3Pressed) {

            g.drawString("Contrôles:\n"
                    + "W/S, Up/Down: Contrôle de la poussée\n"
                    + "A/S, Left/Right: Rotation\n"
                    + "X: Frein complet\n"
                    + "Espace: Tir arme principale\n"
                    + "T: Pause\n\n"
                    + "Infos:\n"
                    + "deltaX: " + String.format("%.2f", ship.getVelocity().getX())
                    + "\ndeltaY: " + String.format("%.2f", ship.getVelocity().getY())
                    + "\nvitAng: " + String.format("%.2f", angularSpeed)
                    + "\nangle: " + String.format("%.2f", ship.getAngle() % 360)
                    + "\nxChunk: " + String.format("%.2f", ship.getX() / 1000000)
                    + "\nyChunk: " + String.format("%.2f", ship.getY() / 1000000)
                    + "\nxSubChunk: " + String.format("%.2f", ship.getX() / 1000)
                    + "\nySubChunk: " + String.format("%.2f", ship.getY() / 1000)
                    + "\nChunkType: " + ck.getCurrentChunk().getType()
                    + "\nChunkString: " + ck.getCurrentChunk().getSeed()
                    + "\nShipMass: " + String.format("%.2f", ship.getMass()) + " kg"
                    + "\nFuelLeft: " + String.format("%.2f", ship.getFuel()) + " L"
                    + "\nBulletsLeft: " + ship.getBullets(), 10, 30);

        }
        if (iPressed) {

            g.drawString("Inventory:", 350, 30);

            if (ship.getInventory().getListItem().isEmpty()) {
                g.drawString("«empty»", 350, 45);
            } else {
                int nbrItems = 1;
                for (Item item : ship.getInventory().getListItem()) {

                    if (item instanceof StackableItem) {
                        StackableItem sitem = (StackableItem) item;
                        g.drawString("" + sitem.getResource() + "  " + String.format("%.2f", sitem.getMass()) + " kg", 350, 30 + 15 * nbrItems);
                    } else {

                    }
                    nbrItems++;
                }

            }

        }

    }

    /**
     * Getter pour le particleSystem
     *
     * @return le particleSystem de mainGame
     */
    public ParticleSystem getPsy() {
        return psy;
    }

    /**
     * Calcule le scale à appliquer aux méthodes draw pour que tout rentre dans
     * n'importe quelle taille d'écran
     *
     * @param gc Le gamecontainer pour obtenir la taille de l'écran
     * @throws org.newdawn.slick.SlickException
     */
    public void rescale(GameContainer gc) throws SlickException {
        camera = new Camera((float) (ship.getX() - (ship.getWidth() / 2)), (float) (ship.getY() - (ship.getHeight() / 2)), gc);
        bui = new BaseUI(gc, ship);

    }

    /**
     * Fonction qui retourne l'ID du GameState
     *
     * @return ID du GameState
     */
    @Override
    public int getID() {
        return 3;

    }

    /**
     * Méthode qui regarde si le vaisseau du joueur est proche d'une planète ou
     * pas
     */
    private void checkIfNearPlanet() {

        if (ck.getCurrentChunk().getType().equals((Chunk.Type.SOLAR_SYSTEM).toString())) {
            Chunk chunk = ck.getCurrentChunk();

            if (ship.getColCenterX() > chunk.getPosX() && ship.getColCenterX() < (chunk.getPosX() + chunk.getSIZEX())) {
                if (ship.getColCenterY() > chunk.getPosY() && ship.getColCenterY() < (chunk.getPosY() + chunk.getSIZEY())) {
                    for (int i = 0; i < chunk.getContents().size(); i++) {
                        if (chunk.getContents().get(i) instanceof Planet) {
                            if (ship.getPos().intersects(chunk.getContents().get(i).getPos())) {
                                isNearPlanet = true;
                                i = chunk.getContents().size();
                            } else {
                                isNearPlanet = false;
                            }
                        }
                    }
                }
            }
        } else {
            isNearPlanet = false;
        }

    }

    /**
     *
     * @return Le vaisseau du joueur.
     */
    public PlayerShip getShip() {
        return ship;
    }

}
