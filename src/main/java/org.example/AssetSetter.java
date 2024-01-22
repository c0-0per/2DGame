package org.example;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import object.*;


public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    /**
     * create items/objects
     */
    public void setObject(){
        int mapNum = 0;
//        gp.obj[mapNum][0] = new OBJ_Key(gp);
//        gp.obj[mapNum][0].worldX = gp.tileSize*25;
//        gp.obj[mapNum][0].worldY = gp.tileSize*23;
//
        gp.obj[mapNum][1] = new OBJ_Key(gp);
        gp.obj[mapNum][1].worldX = gp.tileSize*25;
        gp.obj[mapNum][1].worldY = gp.tileSize*42;

//        gp.obj[mapNum][2] = new OBJ_Key(gp);
//        gp.obj[mapNum][2].worldX = gp.tileSize*25;
//        gp.obj[mapNum][2].worldY = gp.tileSize*23;


        gp.obj[mapNum][2] = new OBJ_Axe(gp);
        gp.obj[mapNum][2].worldX = gp.tileSize*36;
        gp.obj[mapNum][2].worldY = gp.tileSize*43;

//        gp.obj[mapNum][3] = new OBJ_Shield_Diamond(gp);
//        gp.obj[mapNum][3].worldX = gp.tileSize*35;
//        gp.obj[mapNum][3].worldY = gp.tileSize*21;

        gp.obj[mapNum][3] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][3].worldX = gp.tileSize*22;
        gp.obj[mapNum][3].worldY = gp.tileSize*27;

        gp.obj[mapNum][4] = new OBJ_Door(gp);
        gp.obj[mapNum][4].worldX = gp.tileSize*14;
        gp.obj[mapNum][4].worldY = gp.tileSize*28;

        gp.obj[mapNum][5] = new OBJ_Door(gp);
        gp.obj[mapNum][5].worldX = gp.tileSize*12;
        gp.obj[mapNum][5].worldY = gp.tileSize*12;

        gp.obj[mapNum][6] = new OBJ_Chest(gp);
        gp.obj[mapNum][6].setLoot(new OBJ_Potion_Red(gp));
        gp.obj[mapNum][6].worldX = gp.tileSize*12;
        gp.obj[mapNum][6].worldY = gp.tileSize*9;

//        gp.obj[mapNum][7] = new OBJ_Chest(gp);
//        gp.obj[mapNum][7].setLoot(new OBJ_Key(gp));
//        gp.obj[mapNum][7].worldX = gp.tileSize*17;
//        gp.obj[mapNum][7].worldY = gp.tileSize*20;
//
//        gp.obj[mapNum][8] = new OBJ_Chest(gp);
//        gp.obj[mapNum][8].setLoot(new OBJ_Potion_Red(gp));
//        gp.obj[mapNum][8].worldX = gp.tileSize*16;
//        gp.obj[mapNum][8].worldY = gp.tileSize*20;




    }

    /**
     * create npc
     */
    public void setNPC(){
        int mapNum = 0;
        gp.npc[mapNum][0] = new NPC_OldMan(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize*21;
        gp.npc[mapNum][0].worldY = gp.tileSize*21;

        mapNum++;
        gp.npc[mapNum][0] = new NPC_Merchant(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize*12;
        gp.npc[mapNum][0].worldY = gp.tileSize*7;;


    }

    /**
     * create monsters
     */
    public void setMonster(){
        int mapNum = 0;
        gp.monster[mapNum][0] = new MON_GreenSlime(gp);
        gp.monster[mapNum][0].worldX = gp.tileSize*23;
        gp.monster[mapNum][0].worldY = gp.tileSize*36;

        gp.monster[mapNum][1] = new MON_GreenSlime(gp);
        gp.monster[mapNum][1].worldX = gp.tileSize*24;
        gp.monster[mapNum][1].worldY = gp.tileSize*37;

        gp.monster[mapNum][2] = new MON_GreenSlime(gp);
        gp.monster[mapNum][2].worldX = gp.tileSize*34;
        gp.monster[mapNum][2].worldY = gp.tileSize*42;

        gp.monster[mapNum][3] = new MON_GreenSlime(gp);
        gp.monster[mapNum][3].worldX = gp.tileSize*38;
        gp.monster[mapNum][3].worldY = gp.tileSize*42;

        gp.monster[mapNum][4] = new MON_Orc(gp);
        gp.monster[mapNum][4].worldX = gp.tileSize*12;
        gp.monster[mapNum][4].worldY = gp.tileSize*32;

        mapNum++;
        mapNum++;
        gp.monster[mapNum][0] = new MON_Orc(gp);
        gp.monster[mapNum][0].worldX = gp.tileSize*24;
        gp.monster[mapNum][0].worldY = gp.tileSize*37;

        gp.monster[mapNum][1] = new MON_GreenSlime(gp);
        gp.monster[mapNum][1].worldX = gp.tileSize*23;
        gp.monster[mapNum][1].worldY = gp.tileSize*36;

        gp.monster[mapNum][2] = new MON_GreenSlime(gp);
        gp.monster[mapNum][2].worldX = gp.tileSize*24;
        gp.monster[mapNum][2].worldY = gp.tileSize*36;
    }
}
