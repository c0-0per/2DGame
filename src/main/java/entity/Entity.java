package entity;

import org.example.GamePanel;
import org.example.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.attribute.AclEntryType;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    GamePanel gp;

    public BufferedImage down1;
    protected BufferedImage up1, up2, down2, right1, right2, left1, left2;
    protected BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackRight1, attackRight2, attackLeft1,
            attackLeft2, guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    protected Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    protected String dialogues[] = new String[20];
    protected Entity attacker;



    public int worldX, worldY;
    public String direction = "down";
    protected int spriteNum = 1;
    private int dialogueIndex = 0;
    public boolean invincible = false;
    public boolean collision = false;
    protected boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    private boolean hpBarOn = false;
    protected boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;
    protected boolean guarding = false;
    protected boolean transparent = false;
    protected boolean offBalance = false;
    public Entity loot;
    public boolean opened = false;

    protected int spriteCounter = 0;
    protected int actionLockCounter = 0;
    protected int invincibleCounter = 0;
    protected int shotAvailableCounter = 0;
    private int dyingCounter = 0;
    private int hpBarCounter = 0;
    protected int knockBackCounter = 0;
    protected int guardCounter = 0;
    private int offBalanceCounter = 0;




    public int maxLife;
    protected int defaultSpeed;
    public int life;
    public int maxMana;
    public int mana;
    public int speed;
    public String name;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defence;
    public int exp;
    public int nextLevelExp;
    public int coins;
    protected int motion1_duration;
    protected int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;
    protected Projectile projectile;

    public ArrayList<Entity> inventory = new ArrayList<>();
    protected int value;
    protected int attackValue;
    protected int defenceValue;
    public String description = "";
    protected int useCost;
    public int price;
    protected int knockBackPower = 0;
    protected boolean stackable = false;
    public int amount = 1;

    protected int type;
//    public final int type_player = 0;
//    public final int type_npc = 1;
    protected final int type_monster = 2;
    protected final int type_sword = 3;
    protected final int type_axe = 4;
    protected final int type_shield = 5;
    protected final int type_consumable = 6;
    protected final int type_pickupOnly = 7;
    protected final int type_obstacle = 8;

    public Entity(GamePanel gp){
        this.gp = gp;
    }
    private int getLeftX(){
        return worldX + solidArea.x;
    }
    private int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }
    private int getTopY(){
        return worldY + solidArea.y;
    }
    private int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }
    private int getCol(){
        return (worldX + solidArea.x)/gp.tileSize;
    }
    private int getRow(){
        return (worldY + solidArea.y)/gp.tileSize;
    }
    private int getXdistance(Entity target){
        int xDistance = Math.abs(worldX - target.worldX);
        return xDistance;
    }
    private int getYdistance(Entity target){
        int yDistance = Math.abs(worldY - target.worldY);
        return yDistance;
    }
    private int getTileDistance(Entity target){
        int tileDistance = (getXdistance(target) + getYdistance(target))/gp.tileSize;
        return tileDistance;
    }
    protected int getGoalCol(Entity target){
        int goalCol = (target.worldX + target.solidArea.x)/gp.tileSize;
        return goalCol;
    }
    protected int getGoalRow(Entity target){
        int goalRow = (target.worldY + target.solidArea.y)/gp.tileSize;
        return goalRow;
    }
    public void setLoot(Entity loot){

    }
    protected void setAction(){

    }
    protected void damageReaction(){

    }

    /**
     * show current dialog and turn npc to player
     */
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
            case "left":
                direction = "right";
                break;
        }
    }
    protected void interact(){

    }
    protected boolean use(Entity entity){
        return false;
    }
    public void checkDrop(){}

    /**
     * drop item on map
     * @param droppedItem
     */
    protected void dropItem(Entity droppedItem){
        for(int i = 0; i < gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    private Color getParticleColor(){
        Color color = null;
        return color;
    }
    private int getParticleSize(){
        int size = 0;
        return size;
    }
    private int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    private int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }

    /**
     * generate particle
     * @param generator (where spawn particles)
     * @param target (Im using particle on projectiles so generate particle when hit target)
     */
    protected void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    private void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkEntity(this,gp.npc);
        gp.cChecker.checkEntity(this,gp.monster);
        boolean contactPlayer =  gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer == true){
            damagePlayer(attack);
        }
    }


    /**
     * update game
     */
    public void update(){
        if(knockBack == true){
            checkCollision();
            if(collision == true){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(collisionOn == false){
                switch (knockBackDirection){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                }
            }
            knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else if(attacking == true){
            attacking();
        }
        else {
            setAction();
            checkCollision();
            if (collisionOn == false) {

                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 24) {
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincibleCounter = 0;
                invincible = false;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        if(offBalance == true){
            offBalanceCounter++;
            if(offBalanceCounter > 60){
                offBalance = false;
                offBalanceCounter = 0;
            }
        }
    }
    protected void checkAttackOrNot(int rate, int straight, int horizontal){ // ZASUNUTY V ORCA !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        boolean targetInRange = false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);

        switch (direction){
            case "up":
                if(gp.player.worldY < worldY && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "down":
                if(gp.player.worldY > worldY && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "left":
                if(gp.player.worldX < worldX && xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "right":
                if(gp.player.worldX > worldX && xDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
        }
        if(targetInRange == true){
            int i = new Random().nextInt(rate);
            if(i == 0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }
    protected void checkShootOrNot(int rate, int shotInterval){ // SLIME
        int i = new Random().nextInt(rate);
        if(i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval){
            projectile.set(worldX, worldY, direction, true, this);

            for(int j = 0; j < gp.projectile[1].length; j++){
                if(gp.projectile[gp.currentMap][j] == null){
                    gp.projectile[gp.currentMap][j] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }
    protected void checkStartChasingOrNot(Entity target, int distance){ // SLIME A ORC
        if(getTileDistance(target) < distance){
            onPath = true;
        }
    }
    protected void checkStopChasingOrNot(Entity target, int distance){ // SLIME A ORC
        if(getTileDistance(target) > distance){
            onPath = false;
        }
    }
    protected void getRandomDirection(){ // SLIME A ORC
        actionLockCounter++;
        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i<=50){
                direction = "down";
            }
            if(i > 50 && i <=75){
                direction = "right";
            }
            if(i > 75 && i <=100){
                direction = "left";
            }
            actionLockCounter = 0;
        }
    }
    private String getOppositeDirection(String direction){
        String oppositeDirection = "";

        switch (direction){
            case "up": oppositeDirection = "down";break;
            case "down": oppositeDirection = "up";break;
            case "right": oppositeDirection = "left";break;
            case "left": oppositeDirection = "right";break;
        }
        return oppositeDirection;
    }
    protected void attacking(){
        spriteCounter++;
        if(spriteCounter <= motion1_duration){
            spriteNum = 1;
        }
        if(spriteCounter > motion1_duration && spriteCounter <= motion2_duration){
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction){
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if(type == type_monster){
                if(gp.cChecker.checkPlayer(this) == true){
                    damagePlayer(attack);
                }
            } else {
                int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack,currentWeapon.knockBackPower);

                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }



            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > motion2_duration){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    /**
     * damage player
     * @param attack
     */
    protected void damagePlayer(int attack){
        if(gp.player.invincible == false){
            int damage = attack - gp.player.defence;
            String canGuardDirection = getOppositeDirection(direction);
            if(gp.player.guarding == true && gp.player.direction.equals(canGuardDirection)){
                if(gp.player.guardCounter < 10){
                    damage = 0;
                    setKnockBack(this, gp.player, knockBackPower/2);
                    offBalance = true;
                    spriteCounter -= 60;
                }
                else {
                    damage /= 3;
                }
            }
            else{
                if(damage < 1){
                    damage = 1;
                }
            }
            if(damage != 0){
                gp.player.transparent = true;
                setKnockBack(gp.player, this, knockBackPower);
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    protected void setKnockBack(Entity target, Entity attacker, int knockBackPower){
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (direction){
                case "up":
                    if (attacking == false) {
                        if(spriteNum == 1) { image = up1; }
                        if(spriteNum == 2) { image = up2; }
                    }
                    if(attacking == true){
                        tempScreenY = screenY - gp.tileSize;
                        if(spriteNum == 1) { image = attackUp1; }
                        if(spriteNum == 2) { image = attackUp2; }
                    }
                    break;
                case "down":
                    if (attacking == false) {
                        if(spriteNum == 1) { image = down1; }
                        if(spriteNum == 2) { image = down2; }
                    }
                    if(attacking == true){
                        if(spriteNum == 1) { image = attackDown1; }
                        if(spriteNum == 2) { image = attackDown2; }
                    }
                    break;
                case "right":
                    if (attacking == false) {
                        if(spriteNum == 1) { image = right1; }
                        if(spriteNum == 2) { image = right2; }
                    }
                    if(attacking == true){
                        if(spriteNum == 1) { image = attackRight1; }
                        if(spriteNum == 2) { image = attackRight2; }
                    }
                    break;
                case "left":
                    if (attacking == false) {
                        if(spriteNum == 1) { image = left1; }
                        if(spriteNum == 2) { image = left2; }
                    }
                    if(attacking == true){
                        tempScreenX = screenX - gp.tileSize;
                        if(spriteNum == 1) { image = attackLeft1; }
                        if(spriteNum == 2) { image = attackLeft2; }
                    }
                    break;
            }
            if(type == 2 && hpBarOn == true){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX,screenY - 15, (int)hpBarValue, 10);
                hpBarCounter++;
                if(hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }


            if(invincible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            if(dying == true){
                dyingAnimation(g2);
            }

            g2.drawImage(image, tempScreenX, tempScreenY,null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    /**
     * dying animation
     * @param g2
     */
    private void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        if(dyingCounter <= 5){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }
        if(dyingCounter > 5 && dyingCounter <=10){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if(dyingCounter > 10 && dyingCounter <=15){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }
        if(dyingCounter > 15 && dyingCounter <=20){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if(dyingCounter > 20 && dyingCounter <=25){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }
        if(dyingCounter > 25 && dyingCounter <=30){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if(dyingCounter > 30 && dyingCounter <=35){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }
        if(dyingCounter > 35 && dyingCounter <=40){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if(dyingCounter > 40){
            alive = false;
        }
    }

    /**
     * setup image
     * @param imagePath
     * @param width
     * @param height
     * @return
     */
    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    protected void searchPath(int goalCol, int goalRow){ // SLIME A ORC
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow, this);
        if(gp.pFinder.search() == true){
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX){
                direction = "up";
                checkCollision();
                if(collision == true){
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                direction = "up";
                checkCollision();
                if(collision == true){
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                direction = "down";
                checkCollision();
                if(collision == true){
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                direction = "down";
                checkCollision();
                if(collision == true){
                    direction = "right";
                }
            }

            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow){
                onPath = false;
            }

        }
    }
    protected int getDetected(Entity user, Entity target[][], String targetName){
        int index = 999;
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction){
            case "up": nextWorldY = user.getTopY()-gp.player.speed;break;
            case "down": nextWorldY = user.getBottomY()+gp.player.speed;break;
            case "left": nextWorldX = user.getLeftX()-gp.player.speed;break;
            case "right": nextWorldX = user.getRightX()+gp.player.speed;break;
        }
        int col =nextWorldX/gp.tileSize;
        int row =nextWorldY/gp.tileSize;

        for(int i = 0; i < target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                if(target[gp.currentMap][i].getCol() == col &&
                        target[gp.currentMap][i].getRow() == row &&
                        target[gp.currentMap][i].name.equals(targetName)){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

}
