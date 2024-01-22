package org.example;

import entity.Entity;
import object.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    private Font arial_40, arial_80B;
    private BufferedImage heart_full, heart_half, heart_blank, manacrystal_full, manacrystal_blank, coin;

    private ArrayList<String> message = new ArrayList<>();
    private ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public int commandNum = 0;
    int titleScreenState = 0;
    public int playerSlotRow = 0;
    public int playerSlotCol = 0;
    int npcSlotRow = 0;
    int npcSlotCol = 0;
    int substate = 0;
    private int counter = 0;
    public Entity npc;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B= new Font("Arial", Font.BOLD, 80);

        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity manaCrystal = new OBJ_Mana(gp);
        manacrystal_full = manaCrystal.image;
        manacrystal_blank = manaCrystal.image2;
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
    }

    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }
        if(gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        if(gp.gameState == gp.transitionState){
            drawTransition();
        }
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
    }
    public void drawPlayerLife(){
//        gp.player.life = 5;
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
        x = gp.tileSize/2-5;
        y = (int)(gp.tileSize*1.5);
        i = 0;
        while (i < gp.player.maxMana){
            g2.drawImage(manacrystal_blank, x ,y, null);
            i++;
            x += 35;
        }
        x = gp.tileSize/2-5;
        y = (int)(gp.tileSize*1.5);
        i = 0;
        while (i < gp.player.mana){
            g2.drawImage(manacrystal_full, x ,y, null);
            i++;
            x += 35;
        }
    }
    private void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        for(int i = 0; i < message.size(); i++){
            if(message.get(i) != null){
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    private void drawTitleScreen(){
        if(titleScreenState == 0){
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,96F));
            String text = "SEMESTRALKA";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);

            g2.setColor(Color.white);
            g2.drawString(text,x,y);

            x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y+= gp.tileSize*3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y+= gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y+= gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }
        }
        else if(titleScreenState == 1){
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "SELECT YOUR CLASS";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "WARRIOR";
            x = getXforCenteredText(text);
            y+= gp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "ROGUE";
            x = getXforCenteredText(text);
            y+= gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "WIZARD";
            x = getXforCenteredText(text);
            y+= gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "BACK";
            x = getXforCenteredText(text);
            y+= gp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-gp.tileSize, y);
            }
        }

    }
    private void drawPauseScreen(){

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    private void drawDialogueScreen(){
        int x = gp.tileSize*3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*6);
        int height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y+= 40;
        }

    }


    private void drawCharacterScreen(){
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth,frameHeight);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(30F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defence", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        int tailX = frameX + frameWidth - 30;
        textY = frameY + gp.tileSize;
        String value;
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defence);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coins);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-24, null);

    }

    private void drawInventory(Entity entity, boolean cursor){
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player){
            frameX = gp.tileSize*12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
            drawSubWindow(frameX,frameY, frameWidth,frameHeight);
            final int slotXstart = frameX + 20;
            final int slotYstart = frameY + 20;
            int slotX = slotXstart;
            int slotY = slotYstart;
            int slotSize = gp.tileSize+3;

            for(int i = 0; i < gp.player.inventory.size(); i++){
                if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
                        gp.player.inventory.get(i) == gp.player.currentShield){
                    g2.setColor(new Color(240,190,90));
                    g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10 );
                }
                g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
                if(gp.player.inventory.get(i).amount > 1){
                    g2.setFont(g2.getFont().deriveFont(32f));
                    int amountX;
                    int amountY;
                    String s = "" + gp.player.inventory.get(i).amount;
                    amountX = getXforAlignToRightText(s, slotX + 50);
                    amountY = slotY + gp.tileSize;

                    g2.setColor(new Color(70,50,50));
                    g2.drawString(s,amountX,amountY);
                    g2.setColor(Color.white);
                    g2.drawString(s, amountX-3, amountY-3);
                }
                slotX += slotSize;
                if( i == 4 || i == 9 || i == 14){
                    slotX = slotXstart;
                    slotY += slotSize;
                }
            }

            if(cursor == true){
                int cursorX = slotXstart + (slotSize * slotCol);
                int cursorY = slotYstart + (slotSize * slotRow);
                int cursorWidth = gp.tileSize;
                int cursorHeight = gp.tileSize;

                g2.setColor(Color.white);
                g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

                int dFrameX = frameX;
                int dFrameY = frameY + frameHeight;
                int dFrameHeight = gp.tileSize*3;
                int dFrameWidth = frameWidth;

                int textY = dFrameY + gp.tileSize;
                int textX = dFrameX + 20;
                g2.setFont(g2.getFont().deriveFont(24F));
                int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
                if(itemIndex < gp.player.inventory.size()){
                    drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                    for(String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
                        g2.drawString(line, textX, textY);
                        textY += 32;
                    }
                }
            }
        } else {
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
            drawSubWindow(frameX,frameY, frameWidth,frameHeight);
            final int slotXstart = frameX + 20;
            final int slotYstart = frameY + 20;
            int slotX = slotXstart;
            int slotY = slotYstart;
            int slotSize = gp.tileSize+3;

            for(int i = 0; i < entity.inventory.size(); i++){
                if(entity.inventory.get(i) == entity.currentWeapon ||
                        entity.inventory.get(i) == entity.currentShield){
                    g2.setColor(new Color(240,190,90));
                    g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10 );
                }
                g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
                slotX += slotSize;
                if( i == 4 || i == 9 || i == 14){
                    slotX = slotXstart;
                    slotY += slotSize;
                }
            }

            if(cursor == true){
                int cursorX = slotXstart + (slotSize * slotCol);
                int cursorY = slotYstart + (slotSize * slotRow);
                int cursorWidth = gp.tileSize;
                int cursorHeight = gp.tileSize;

                g2.setColor(Color.white);
                g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

                int dFrameX = frameX;
                int dFrameY = frameY + frameHeight;
                int dFrameHeight = gp.tileSize*3;
                int dFrameWidth = frameWidth;

                int textY = dFrameY + gp.tileSize;
                int textX = dFrameX + 20;
                g2.setFont(g2.getFont().deriveFont(24F));
                int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
                if(itemIndex < entity.inventory.size()){
                    drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                    for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
                        g2.drawString(line, textX, textY);
                        textY += 32;
                    }
                }
            }
        }




    }
    private void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        //Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);
        //Main text
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.drawString(">", x-40, y);
        }

        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">", x-40, y);
        }
    }
    private void drawOptionsScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(28F));
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (substate){
            case 0: options_top(frameX, frameY); break;
//            case 1:break;// code with full screen
            case 1: options_control(frameX, frameY);break;
            case 2: options_endGameConfirmation(frameX, frameY); break;

        }
        gp.keyH.enterPressed = false;
    }
    private void options_top(int frameX, int frameY){
        int textX;
        int textY;

        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        textX = frameX + gp.tileSize;
//        textY += gp.tileSize*2;
//        g2.drawString("Full Screen", textX, textY);
//        if(commandNum == 0){
//            g2.drawString(">", textX-25, textY);
//        }

        textY += gp.tileSize*2;
        g2.drawString("Control", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                substate = 1;
                commandNum = 0;
            }
        }

        textY += gp.tileSize;
        g2.drawString("End game", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                substate = 2;
                commandNum = 0;
            }
        }

        textY += gp.tileSize*5;
        g2.drawString("Back", textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }


    }
    private void options_control(int frameX, int frameY){
        int textX;
        int textY;
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Character screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("Enter", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("Esc", textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                substate = 0;
                commandNum = 0;
            }
        }
    }
    private void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX,textY);
            textY += 40;
        }
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                substate = 0;
                gp.gameState = gp.titleState;
                titleScreenState = 0;
                gp.resetGame(true);
            }
        }

        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                substate = 0;
                commandNum = 1;
            }
        }
    }
    public int getItemIndexOnSlot(int SlotCol, int SlotRow){
        int itemIndex = SlotCol + SlotRow*5;
        return itemIndex;
    }
    private void drawTransition(){
        counter++;
        g2.setColor(new Color(0,0,0, counter*5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }
    private void drawTradeScreen(){
        switch (substate){
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
        }
        gp.keyH.enterPressed = false;
    }
    private void trade_select(){
        drawDialogueScreen();
        int x = gp.tileSize*15;
        int y = gp.tileSize*4;
        int width = gp.tileSize*3;
        int height = (int)(gp.tileSize*3.5);
        drawSubWindow(x,y,width,height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString(">", x-24,y);
            if(gp.keyH.enterPressed == true){
                substate = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNum == 1){
            g2.drawString(">", x-24,y);
            if(gp.keyH.enterPressed == true){
                substate = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if(commandNum == 2){
            g2.drawString(">", x-24,y);
            if(gp.keyH.enterPressed == true){
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "See u";
            }
        }
        y += gp.tileSize;
    }
    private void trade_buy(){
        drawInventory(gp.player, false);
        drawInventory(npc, true);

        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x+24, y+60);

        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your coins: " + gp.player.coins,x+24, y+60);

        int itemIndex = getItemIndexOnSlot(npcSlotCol,npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width, height);
            g2.drawImage(coin, x+10, y+8,32,32,null);
            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text,gp.tileSize*8-20);
            g2.drawString(text,x,y+34);

            if(gp.keyH.enterPressed == true){
                if(npc.inventory.get(itemIndex).price > gp.player.coins){
                    substate = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "U dont have enough dough";
                    drawDialogueScreen();
                }
                else{
                    if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true){
                        gp.player.coins -= npc.inventory.get(itemIndex).price;
                    }
                    else {
                        substate = 0;
                        gp.gameState = gp.dialogueState;
                        currentDialogue = "Your bag is full";
                    }
                }

            }
        }
    }
    private void trade_sell(){
        drawInventory(gp.player, true);

        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x+24, y+60);

        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your coins: " + gp.player.coins,x+24, y+60);

        int itemIndex = getItemIndexOnSlot(playerSlotCol,playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){
            x = (int)(gp.tileSize*15.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width, height);
            g2.drawImage(coin, x+10, y+8,32,32,null);
            int price = gp.player.inventory.get(itemIndex).price/2;
            String text = "" + price;
            x = getXforAlignToRightText(text,gp.tileSize*18-20);
            g2.drawString(text,x,y+34);

            if(gp.keyH.enterPressed == true){
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                        gp.player.inventory.get(itemIndex) == gp.player.currentShield){
                    commandNum = 0;
                    substate = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You cant sell what u wear";
                }
                else{
                    if(gp.player.inventory.get(itemIndex).amount > 1){
                        gp.player.inventory.get(itemIndex).amount--;
                    }
                    else {
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coins += price;
                }
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    private void drawSubWindow(int x,int y,int width,int height){
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x+3,y+3,width-6,height-6,25,25);
    }
    private int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    private int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
