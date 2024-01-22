package object;

import entity.Entity;
import org.example.GamePanel;

import java.util.logging.Logger;


public class OBJ_Heart extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    public OBJ_Heart(GamePanel gp){
        super(gp);
        name = "Heart";
        try {
            image = setup("objects/heart_full", gp.tileSize, gp.tileSize);
            image2 = setup("objects/heart_half", gp.tileSize, gp.tileSize);
            image3 = setup("objects/heart_blank", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGES OF HEART");
        }


    }
}
