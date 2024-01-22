package monster;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Potion_Red;
import object.OBJ_Rock;
import org.example.GamePanel;

import java.util.Random;
import java.util.logging.Logger;

public class MON_GreenSlime extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());

    GamePanel gp;
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Green Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defence = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    /**
     * monster images
     */
    public void getImage(){
        try {
            up1 = setup("monster/greenslime_down_1", gp.tileSize, gp.tileSize);
            up2 = setup("monster/greenslime_down_2", gp.tileSize, gp.tileSize);
            down1 = setup("monster/greenslime_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("monster/greenslime_down_2", gp.tileSize, gp.tileSize);
            right1 = setup("monster/greenslime_down_1", gp.tileSize, gp.tileSize);
            right2 = setup("monster/greenslime_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("monster/greenslime_down_1", gp.tileSize, gp.tileSize);
            left2 = setup("monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning("NO IMAGES OF SLIME");
        }

    }

    /**
     * randomly moving AI - how mosnter move
     */
    public void setAction(){

        if(onPath == true){
            //Check if it stops chasing
            checkStopChasingOrNot(gp.player, 15);
            //Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

            //Check if it shoots a projectile
            checkShootOrNot(200,30);
        }
        else {
            //Check if it starts chasing
            checkStartChasingOrNot(gp.player, 5);
            //Get a random  direction
            getRandomDirection();
        }
    }

    /**
     * monster run from player after get harmed
     */
    public void damageReaction(){
        actionLockCounter = 0;
//        direction = gp.player.direction;
        onPath = true;
    }

    /**
     * chance of item drop
     */
    public void checkDrop(){
        int i = new Random().nextInt(100)+1;
        if(i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 70){
            dropItem(new OBJ_Potion_Red(gp));
        }
    }
}
