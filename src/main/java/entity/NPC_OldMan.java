package entity;

import org.example.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{
    public NPC_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    /**
     * images
     */
    public void getImage(){

        up1 = setup("npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("npc/oldman_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("npc/oldman_right_2", gp.tileSize, gp.tileSize);
        left1 = setup("npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("npc/oldman_left_2", gp.tileSize, gp.tileSize);
    }

    /**
     * dialogues of npc
     */
    public void setDialogue(){
        dialogues[0] = "Hello, Vlad.";
        dialogues[1] = "So u`ve come to this island to \nfind the treasure? ";
        dialogues[2] = "I used to be great wizard but now... \nI`m a bit too old for taking an adventure.";
        dialogues[3] = "Well, good luck on you.";
    }

    /**
     * moving AI - how npc move
     */
    public void setAction(){

        actionLockCounter++;
        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i<=50){
                direction = "down";
            }
            if(i > 50 && i <=75){
                direction = "right";
            }
            if(i > 75 && i <=100){
                direction = "left";
            }
            actionLockCounter = 0;
        }

    }
    public void speak(){
        super.speak();
    }


}
