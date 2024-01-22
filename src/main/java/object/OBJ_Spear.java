package object;

import entity.Entity;
import org.example.GamePanel;

import java.util.logging.Logger;

public class OBJ_Spear extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    public OBJ_Spear(GamePanel gp) {
        super(gp);
        name = "Spear";
        type = type_spear;
        try {
            down1 = setup("objects/spear", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGE OF SPEAR");
        }
        attackValue = 1;
        attackArea.width = 45;
        attackArea.height = 45;
        description = "[" + name + "]\nWeapon of rogue.\nIt has 1 attack.";
        knockBackPower = 1;
        motion1_duration = 3;
        motion2_duration = 15;
    }
}
