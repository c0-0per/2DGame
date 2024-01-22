package object;

import entity.Entity;
import org.example.GamePanel;

import java.util.logging.Logger;

public class OBJ_Sword_Normal extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);
        name = "Normal Sword";
        type = type_sword;
        try {
            down1 = setup("objects/sword_stone", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGE OF SWORD");
        }
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nBasic sword.\nIt has 1 attack.";
        knockBackPower = 2;
        motion1_duration = 5;
        motion2_duration = 25;
    }
}
