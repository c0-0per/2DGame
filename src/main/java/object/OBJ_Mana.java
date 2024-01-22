package object;

import entity.Entity;
import org.example.GamePanel;

import java.util.logging.Logger;

public class OBJ_Mana extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    GamePanel gp;
    public OBJ_Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Mana Crystal";
        try {
            image = setup("objects/manacrystal_full", gp.tileSize, gp.tileSize);
            image2 = setup("objects/manacrystal_blank", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGEs OF MANA");
        }

    }
}
