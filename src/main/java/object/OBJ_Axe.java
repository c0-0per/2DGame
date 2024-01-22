package object;

import entity.Entity;
import org.example.GamePanel;

import java.util.logging.Logger;

public class OBJ_Axe extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        name = "Axe";
        type = type_axe;
        description = "[Axe]\nBig but slow weapon";
        attackArea.width = 30;
        attackArea.height = 30;
        attackValue = 2;
        knockBackPower = 5;
        motion1_duration = 20;
        motion2_duration = 40;
        try {
            down1 = setup("objects/axe_stone", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGE OF AXE");
        }
    }
}
