package object;

import entity.Entity;
import entity.Projectile;
import org.example.GamePanel;

import java.awt.*;
import java.util.logging.Logger;

public class OBJ_Rock extends Projectile {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    GamePanel gp;
    public OBJ_Rock(GamePanel gp) {
        super(gp);

        name = "Rock";

        speed = 3;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        try {
            up1 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            up2 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            down1 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            right1 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            right2 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            left1 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
            left2 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGES OF ROCK");
        }
    }

    /**
     * @param user (who use)
     * @return (true if have resources / false if not)
     */
    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if(user.ammo >= useCost){
            haveResource = true;
        }
        return haveResource;
    }

    /**
     * spend resource for using
     * @param user (who use)
     */
    public void subtractResource(Entity user){
        user.ammo -= useCost;
    }
    public Color getParticleColor(){
        Color color = new Color(40, 50, 0);
        return color;
    }
    public int getParticleSize(){
        int size = 8;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
