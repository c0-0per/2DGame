package object;

import entity.Entity;
import org.example.GamePanel;

import java.util.logging.Logger;

public class OBJ_Potion_Red extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    GamePanel gp;
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;
        price = 20;
        type = type_consumable;
        name = "Red Potion";
        value = 2;
        try {
            down1 = setup("objects/potion_red", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGE OF HEALTH POTION");
        }

        description = "[Red Potion]\nRestore " + value + " heart.";
        stackable = true;

    }

    /**
     * use potion, + health
     * @param entity
     */
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You used the " + name + ".\nYour life has been recovered by " + value + ".";
        entity.life += value;
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        return true;
    }
}
