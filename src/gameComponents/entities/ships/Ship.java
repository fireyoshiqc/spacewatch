/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameComponents.entities.ships;

import gameComponents.entities.PhysicsObject;
import gameComponents.entities.projectiles.ProjectileSystem;
import gameComponents.modules.Module;
import gameComponents.modules.engines.IonEngine;
import gameComponents.modules.engines.RocketEngine;
import gameComponents.modules.shields.Shield;
import gameComponents.modules.specials.Radar;
import gameComponents.modules.weapons.AcidWeapon;
import gameComponents.modules.weapons.BombWeapon;
import gameComponents.modules.weapons.BulletWeapon;
import gameComponents.modules.weapons.LaserWeapon;
import gameComponents.modules.weapons.MissileWeapon;
import java.util.ArrayList;
import model.aiSystem.BaseAI;
import model.aiSystem.BaseAI.Race;
import model.aiSystem.BaseAI.Skillset;
import model.aiSystem.BaseAI.Special;
import model.aiSystem.EnemyAI;
import model.items.DropTable;
import model.items.Inventory;
import model.items.ItemSystem;
import model.items.StackableItem;
import model.particleSystem.ExplosionEmitter;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;
import view.hud.LifeBar;

/**
 * Vaisseau spatial.
 *
 * @author 1331037
 */
public class Ship extends PhysicsObject {

    /**
     * La liste des modules du vaisseau.
     */
    protected ArrayList<Module> modulesList;

    /**
     * L'état actif ou non des réacteurs.
     */
    protected int thrustersOn = 0;

    /**
     * Le carburant restant.
     */
    protected float fuel;

    /**
     * Le carburant maximal.
     */
    protected float maxFuel;

    /**
     * La vie maximale.
     */
    protected float maxHp;

    /**
     * La vie restante.
     */
    protected float currentHp;

    /**
     * L'armure maximale.
     */
    protected float maxArmor;

    /**
     * Les munitions restantes.
     */
    protected int bullets;

    /**
     * Les munitions maximales.
     */
    protected int maxBullets;

    /**
     * Le bouclier maximal.
     */
    protected float maxShield;

    /**
     * Le bouclier restant.
     */
    protected float currentShield;

    /**
     * L'armure restante.
     */
    protected float currentArmor;

    /**
     * Le décalage du bouclier par rapport au centre du vaisseau.
     */
    protected float shieldOffset;

    /**
     * La vitesse maximale du vaisseau.
     */
    protected float maxVelocity;

    /**
     * La portée du radar.
     */
    protected float radarRange;

    /**
     * L'intelligence artificielle.
     */
    protected BaseAI ai;

    /**
     * La barre de vie.
     */
    protected LifeBar hpBar;

    /**
     * Le son d'explosion.
     */
    protected Sound explosion;

    /**
     * La faction du vaisseau,
     */
    protected Team team;

    /**
     * Le système de particules.
     */
    protected ParticleSystem psy;

    /**
     * Le système de projectiles.
     */
    protected ProjectileSystem prs;

    /**
     * Possède des fusils ou non.
     */
    protected boolean hasBullets = false;

    /**
     * Possède de l'acide ou non.
     */
    protected boolean hasAcid = false;

    /**
     * Possède des missiles ou non.
     */
    protected boolean hasMissiles = false;

    /**
     * Possède des lasers ou non.
     */
    protected boolean hasLasers = false;

    /**
     * Possède des bombes ou non.
     */
    protected boolean hasBombs = false;

    /**
     * Inventaire du vaisseau.
     */
    protected Inventory inventory;

    /**
     * Récompenses possibles à la destruction du vaisseau.
     */
    protected DropTable dropTable;

    /**
     * Système d'items.
     */
    protected ItemSystem its;

    /**
     * Faction du vaisseau.
     */
    public enum Team {

        /**
         * Équipe 1
         */
        Team1,
        /**
         * Équipe 2
         */
        Team2,
        /**
         * Équipe 3
         */
        Team3,
        /**
         * Équipe 4
         */
        Team4
    }

    /**
     * Le type de vaisseau.
     */
    public enum ShipType {

        /**
         * Vaisseau de base des humains. 2 fusils, 1 lance-bombes, 1
         * moteur-fusée.
         */
        R01,
        /**
         * Vaisseau avancé des humains. 1 fusil-mitrailleur, 2 lasers, 3
         * moteurs-fusée.
         */
        R02,
        /**
         * Vaisseau des canidés. 2 fusils, 2 lance-missiles, 2 moteurs-fusée.
         */
        STOMPER,
        /**
         * Vaisseau des avians. 1 fusil, 2 lasers, 1 moteur ionique.
         */
        AFA,
        /**
         * Vaisseau des Zz'Rax. 1 lance-acide, 2 moteurs ioniques.
         */
        ZAX,
        /**
         * Vaisseau-boss des Skrelch. 1 laser ultrapuissant, 3 moteurs-fusée.
         */
        SKRELCH_CAPITAL
    }

    /**
     *
     * @param x La position en X.
     * @param y La position en Y.
     * @param mass La masse de l'objet.
     * @param velocity Le vecteur vitesse de l'objet.
     * @param acceleration Le vecteur accélération de l'objet.
     * @param angularSpeed La vitesse rotationnelle de l'objet.
     * @param type Le type de vaisseau à créer.
     * @param team La faction dont fait partie le vaisseau.
     * @param skin La sous-image à utiliser pour le vaisseau (de 0 à 3).
     * @param psy Le système de particules.
     * @param its Le système d'objets.
     * @throws SlickException
     */
    public Ship(double x, double y, double mass, Vector2f velocity, Vector2f acceleration, double angularSpeed, ShipType type, Team team, int skin, ParticleSystem psy, ItemSystem its) throws SlickException {
        super(x, y, mass, velocity, acceleration, angularSpeed);
        this.team = team;
        this.psy = psy;
        this.its = its;
        explosion = new Sound("Normal_Explosion.ogg");
        modulesList = new ArrayList<>();
        dropTable = new DropTable(its);

        switch (type) {
            case R01:
                sprite = new SpriteSheet("R01Ship_Spritesheet.png", 256, 256).getSprite(skin, 0);
                centerX = 128;
                centerY = 100;
                shieldOffset = 28;
                maxHp = 500;
                maxArmor = 200;
                collisionRadius = 128;

                sprite.setCenterOfRotation(centerX, centerY);
                maxVelocity = 50;
                radarRange = 20000;
                this.mass = 9000;

                BombWeapon w1 = new BombWeapon(BombWeapon.WeaponType.B101, this);
                BulletWeapon w2 = new BulletWeapon(BulletWeapon.WeaponType.M201, this);
                BulletWeapon w3 = new BulletWeapon(BulletWeapon.WeaponType.M201, this);
                w1.setManualPosition(this.getWidth() / 2 - w1.getSprite().getWidth() / 2, -20);
                w2.setManualPosition(10, 60);
                w3.setManualPosition(182, 60);
                modulesList.add(w1);
                modulesList.add(w2);
                modulesList.add(w3);

                RocketEngine e1 = new RocketEngine(RocketEngine.EngineType.PR005);
                e1.setManualPosition(sprite.getCenterOfRotationX() - e1.getSprite().getWidth() / 2, 75);
                modulesList.add(e1);

                Shield s1 = new Shield(Shield.ShieldType.WC1);
                s1.setManualPosition(0, -shieldOffset);
                modulesList.add(s1);

                Radar r2 = new Radar(Radar.RadarType.UXA, this);
                r2.setManualPosition(sprite.getCenterOfRotationX(), sprite.getCenterOfRotationY());
                modulesList.add(r2);

                hasBombs = true;
                hasBullets = true;

                dropTable.addItem(new StackableItem(StackableItem.Resources.COPPER, x, y, 2), 100);
                dropTable.addItem(new StackableItem(StackableItem.Resources.FUEL, x, y, 10), 100);

                dropTable.addItem(new StackableItem(StackableItem.Resources.IRON, x, y, 2), 50);

                break;

            case R02:
                sprite = new SpriteSheet("R02Ship_Spritesheet.png", 256, 256).getSprite(skin, 0);
                centerX = 128;
                centerY = 124;
                shieldOffset = 4;
                maxHp = 500;
                maxArmor = 1000;
                maxVelocity = 150;
                radarRange = 20000;
                this.mass = 20000;
                collisionRadius = 128;

                BulletWeapon w4 = new BulletWeapon(BulletWeapon.WeaponType.M261, this);
                LaserWeapon w5 = new LaserWeapon(LaserWeapon.WeaponType.ZRD, this);
                LaserWeapon w6 = new LaserWeapon(LaserWeapon.WeaponType.ZRD, this);
                w4.setManualPosition(this.getWidth() / 2 - w4.getSprite().getWidth() / 2 + 10, 0);
                w5.setManualPosition(40, 85);
                w6.setManualPosition(152, 85);
                modulesList.add(w4);
                modulesList.add(w5);
                modulesList.add(w6);

                RocketEngine e2 = new RocketEngine(RocketEngine.EngineType.PR005);
                e2.setManualPosition(sprite.getCenterOfRotationX() - e2.getSprite().getWidth() / 2 - 30, 110);
                modulesList.add(e2);
                RocketEngine e3 = new RocketEngine(RocketEngine.EngineType.PR005);
                e3.setManualPosition(sprite.getCenterOfRotationX() - e3.getSprite().getWidth() / 2, 120);
                modulesList.add(e3);
                RocketEngine e4 = new RocketEngine(RocketEngine.EngineType.PR005);
                e4.setManualPosition(sprite.getCenterOfRotationX() - e4.getSprite().getWidth() / 2 + 30, 110);
                modulesList.add(e4);

                Shield s2 = new Shield(Shield.ShieldType.WC1);
                s2.setManualPosition(0, -shieldOffset);
                modulesList.add(s2);

                Radar r1 = new Radar(Radar.RadarType.UXA, this);
                r1.setManualPosition(sprite.getCenterOfRotationX(), sprite.getCenterOfRotationY());
                modulesList.add(r1);

                hasBullets = true;
                hasLasers = true;
                dropTable.addItem(new StackableItem(StackableItem.Resources.COPPER, x, y, 5), 100);
                dropTable.addItem(new StackableItem(StackableItem.Resources.FUEL, x, y, 15), 100);

                dropTable.addItem(new StackableItem(StackableItem.Resources.IRON, x, y, 8), 75);
                dropTable.addItem(new StackableItem(StackableItem.Resources.GOLD, x, y, 2), 50);
                break;

            case STOMPER:
                sprite = new SpriteSheet("StomperShip_Spritesheet.png", 256, 256).getSprite(skin, 0);
                centerX = 128;
                centerY = 100;
                shieldOffset = 28;
                maxHp = 2000;
                maxArmor = 2000;
                collisionRadius = 128;

                sprite.setCenterOfRotation(centerX, centerY);
                maxVelocity = 50;
                radarRange = 20000;
                this.mass = 15000;

                BulletWeapon w13 = new BulletWeapon(BulletWeapon.WeaponType.M221, this);
                BulletWeapon w14 = new BulletWeapon(BulletWeapon.WeaponType.M221, this);
                MissileWeapon w15 = new MissileWeapon(MissileWeapon.WeaponType.L341, this);
                MissileWeapon w16 = new MissileWeapon(MissileWeapon.WeaponType.L341, this);

                w13.setManualPosition(this.getWidth() / 2 - w13.getSprite().getWidth() / 2 - 32, -20);
                w14.setManualPosition(this.getWidth() / 2 - w14.getSprite().getWidth() / 2 + 32, -20);
                w15.setManualPosition(this.getWidth() / 2 - w15.getSprite().getWidth() / 2 - 114, 105);
                w16.setManualPosition(this.getWidth() / 2 - w16.getSprite().getWidth() / 2 + 114, 105);
                modulesList.add(w13);
                modulesList.add(w14);
                modulesList.add(w15);
                modulesList.add(w16);

                RocketEngine e12 = new RocketEngine(RocketEngine.EngineType.PR005);
                e12.setManualPosition(sprite.getCenterOfRotationX() - e12.getSprite().getWidth() / 2 - 20, 75);
                modulesList.add(e12);
                RocketEngine e11 = new RocketEngine(RocketEngine.EngineType.PR005);
                e11.setManualPosition(sprite.getCenterOfRotationX() - e11.getSprite().getWidth() / 2 + 20, 75);
                modulesList.add(e11);

                Shield s6 = new Shield(Shield.ShieldType.WC1);
                s6.setManualPosition(0, -shieldOffset);
                modulesList.add(s6);

                Radar r3 = new Radar(Radar.RadarType.UXA, this);
                r3.setManualPosition(sprite.getCenterOfRotationX(), sprite.getCenterOfRotationY());
                modulesList.add(r3);

                hasMissiles = true;
                hasBullets = true;
                dropTable.addItem(new StackableItem(StackableItem.Resources.IRON, x, y, 5), 100);
                dropTable.addItem(new StackableItem(StackableItem.Resources.FUEL, x, y, 15), 100);

                dropTable.addItem(new StackableItem(StackableItem.Resources.PLATINUM, x, y, 1), 20);
                dropTable.addItem(new StackableItem(StackableItem.Resources.CARBON, x, y, 3), 75);
                break;
            case AFA:
                sprite = new SpriteSheet("AFAShip_Spritesheet.png", 256, 256).getSprite(skin, 0);
                centerX = 128;
                centerY = 128;
                shieldOffset = 0;
                maxHp = 500;
                maxArmor = 500;
                maxVelocity = 100;
                radarRange = 20000;
                this.mass = 3000;
                collisionRadius = 128;

                BulletWeapon w10 = new BulletWeapon(BulletWeapon.WeaponType.M251, this);
                LaserWeapon w11 = new LaserWeapon(LaserWeapon.WeaponType.ZRA, this);
                LaserWeapon w12 = new LaserWeapon(LaserWeapon.WeaponType.ZRA, this);

                w10.setManualPosition(this.getWidth() / 2 - w10.getSprite().getWidth() / 2, 0);
                w11.setManualPosition(64, 100);
                w12.setManualPosition(128, 100);
                modulesList.add(w10);
                modulesList.add(w11);
                modulesList.add(w12);

                IonEngine e10 = new IonEngine(IonEngine.EngineType.ION75);
                e10.setManualPosition(sprite.getCenterOfRotationX() - e10.getSprite().getWidth() / 2, 110);
                modulesList.add(e10);

                Shield s4 = new Shield(Shield.ShieldType.WC1);
                s4.setManualPosition(0, -shieldOffset);
                modulesList.add(s4);

                Radar r4 = new Radar(Radar.RadarType.UXA, this);
                r4.setManualPosition(sprite.getCenterOfRotationX(), sprite.getCenterOfRotationY());
                modulesList.add(r4);

                hasBullets = true;
                hasLasers = true;
                dropTable.addItem(new StackableItem(StackableItem.Resources.FUEL, x, y, 10), 100);

                dropTable.addItem(new StackableItem(StackableItem.Resources.SILVER, x, y, 3), 60);
                dropTable.addItem(new StackableItem(StackableItem.Resources.GOLD, x, y, 2), 50);
                dropTable.addItem(new StackableItem(StackableItem.Resources.PLATINUM, x, y, 1), 30);
                break;
            case ZAX:
                sprite = new SpriteSheet("ZAXShip_Spritesheet.png", 256, 256).getSprite(skin, 0);
                centerX = 128;
                centerY = 140;
                shieldOffset = -12;
                maxHp = 500;
                maxArmor = 100;
                maxVelocity = 200;
                radarRange = 20000;
                this.mass = 5000;
                collisionRadius = 128;
                IonEngine e5 = new IonEngine(IonEngine.EngineType.ION75);
                e5.setManualPosition(sprite.getCenterOfRotationX() - e5.getSprite().getWidth() / 2 - 35, 130);
                modulesList.add(e5);
                IonEngine e6 = new IonEngine(IonEngine.EngineType.ION75);
                e6.setManualPosition(sprite.getCenterOfRotationX() - e6.getSprite().getWidth() / 2 + 35, 130);
                modulesList.add(e6);
                AcidWeapon w7 = new AcidWeapon(AcidWeapon.WeaponType.PX1, this);
                w7.setManualPosition(this.getWidth() / 2 - w7.getSprite().getWidth() / 2, -10);
                modulesList.add(w7);
                Shield s5 = new Shield(Shield.ShieldType.WC1);
                s5.setManualPosition(0, -shieldOffset);
                modulesList.add(s5);

                Radar r5 = new Radar(Radar.RadarType.UXA, this);
                r5.setManualPosition(sprite.getCenterOfRotationX(), sprite.getCenterOfRotationY());
                modulesList.add(r5);

                hasAcid = true;
                dropTable.addItem(new StackableItem(StackableItem.Resources.FUEL, x, y, 50), 100);
                dropTable.addItem(new StackableItem(StackableItem.Resources.POISON, x, y, 50), 100);

                dropTable.addItem(new StackableItem(StackableItem.Resources.URANIUM, x, y, 1), 10);

                break;
            case SKRELCH_CAPITAL:
                sprite = new SpriteSheet("SkrelchCapital_Spritesheet.png", 1024, 2048).getSprite(skin, 0);
                centerX = 512;
                centerY = 1024;
                shieldOffset = 0;
                maxHp = 5000;
                maxArmor = 50000;
                maxVelocity = 30;
                radarRange = 20000;
                this.mass = 10000000;
                collisionRadius = 1024;

                LaserWeapon w9 = new LaserWeapon(LaserWeapon.WeaponType.ZRX, this);
                w9.setManualPosition(this.getWidth() / 2 - w9.getSprite().getWidth() / 2, 250);
                modulesList.add(w9);

                RocketEngine e7 = new RocketEngine(RocketEngine.EngineType.PR005);
                e7.setManualPosition(sprite.getCenterOfRotationX() - e7.getSprite().getWidth() / 2 - 30, 1700);
                modulesList.add(e7);
                RocketEngine e8 = new RocketEngine(RocketEngine.EngineType.PR005);
                e8.setManualPosition(sprite.getCenterOfRotationX() - e8.getSprite().getWidth() / 2, 1700);
                modulesList.add(e8);
                RocketEngine e9 = new RocketEngine(RocketEngine.EngineType.PR005);
                e9.setManualPosition(sprite.getCenterOfRotationX() - e9.getSprite().getWidth() / 2 + 30, 1700);
                modulesList.add(e9);

                Shield s3 = new Shield(Shield.ShieldType.WC1);
                s3.setSprite(s3.getSprite().getScaledCopy(8f));
                s3.setManualPosition(-sprite.getWidth() / 2, -shieldOffset);
                modulesList.add(s3);
                hasLasers = true;
                dropTable.addItem(new StackableItem(StackableItem.Resources.FUEL, x, y, 200), 100);

                dropTable.addItem(new StackableItem(StackableItem.Resources.IRON, x, y, 60), 100);
                dropTable.addItem(new StackableItem(StackableItem.Resources.COPPER, x, y, 30), 100);
                dropTable.addItem(new StackableItem(StackableItem.Resources.SILVER, x, y, 25), 100);
                dropTable.addItem(new StackableItem(StackableItem.Resources.PLATINUM, x, y, 15), 40);
                dropTable.addItem(new StackableItem(StackableItem.Resources.URANIUM, x, y, 10), 20);
                break;

        }
        colCenterX = (float) (x + centerX);
        colCenterY = (float) (y + centerY);
        sprite.setCenterOfRotation(centerX, centerY);
        currentHp = maxHp;
        currentArmor = maxArmor;

        sprite.setFilter(Image.FILTER_LINEAR);

        for (Module module : modulesList) {
            this.mass += module.getMass();
            if (module instanceof RocketEngine) {
                RocketEngine reg = (RocketEngine) module;

                psy.addEmitter(reg.getEmitter());
                maxFuel += reg.getLeftFuel();

            }
            if (module instanceof IonEngine) {
                IonEngine ieg = (IonEngine) module;
                psy.addEmitter(ieg.getEmitter());
            }

            if (module instanceof AcidWeapon) {
                AcidWeapon acd = (AcidWeapon) module;

                psy.addEmitter(acd.getEmitter());

                maxBullets += acd.getAmmunition();

            }
            if (module instanceof Radar) {
                Radar rdr = (Radar) module;
                radarRange = rdr.getRadarRange();
            }

            if (module instanceof BulletWeapon) {
                BulletWeapon bw = (BulletWeapon) module;
                maxBullets += bw.getAmmunition();
            }
            if (module instanceof Shield) {
                Shield sd = (Shield) module;
                maxShield += sd.getCurrentBarrier();
            }

        }
        fuel = maxFuel;
        bullets = maxBullets;
        currentShield = maxShield;
        hpBar = new LifeBar(this, 0, -50);
        inventory = new Inventory((float) (this.mass / 2), 0);
        if (!(this instanceof PlayerShip)) {
                if (this.getRadar()!=null){
                this.getRadar().setScanEnabled(false);
                }
            }

    }

    /**
     * Active l'intelligence artificielle des ennemis.
     *
     * @param activeShips
     */
    public void activateAI(ArrayList<Ship> activeShips) {
        ai = new EnemyAI(activeShips, this, Race.HUMAN, Skillset.WARRIOR, Special.AGGRESSIVE);
    }

    /**
     * Fait ralentir le vaisseau instantanément. Méthode utilisée pour des
     * tests, devrait être dépréciée.
     *
     * @param delta
     */
    public void brake(float delta) {
        velocity.scale((float) 0.95);
    }

    /**
     * Fait ralentir le vaisseau lentement. Méthode utilisée pour des tests,
     * devrait être dépréciée.
     *
     * @param delta
     */
    public void softBrake(float delta) {
        velocity.scale((float) 0.9999);
    }

    /**
     * Dessin du vaisseau, et de son interface associée.
     */
    @Override
    public void draw() {
        if (!isDestroyed) {

            if (!modulesList.isEmpty()) {

                for (Module module : modulesList) {
                    if (!(module instanceof Shield)) {
                        module.getSprite().setCenterOfRotation(centerX - module.getRelX(), centerY - module.getRelY());
                        module.getSprite().setRotation((float) angle);
                        module.draw((float) x, (float) y);
                    }

                }
            }
            sprite.setRotation((float) angle);
            sprite.draw((float) x, (float) y);
            if (!modulesList.isEmpty()) {

                for (Module module : modulesList) {
                    if (module instanceof Shield) {
                        module.getSprite().setCenterOfRotation(centerX - module.getRelX(), centerY - module.getRelY());
                        module.getSprite().setRotation((float) angle);
                        module.draw((float) x, (float) y);
                    }

                }
            }

            hpBar.draw((float) x, (float) y);
            
        }
    }

    /**
     *
     * @return La liste des modules installés sur le vaisseau.
     */
    public ArrayList<Module> getModulesList() {
        return modulesList;
    }

    /**
     * Mise à jour du vaisseau. Méthode complexe qui gère les changements
     * apportés à la physique du vaisseau.
     *
     * @param force La force à appliquer au vaisseau, en Newtons.
     * @param angularSpeed La vitesse angulaire.
     * @param delta Le ratio de mise à jour sur 60 IPS.
     * @param psy Le système de particules.
     * @param prs Le système de projectiles.
     */
    @Override
    public void update(float force, float angularSpeed, float delta, ParticleSystem psy, ProjectileSystem prs) {

        if (!isDestroyed) {
            float accTot;
            float accX;
            float accY;
            this.angularSpeed = angularSpeed;

            angle += (angularSpeed * delta) % 360;
            accTot = (float) (force / mass);
            accX = (float) (accTot * Math.cos(Math.toRadians(angle - 90)) * delta);
            accY = (float) (accTot * Math.sin(Math.toRadians(angle - 90)) * delta);

            acceleration.set(accX, accY);

            velocity.add(acceleration);

            if (velocity.length() > maxVelocity) {
                velocity.scale(maxVelocity / velocity.length());
            }

            x += velocity.getX() * delta;
            y += velocity.getY() * delta;
            colCenterX = (float) (x + centerX);
            colCenterY = (float) (y + centerY);
            ///
            fuel = 0;
            bullets = 0;
            currentShield = 0;

            if (!modulesList.isEmpty()) {

                for (Module module : modulesList) {

                    if (module instanceof RocketEngine) {
                        RocketEngine reg = (RocketEngine) module;
                        reg.update(psy, delta, angle, thrustersOn, this);
                        if (reg.getLeftFuel() > 0) {
                            fuel += reg.getLeftFuel();

                        } else {
                            reg.getEmitter().setEnabled(false);
                            psy.removeEmitter(reg.getEmitter());
                        }

                    }
                    if (module instanceof IonEngine) {
                        IonEngine ieg = (IonEngine) module;
                        ieg.update(psy, delta, angle, thrustersOn, this);
                    }

                    if (module instanceof AcidWeapon) {
                        AcidWeapon acd = (AcidWeapon) module;
                        if (acd.getPrs() == null) {
                            acd.setPrs(prs);
                        }
                        if (acd.getAmmunition() > 0) {
                            bullets += acd.getAmmunition();
                        }

                        acd.update(psy, delta, angle, this);

                    }

                    if (module instanceof BulletWeapon) {
                        BulletWeapon bw = (BulletWeapon) module;
                        if (bw.getAmmunition() > 0) {
                            bullets += bw.getAmmunition();
                        }

                    }
                    if (module instanceof Shield) {
                        Shield sd = (Shield) module;
                        if (currentShield < maxShield) {
                            currentShield += sd.getCurrentBarrier();
                            sd.regenerate(delta);
                        }
                    }
                    if (module instanceof Radar) {

                        Radar rdr = (Radar) module;
                        rdr.update(prs, delta);
                    }
                }
            }
            if (ai instanceof EnemyAI) {
                ai.update(delta, prs);
            }
        }

    }

    /**
     *
     * @return Le carburant restant du vaisseau.
     */
    public float getFuel() {
        return fuel;
    }

    /**
     *
     * @return Le nombre de munitions d'arme primaire restantes.
     */
    public int getBullets() {
        return bullets;
    }

    /**
     *
     * @param thrustersOn Allume ou éteint les réacteurs du vaisseau.
     */
    public void setThrustersOn(int thrustersOn) {
        this.thrustersOn = thrustersOn;
    }

    /**
     *
     * @return La force délivrée par les réacteurs du vaisseau selon leur état.
     */
    public float getForce() {
        float force = 0;
        if (thrustersOn != 0) {
            if (!modulesList.isEmpty()) {

                for (Module engine : modulesList) {

                    if (engine instanceof RocketEngine) {
                        RocketEngine reg = (RocketEngine) engine;
                        force += reg.getForce();
                    }
                    if (engine instanceof IonEngine) {
                        IonEngine ieg = (IonEngine) engine;
                        force += ieg.getForce();
                    }
                }
            }

        }
        return force;
    }

    /**
     *
     * @return La force maximale délivrée par les réacteurs du vaisseau.
     */
    public float getMaxForce() {
        float force = 0;

        if (!modulesList.isEmpty()) {

            for (Module engine : modulesList) {

                if (engine instanceof RocketEngine) {
                    RocketEngine reg = (RocketEngine) engine;
                    force += reg.getForce();
                }
            }
        }

        return force;
    }

    /**
     * Tire les armes principales du vaisseau (fusils ou acide).
     *
     * @param multi Le ratio sur 60 IPS.
     * @param prs Le système de projectiles.
     */
    public void firePrimary(float multi, ProjectileSystem prs) {

        for (Module module : modulesList) {

            if (module instanceof BulletWeapon) {
                BulletWeapon bw = (BulletWeapon) module;

                bw.fire(multi, prs, psy);

            }
            if (module instanceof AcidWeapon) {
                AcidWeapon acd = (AcidWeapon) module;

                acd.fire(multi, prs, psy);

            }
        }
    }

    /**
     * Tire les armes secondaires du vaisseau (lasers, missiles ou bombes).
     *
     * @param multi Le ratio sur 60 IPS.
     * @param prs Le système de projectiles.
     */
    public void fireSecondary(float multi, ProjectileSystem prs) {

        for (Module module : modulesList) {
            if (module instanceof LaserWeapon) {
                LaserWeapon lw = (LaserWeapon) module;

                lw.fire(multi, prs, psy);

            }
            if (module instanceof MissileWeapon) {
                MissileWeapon mw = (MissileWeapon) module;

                mw.fire(multi, prs, psy);

            }
            if (module instanceof BombWeapon) {
                BombWeapon bw = (BombWeapon) module;

                bw.fire(multi, prs, psy);

            }
        }
    }

    /**
     * Arrêt du tir des lasers. Méthode utilisée à des fins techniques.
     *
     * @param prs Système de projectiles.
     */
    public void stopLaserFire(ProjectileSystem prs) {
        for (Module module : modulesList) {
            if (module instanceof LaserWeapon) {
                LaserWeapon lw = (LaserWeapon) module;
                lw.stopFire(prs);

            }
        }
    }

    /**
     * Arrêt du tir d'acide. Méthode utilisée à des fins techniques.
     */
    public void stopAcidFire() {
        for (Module module : modulesList) {
            if (module instanceof AcidWeapon) {
                AcidWeapon acd = (AcidWeapon) module;
                acd.stopFire();

            }
        }
    }

    /**
     *
     * @return La vitesse angulaire du vaisseau.
     */
    public double getAngularSpeed() {
        return angularSpeed;
    }

    /**
     *
     * @return Le décalage du bouclier.
     */
    public float getShieldOffset() {
        return shieldOffset;
    }

    /**
     *
     * @return La vie maximale du vaisseau.
     */
    public float getMaxHp() {
        return maxHp;
    }

    /**
     *
     * @return La vie actuelle du vaisseau.
     */
    public float getCurrentHp() {
        return currentHp;
    }

    /**
     *
     * @return Le carburant maximal du vaisseau.
     */
    public float getMaxFuel() {
        return maxFuel;
    }

    /**
     *
     * @return Les munitions maximales du vaisseau.
     */
    public int getMaxBullets() {
        return maxBullets;
    }

    /**
     *
     * @return Le bouclier maximal.
     */
    public float getMaxShield() {
        return maxShield;
    }

    /**
     *
     * @return Le bouclier actuel du vaisseau.
     */
    public float getCurrentShield() {
        return currentShield;
    }

    /**
     * Endommage le bouclier du vaisseau.
     *
     * @param puissance La puissance avec laquelle endommager le bouclier.
     */
    public void damageShield(float puissance) {

        for (Module module : modulesList) {

            if (module instanceof Shield) {
                Shield sd = (Shield) module;
                sd.resetHit();
                this.currentShield = currentShield - (5 - sd.getAbsorption()) * puissance;
                sd.setCurrentBarrier(currentShield);

            }
        }

    }

    /**
     * Réduit la vie et l'armure du vaisseau.
     *
     * @param puissance La puissance avec laquelle endommager le vaisseau.
     */
    public void damageHP(int puissance) {

        if (this.currentShield <= 0) {
            if (this.currentArmor > 0) {
                this.currentArmor = currentArmor - puissance;
                this.currentHp = currentHp - 0.05f * puissance;

            } else {
                this.currentHp = currentHp - puissance;
            }

        }
        if (this.currentHp <= 0) {
            this.currentHp = 0;
            isDestroyed = true;
        }

    }

    /**
     * Détruit le vaisseau.
     *
     * @param psy Le système de particules.
     * @param prs Le système de projectiles.
     */
    public void destroy(ParticleSystem psy, ProjectileSystem prs) {
        dropTable.dropItem(this);
        if (this.getRadar() != null) {
            this.getRadar().setScanEnabled(false);
        }
        this.currentHp = 0;
        this.currentShield = 0;
        this.fuel = 0;
        this.bullets = 0;
        this.velocity.sub(velocity);
        stopLaserFire(prs);
        stopAcidFire();
        for (Module module : modulesList) {
            if (module instanceof RocketEngine) {
                RocketEngine re = (RocketEngine) module;
                re.getEmitter().setEnabled(false);
                psy.removeEmitter(re.getEmitter());
            }
            if (module instanceof IonEngine) {
                IonEngine ie = (IonEngine) module;
                ie.getEmitter().setEnabled(false);
                psy.removeEmitter(ie.getEmitter());
            }
            module = null;
        }
        this.modulesList.clear();
        ExplosionEmitter explosionemit = new ExplosionEmitter((int) colCenterX, (int) colCenterY, 200);
        psy.addEmitter(explosionemit);
        explosion.play();

    }

    /**
     *
     * @return La vitesse maximale du vaisseau.
     */
    public float getMaxVelocity() {
        return maxVelocity;
    }

    /**
     *
     * @return La portée du radar du vaisseau.
     */
    public float getRadarRange() {
        return radarRange;
    }

    /**
     *
     * @return L'intelligence artificielle du vaisseau.
     */
    public BaseAI getAI() {
        return ai;
    }

    /**
     *
     * @return L'armure maximale du vaisseau.
     */
    public float getMaxArmor() {
        return maxArmor;
    }

    /**
     *
     * @return L'armure du vaisseau.
     */
    public float getCurrentArmor() {
        return currentArmor;
    }

    /**
     *
     * @return La portée des lasers du vaisseau.
     */
    public float getLaserRange() {
        float range = 0;
        for (Module module : modulesList) {
            if (module instanceof LaserWeapon) {
                LaserWeapon lw = (LaserWeapon) module;
                if (lw.getRange() > range) {
                    range = lw.getRange();

                }

            }
        }
        return range;
    }

    /**
     *
     * @return La faction du vaisseau.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Réduit le temps de tir des armes secondaires à longue recharge (missiles
     * et bombes).
     *
     * @param delta Le ratio sur 60 IPS.
     */
    public void cooldown(float delta) {
        for (Module module : modulesList) {
            if (module instanceof MissileWeapon) {
                MissileWeapon mw = (MissileWeapon) module;
                mw.cooldown(delta);

            }
            if (module instanceof BombWeapon) {
                BombWeapon bw = (BombWeapon) module;
                bw.cooldown(delta);

            }
        }

    }

    /**
     *
     * @return Si le vaisseau a de l'acide.
     */
    public boolean hasAcid() {
        return hasAcid;
    }

    /**
     *
     * @return Si le vaisseau a des fusils.
     */
    public boolean hasBullets() {
        return hasBullets;
    }

    /**
     *
     * @return Si le vaisseau a des lasers.
     */
    public boolean hasLasers() {
        return hasLasers;
    }

    /**
     *
     * @return Si le vaisseau a des missiles.
     */
    public boolean hasMissiles() {
        return hasAcid;
    }

    /**
     *
     * @return Si le vaisseau a des bombes.
     */
    public boolean hasBombs() {
        return hasAcid;
    }

    /**
     *
     * @return L'inventaire du vaisseau.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     *
     * @return Le radar du vaisseau.
     */
    public Radar getRadar() {
        Radar radar = null;
        for (Module module : modulesList) {
            if (module instanceof Radar) {
                radar = (Radar) module;
            }
        }
        return radar;
    }

}
