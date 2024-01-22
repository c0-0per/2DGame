package entity;

import object.OBJ_Potion_Red;
import org.example.GamePanel;

public class NPC_Merchant extends Entity {
    public NPC_Merchant(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 0;
        getImage();
        setDialogue();
        setItems();
    }

    /**
     * images
     */
    public void getImage(){

        up1 = setup("npc/merchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("npc/merchant_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("npc/merchant_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("npc/merchant_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("npc/merchant_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("npc/merchant_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("npc/merchant_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("npc/merchant_down_2", gp.tileSize, gp.tileSize);
    }

    /**
     * dialogues of npc
     */
    private void setDialogue(){
        dialogues[0] = "Hi player, I have some\ninteresting items. Do u wanna trade?";
    }
    private void setItems(){
        inventory.add(new OBJ_Potion_Red(gp));
    }
    public void speak(){
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
