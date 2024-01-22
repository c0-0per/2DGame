package org.example;

import entity.Entity;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col< gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }


    }

    /**
     * set healing pool and traps on the map
     */
    public void checkEvent(){
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if(canTouchEvent == true){
            if(hit(0,23,12,"up") == true){
                healingPool( gp.dialogueState);
            }
            else if(hit(0,38,10,"any") == true){
                teleport(1,12,13);
            }
            else if(hit(1,12,13,"any") == true){
                teleport(0,38,10);
            }
            else if(hit(0,10,39,"any") == true){
                teleport(2,10,39);
            }
            else if(hit(2,10,39,"any") == true){
                teleport(0,10,39);
            }
            else if(hit(1,12,9,"up") == true){
                speak(gp.npc[1][0]);
            }
        }

    }

    /**
     * where player stays
     * @param eventCol
     * @param eventRow
     * @param reqDirection (direction moving object(player))
     * @return (true if stay in eventCol, eventRow coordinates and if need use reqDirection, false if not)
     */
    private boolean hit(int map, int eventCol, int eventRow, String reqDirection){
        boolean hit = false;
        if(map == gp.currentMap){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][eventCol][eventRow].x = eventCol*gp.tileSize + eventRect[map][eventCol][eventRow].x;
            eventRect[map][eventCol][eventRow].y = eventRow*gp.tileSize + eventRect[map][eventCol][eventRow].y;

            if(gp.player.solidArea.intersects(eventRect[map][eventCol][eventRow]) && eventRect[map][eventCol][eventRow].eventDone == false){
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][eventCol][eventRow].x = eventRect[map][eventCol][eventRow].eventRectDefaultX;
            eventRect[map][eventCol][eventRow].y = eventRect[map][eventCol][eventRow].eventRectDefaultY;
        }


        return hit;
    }


    /**
     * what is healing pool
     * @param gameState (change gamestate to @param gameState)
     */
    public void healingPool(int gameState){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.ui.currentDialogue = "U drink the water\nYour life has been recovered\nSave point";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
        }
    }
    private void teleport(int map, int col, int row){
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
    }
    private void speak(Entity entity){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }

}
