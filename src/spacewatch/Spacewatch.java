/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacewatch;

import org.newdawn.slick.*;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import view.states.MainGame;
import view.states.MainMenu;
import view.states.Opening;
import view.states.OptionsMenu;
import view.states.Shop;

/**
 *
 * @author Félix
 */
public class Spacewatch extends StateBasedGame {

    /**
     * Crée une nouvelle instance du jeu.
     *
     * @param name Le nom du jeu.
     */
    public Spacewatch(String name) {
        super("Spacewatch");

    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {

        addState(new Opening());
        addState(new MainMenu());
        addState(new MainGame());
        addState(new OptionsMenu());
        addState(new Shop());

    }

    /**
     * Démarre le jeu et lui donne les paramètres graphiques et sonores.
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new Spacewatch("Spacewatch Beta V1.0"));

            appgc.setDisplayMode(appgc.getScreenWidth(), appgc.getScreenHeight(), false);

            appgc.setSmoothDeltas(true);
            appgc.setVSync(true);
            appgc.setTargetFrameRate(900);

            appgc.setIcons(new String[]{"16x16.tga", "32x32.tga", "256x256.tga"});
            LoadingList.setDeferredLoading(true);

            appgc.start();

        } catch (SlickException ex) {

        }

    }

}
