/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.states;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import model.items.Item;
import model.items.StackableItem;
import static model.items.StackableItem.Resources.COPPER;
import static model.items.StackableItem.Resources.GOLD;
import static model.items.StackableItem.Resources.IRON;
import static model.items.StackableItem.Resources.PLATINUM;
import static model.items.StackableItem.Resources.SILVER;
import static model.items.StackableItem.Resources.URANIUM;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;
import view.hud.ShopItem;

/**
 * GameState du Magasin, lorsque le joueur atterit sur une planète Permet de
 * vendre des ressources contre des crédits, et d'acheter des items (DISPONIBLE
 * EN DLC POUR 15,99$)
 *
 * @author 1332327
 */
public class Shop extends BasicGameState {

    private Input input;
    private float scx;
    private float scy;
    private Image bg;
    private boolean isOver = false, hasPlayed = false;
    private Sound hover, select;
    private TrueTypeFont font;
    private TrueTypeFont font2;
    private Color textColor;
    private int selectedShopTab = 0;
    private int soldValue;
    private boolean isMouseButtonDown = false;
    private Sound nope;

    private ShopItem R02;
    private ShopItem AFA;
    private ShopItem Stomper;
    private ShopItem M201, M211, M221, M241, M251, M261, M271;
    private ShopItem ZRA, ZRB, ZRC, ZRD, ZRE, ZRF;
    private ShopItem L311, L321, L331, L341, L901;
    private ShopItem B9001;
    private ShopItem PX1;
    private ShopItem RE, IE;

    private ArrayList<Item> toRemove;

    private SpriteSheet r02, afa, stomper, shipLogos, Weapons, Engines;

    private MouseOverArea backbutton, ships, weapons, engines, sellAllRes;

    private MainGame maingame;

    /**
     * Fonction qui retourne l'ID du GameState
     *
     * @return ID du GameState
     */
    @Override
    public int getID() {
        return 4;
    }

    /**
     * Initialise les variables de la classe pour future utilisation
     *
     * @param container Ce qui contient les éléments du jeu
     * @param game Le jeu en tant que tel
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        toRemove = new ArrayList<>();

        soldValue = 0;

        maingame = (MainGame) game.getState(3);

        hover = new Sound("MenuHover.ogg");
        select = new Sound("MenuSelect.ogg");
        nope = new Sound("nope.ogg");
        bg = new Image("Shop_Back.png");
        Weapons = new SpriteSheet("Weapon_Spritesheet.png", 64, 64);
        Engines = new SpriteSheet("Engine_Spritesheet.png", 128, 128);

        shipLogos = new SpriteSheet("ShipLogos_Spritesheet.png", 640, 320);
        r02 = new SpriteSheet("R02Ship_Spritesheet.png", 256, 256);
        afa = new SpriteSheet("AFAShip_SpriteSheet.png", 256, 256);
        stomper = new SpriteSheet("StomperShip_SpriteSheet.png", 256, 256);

        input = new Input(Toolkit.getDefaultToolkit().getScreenSize().height);
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, false);
        scx = (float) container.getWidth() / 1920;
        scy = (float) container.getHeight() / 1080;

        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("Xperia.ttf");

            Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont2 = awtFont2.deriveFont(30f); // set font size
            font2 = new TrueTypeFont(awtFont2, true);

        } catch (FontFormatException | IOException e) {
        }

        setShopItem(container);
        setMouseOverAreas(container);

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

        if (soldValue == 0) {
            if (isOver && !hasPlayed) {
                hover.play();
                hasPlayed = true;
            }
        }

        if (backbutton.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            select.play();
            selectedShopTab = 0;
            game.enterState(3, new FadeOutTransition(), new FadeInTransition());
        }

        if (ships.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
            select.play();
            selectedShopTab = 1;
        }

        if (weapons.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
            select.play();
            selectedShopTab = 2;
        }

        if (engines.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
            select.play();
            selectedShopTab = 3;
        }

        if (sellAllRes.isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
            select.play();
            for (Item item : maingame.getShip().getInventory().getListItem()) {
                if (item instanceof StackableItem) {
                    switch (((StackableItem) item).getResource()) {
                        case IRON:
                            soldValue += (item.getMass() / 7.874f) * 50;
                            break;
                        case COPPER:
                            soldValue += (item.getMass() / 8.96f) * 100;
                            break;
                        case SILVER:
                            soldValue += (item.getMass() / 10.49f) * 500;
                            break;
                        case GOLD:
                            soldValue += (item.getMass() / 19.30f) * 1000;
                            break;
                        case PLATINUM:
                            soldValue += (item.getMass() / 21.45f) * 5000;
                            break;
                        case URANIUM:
                            soldValue += (item.getMass() / 19.1f) * 10000;
                            break;

                    }
                }
            }
            maingame.getShip().setSc(soldValue);
            soldValue = 0;
            maingame.getShip().getInventory().getListItem().removeAll(toRemove);
        }

        switch (selectedShopTab) {
            case 1:
                if (R02.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(R02);
                }
                if (AFA.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(AFA);
                }
                if (Stomper.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(Stomper);
                }
                break;
            case 2:
                if (M201.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(M201);
                }
                if (M211.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(M211);
                }
                if (M221.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(M221);
                }
                if (M241.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(M241);
                }
                if (M251.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(M251);
                }
                if (M261.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(M261);
                }
                if (M271.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(M271);
                }
                if (ZRA.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(ZRA);
                }
                if (ZRB.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(ZRB);
                }
                if (ZRC.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(ZRC);
                }
                if (ZRD.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(ZRD);
                }
                if (ZRE.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(ZRE);
                }
                if (ZRF.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(ZRF);
                }
                if (L311.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(L311);
                }
                if (L321.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(L321);
                }
                if (L331.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(L331);
                }
                if (L341.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(L341);
                }
                if (L901.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(L901);
                }
                if (PX1.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(PX1);
                }
                if (B9001.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(B9001);
                }

                break;
            case 3:
                if (RE.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(RE);
                }
                if (IE.getClickArea().isMouseOver() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && isMouseButtonDown) {
                    select.play();
                    buy(IE);
                }
                break;
        }

        if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            isMouseButtonDown = true;
        }
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            isMouseButtonDown = false;
        }

    }

    /**
     * S'occupe de dessiner les éléments graphiques
     *
     * @param container * @param container Ce qui contient les éléments du jeu
     * @param game Le jeu en tant que tel
     * @param g L'élément graphics qui va dessiner les éléments graphiques
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        isOver = false;
        g.scale(scx, scy);
        g.setFont(font2);
        g.drawImage(bg, 0, 0);

        g.setColor(Color.white);
        g.drawString(maingame.getShip().getSc() + " S.C.", 90, 104);

        textColor = Color.white;
        if (backbutton.isMouseOver()) {
            textColor = Color.cyan;
            isOver = true;
        }
        g.setColor(textColor);
        g.drawString("Back", 90, 976);

        textColor = Color.white;
        if (ships.isMouseOver()) {
            textColor = Color.cyan;
            isOver = true;
        }
        g.setColor(textColor);
        g.drawString("Ships", 120, 324);

        textColor = Color.white;
        if (weapons.isMouseOver()) {
            textColor = Color.cyan;
            isOver = true;
        }
        g.setColor(textColor);
        g.drawString("Weapons", 120, 420);

        textColor = Color.white;
        if (engines.isMouseOver()) {
            textColor = Color.cyan;
            isOver = true;
        }
        g.setColor(textColor);
        g.drawString("Engines", 120, 516);

        textColor = Color.white;
        if (sellAllRes.isMouseOver()) {
            textColor = Color.cyan;
            isOver = true;
        }
        g.setColor(textColor);
        g.drawString("Sell All for", 120, 612);
        g.drawString("test" + " C.P.", 120, 636);
        switch (selectedShopTab) {
            case 1:
                if (R02.getClickArea().isMouseOver()) {
                    isOver = true;
                }
                if (R02.getClickArea().isMouseOver()) {
                    isOver = true;
                }
                if (R02.getClickArea().isMouseOver()) {
                    isOver = true;
                }
                R02.draw(500, 420);
                AFA.draw(900, 420);
                Stomper.draw(1300, 420);
                break;
            case 2:
                M201.draw(500, 90);
                M211.draw(500, 200);
                M221.draw(500, 310);
                M241.draw(500, 420);
                M251.draw(500, 530);
                M261.draw(500, 640);
                M271.draw(500, 750);

                ZRA.draw(900, 90);
                ZRB.draw(900, 200);
                ZRC.draw(900, 310);
                ZRD.draw(900, 420);
                ZRE.draw(900, 530);
                ZRF.draw(900, 640);

                L311.draw(1300, 90);
                L321.draw(1300, 200);
                L331.draw(1300, 310);
                L341.draw(1300, 420);
                L901.draw(1300, 530);

                B9001.draw(1300, 640);

                PX1.draw(1300, 750);
                break;
            case 3:
                RE.draw(500, 420);
                IE.draw(1100, 420);
                break;
        }

        if (!isOver) {
            hasPlayed = false;
        }

        g.resetTransform();

    }

    /**
     * Calcule le scale à appliquer aux méthodes draw pour que tout rentre dans
     * n'importe quelle taille d'écran
     *
     * @param container Le gamecontainer pour obtenir la taille de l'écran
     */
    public void rescale(AppGameContainer container) {
        scx = (float) container.getWidth() / 1920;
        scy = (float) container.getHeight() / 1080;
    }

    /**
     * Sépare la création des MouseOverAreas pour alléger la méthode init()
     *
     * @param container Le gamecontainer à passer en paramètre pour les
     * MouseOverAreas
     */
    private void setMouseOverAreas(GameContainer container) {
        ships = new MouseOverArea(container, null, (int) (120 * scx), (int) (324 * scy), (int) (106 * scx), (int) (24 * scy));
        weapons = new MouseOverArea(container, null, (int) (120 * scx), (int) (420 * scy), (int) (168 * scx), (int) (24 * scy));
        engines = new MouseOverArea(container, null, (int) (120 * scx), (int) (516 * scy), (int) (150 * scx), (int) (24 * scy));
        sellAllRes = new MouseOverArea(container, null, (int) (120 * scx), (int) (612 * scy), (int) (150 * scx), (int) (24 * scy));
        backbutton = new MouseOverArea(container, null, (int) (90 * scx), (int) (976 * scy), (int) (96 * scx), (int) (24 * scy));
    }

    /**
     * Sépare la création des items dans le magasin, pour alléger la méthode
     * init()
     *
     * @param container Le gamecontainer à passer en paramètre pour les shopItem
     */
    private void setShopItem(GameContainer container) {
        R02 = new ShopItem(r02.getSprite(0, 0), shipLogos.getSprite(0, 0), "R02", 50000, container);

        AFA = new ShopItem(afa.getSprite(0, 0), shipLogos.getSprite(0, 0), "AFA", 45000, container);

        Stomper = new ShopItem(stomper.getSprite(0, 0), shipLogos.getSprite(0, 0), "STOMPER", 60000, container);

        M201 = new ShopItem(Weapons.getSprite(0, 0), shipLogos.getSprite(0, 0), "M201", 1000, container);
        M211 = new ShopItem(Weapons.getSprite(1, 0), shipLogos.getSprite(0, 0), "M211", 1500, container);
        M221 = new ShopItem(Weapons.getSprite(2, 0), shipLogos.getSprite(0, 0), "M221", 2000, container);
        M241 = new ShopItem(Weapons.getSprite(4, 0), shipLogos.getSprite(0, 0), "M241", 1500, container);
        M251 = new ShopItem(Weapons.getSprite(5, 0), shipLogos.getSprite(0, 0), "M251", 2500, container);
        M261 = new ShopItem(Weapons.getSprite(6, 0), shipLogos.getSprite(0, 0), "M261", 4000, container);
        M271 = new ShopItem(Weapons.getSprite(7, 0), shipLogos.getSprite(0, 0), "M271", 6000, container);

        ZRA = new ShopItem(Weapons.getSprite(0, 1), shipLogos.getSprite(1, 0), "ZRA", 2000, container);
        ZRB = new ShopItem(Weapons.getSprite(1, 1), shipLogos.getSprite(1, 0), "ZRB", 3000, container);
        ZRC = new ShopItem(Weapons.getSprite(2, 1), shipLogos.getSprite(1, 0), "ZRC", 3500, container);
        ZRD = new ShopItem(Weapons.getSprite(3, 1), shipLogos.getSprite(1, 0), "ZRD", 4500, container);
        ZRE = new ShopItem(Weapons.getSprite(4, 1), shipLogos.getSprite(1, 0), "ZRE", 6000, container);
        ZRF = new ShopItem(Weapons.getSprite(5, 1), shipLogos.getSprite(1, 0), "ZRF", 8000, container);

        L311 = new ShopItem(Weapons.getSprite(0, 2), shipLogos.getSprite(2, 0), "L311", 2500, container);
        L321 = new ShopItem(Weapons.getSprite(1, 2), shipLogos.getSprite(2, 0), "L321", 3500, container);
        L331 = new ShopItem(Weapons.getSprite(2, 2), shipLogos.getSprite(2, 0), "L331", 4000, container);
        L341 = new ShopItem(Weapons.getSprite(3, 2), shipLogos.getSprite(2, 0), "L341", 5000, container);
        L901 = new ShopItem(Weapons.getSprite(4, 2), shipLogos.getSprite(2, 0), "L901", 10000, container);

        B9001 = new ShopItem(Weapons.getSprite(0, 3), shipLogos.getSprite(3, 0), "B9001", 10000, container);

        PX1 = new ShopItem(Weapons.getSprite(0, 4), shipLogos.getSprite(4, 0), "PX1", 10000, container);

        RE = new ShopItem(Engines.getSprite(0, 0), shipLogos.getSprite(0, 0), "Rocket Engine", 500, container);
        IE = new ShopItem(Engines.getSprite(0, 1), shipLogos.getSprite(1, 0), "Ion Engine", 1500, container);
    }

    /**
     * Regarde si le jouer possède les crédits nécessaire pour acheter l'item
     * sélectionné. Si oui, achète l'item et retire les crédits.
     *
     * @param shopitem L'item que le joueur a décidé d'acheter.
     */
    private void buy(ShopItem shopitem) {
        if (maingame.getShip().getSc() < shopitem.getCost()) {
            nope.play();
        } else {
            maingame.getShip().setSc(shopitem.getCost() * -1);
        }
    }

}
