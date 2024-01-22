package object;

import entity.Entity;
import entity.Projectile;
import org.example.GamePanel;

import java.awt.*;
import java.util.logging.Logger;

public class OBJ_Fireball extends Projectile {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    GamePanel gp;
    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Fireball";

        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        knockBackPower = 0;
        useCost = 1;
        alive = false;
        try {
            up1 = setup("projectiles/fireball_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("projectiles/fireball_up_2", gp.tileSize, gp.tileSize);
            down1 = setup("projectiles/fireball_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("projectiles/fireball_down_2", gp.tileSize, gp.tileSize);
            right1 = setup("projectiles/fireball_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("projectiles/fireball_right_2", gp.tileSize, gp.tileSize);
            left1 = setup("projectiles/fireball_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("projectiles/fireball_left_2", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGES OF FIREBALL");
        }

    }
    /**
     * @param user (who use)
     * @return (true if have resources / false if not)
     */
    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if(user.mana >= useCost){
            haveResource = true;
        }
        return haveResource;
    }
    /**
     * spend resource for using
     * @param user (who use)
     */
    public void subtractResource(Entity user){
        user.mana -= useCost;
    }
    public Color getParticleColor(){
        Color color = new Color(229, 117, 21);
        return color;
    }
    public int getParticleSize(){
        int size = 10;
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
