package data;

import data.DataStorage;
import entity.Entity;
import object.*;
import org.example.GamePanel;

import javax.swing.*;
import java.io.*;
import java.lang.runtime.ObjectMethods;
import java.util.logging.Logger;

public class SaveLoad {
    GamePanel gp;
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());


    public SaveLoad(GamePanel gp) {
        this.gp = gp;

    }
    private Entity getObject(String itemName){
        Entity obj = null;

        switch (itemName){
            case"Axe":obj = new OBJ_Axe(gp);break;
            case"Red Potion":obj = new OBJ_Potion_Red(gp);break;
            case"Key":obj = new OBJ_Key(gp);break;
            case"Shield":obj = new OBJ_Shield(gp);break;
            case"Normal Sword":obj = new OBJ_Sword_Normal(gp);break;
            case"Chest":obj = new OBJ_Chest(gp);break;
            case"Spear":obj = new OBJ_Spear(gp);break;
            case"Door":obj = new OBJ_Door(gp);break;
        }
        return obj;
    }
    public void save(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("stats.dat")));
            DataStorage ds = new DataStorage();
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coins = gp.player.coins;
            ds.heroClass = gp.player.heroClass;

            for(int i = 0; i < gp.player.inventory.size(); i++){
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmounts.add(gp.player.inventory.get(i).amount);
            }
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();

            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean [gp.maxMap][gp.obj[1].length];

            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++){
                for(int i = 0; i < gp.obj[1].length; i++){
                    if(gp.obj[mapNum][i] == null){
                        ds.mapObjectNames[mapNum][i] = "NA";
                    }
                    else{
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                        if(gp.obj[mapNum][i].loot != null){
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }


            oos.writeObject(ds);
        }
        catch (IOException e){
            logger.warning("NO FILE TO SAVE");
        }
    }
    public void load(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("stats.dat")));
            DataStorage ds = (DataStorage)ois.readObject();
            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coins = ds.coins;
            gp.player.heroClass = ds.heroClass;



            gp.player.inventory.clear();
            for(int i = 0; i < ds.itemNames.size(); i++){
                    gp.player.inventory.add(getObject(ds.itemNames.get(i)));
                    gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
            }
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);

            gp.player.getAttack();
            gp.player.getDefence();
            gp.player.getAttackImage();

            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++){
                for(int i = 0; i < gp.obj[1].length; i++){
                    if(ds.mapObjectNames[mapNum][i].equals("NA")){
                        gp.obj[mapNum][i] = null;
                    }
                    else {
                        gp.obj[mapNum][i] = getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                        if(ds.mapObjectNames[mapNum][i] != null){
                            gp.obj[mapNum][i].loot = getObject(ds.mapObjectNames[mapNum][i]);
                        }
                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                        if(gp.obj[mapNum][i].opened == true){
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                        }
                    }
                }
            }


        }
        catch (Exception e){
            logger.warning("NO FILE TO LOAD");
            gp.player.setDefaultValues();
        }
    }
}
