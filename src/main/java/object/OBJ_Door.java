package object;

import entity.Entity;
import org.example.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Logger;

public class OBJ_Door extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    GamePanel gp;
    public OBJ_Door(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = type_obstacle;
        name = "Door";
        try {
            down1 = setup("objects/door", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGE OF DOOR");
        }
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void interact(){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You need a key to open.";
    }
}
