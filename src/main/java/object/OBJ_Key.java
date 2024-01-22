package object;

import entity.Entity;
import org.example.GamePanel;

import java.util.logging.Logger;


public class OBJ_Key extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    GamePanel gp;
    public OBJ_Key(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Key";
        try {
            down1 = setup("objects/key", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGE OF KEY");
        }
        description = "[" + name + "]\nMain item that\ncan open doors.";
        stackable = true;
    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;

        int onjIndex = getDetected(entity, gp.obj, "Door");

        if(onjIndex != 999){
            gp.ui.currentDialogue = "You used the " + name + " - door is opened";
            gp.obj[gp.currentMap][onjIndex] = null;
            return true;
        }
        else {
            gp.ui.currentDialogue = "Where is a door?";
            return false;
        }
    }
}
