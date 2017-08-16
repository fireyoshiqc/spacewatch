/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.modules.engines;

import gameComponents.modules.Module;

/**
 * Classe abstraite représentant les divers moteurs.
 * @author 1331037
 */
public abstract class Engine extends Module {

    /**
     * La force maximale du moteur.
     */
    protected float force;

    /**
     * Le carburant maximal du moteur.
     */
    protected float maxFuel;

    /**
     * Le carburant restant du moteur.
     */
    protected float leftFuel;
    
    

}
