package object;

import entity.Entity;
import org.example.GamePanel;

import java.util.logging.Logger;

public class OBJ_Coin_Bronze extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    GamePanel gp;
    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name = "Bronze Coin";
        value = 1;
        try {
            down1 = setup("objects/coin_bronze", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGE OF COIN");
        }
    }

    /**
     * + coins / "use" coin
     * @param entity
     */
    public boolean use(Entity entity){
        gp.ui.addMessage("Coin " + value);
        gp.player.coins += value;
        return true;
    }
}
