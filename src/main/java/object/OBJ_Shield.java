package object;

import entity.Entity;
import org.example.GamePanel;

import java.util.logging.Logger;

public class OBJ_Shield extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    public OBJ_Shield(GamePanel gp) {
        super(gp);
        name = "Shield";
        type = type_shield;
        try {
            down1 = setup("objects/shield", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGE OF SHIELD");
        }
        defenceValue = 1;
        description = "[" + name + "]\nIt has 1 defence.\nOnly warrior can use";
    }
}
