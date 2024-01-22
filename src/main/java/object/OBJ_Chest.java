package object;

import entity.Entity;
import org.example.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Logger;

public class OBJ_Chest extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    GamePanel gp;
    public OBJ_Chest(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = type_obstacle;
        name = "Chest";
        try {
            image = setup("objects/chest", gp.tileSize, gp.tileSize);
            image2 = setup("objects/chest_opened", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGES OF CHEST");
        }
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        try {
            down1 = setup("objects/axe_stone", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGE OF AXE");
        }
    }
    public void setLoot(Entity loot){
        this.loot = loot;
    }
    public void interact(){
        gp.gameState = gp.dialogueState;
        if(opened == false){
            StringBuilder sb = new StringBuilder();
            sb.append("You opened the chest and find a " + loot.name + "!");
            if(gp.player.canObtainItem(loot) == false) {
                sb.append("\n..But you cannot carry anymore");
            }
            else {
                sb.append("\nYou obtain the " + loot.name + "!");
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        }
        else{
            gp.ui.currentDialogue = "It is empty";
        }
    }
}