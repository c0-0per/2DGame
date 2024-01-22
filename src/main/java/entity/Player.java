package entity;

import object.*;
import org.example.GamePanel;
import org.example.KeyHandler;
import org.example.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;


public class Player extends Entity {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int counterManaRegen = 0;
    public int manaRegenTime = 5;
    int standCounter = 0;
    public int heroClass = 0; // 1 - warrior, 2 - rogue, 3 - wizard
    public boolean attackCanceled = false;

    public final int maxInventorySize = 20;


    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);

        this.keyH= keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        attackArea.width = 36;
        attackArea.height = 36;
        getImage();
        setDefaultValues();

    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;

        defaultSpeed = 5;

        speed = defaultSpeed;
        direction = "down";

        level = 1;
        strength = 1;
        if(heroClass == 1){
            strength = 2;
        }
        dexterity = 1;
        maxLife = 6;
        if(heroClass == 3){
            maxMana = 4;
        }
        else {
            maxMana = 0;
        }
        mana = maxMana;
        life = maxLife;
        exp = 0;
        nextLevelExp = 5;
        coins = 550;
        if(heroClass != 2){
            currentWeapon = new OBJ_Sword_Normal(gp);
        }
        else {
            currentWeapon = new OBJ_Spear(gp);
        }
        currentShield = new OBJ_Shield(gp);

        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defence = getDefence();

        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
    }
    public void setDefaultPosition(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }
    public void restoreStatus(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        speed = defaultSpeed;
    }
    private void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
    }
    public int getDefence() {
        return defence = dexterity * currentShield.defenceValue;
    }

    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot(){
        int currentShieldSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentShield){
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }

    /**
     * player images
     */
    private void getImage(){
        try {
            up1 = setup("player/hero_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("player/hero_up_2", gp.tileSize, gp.tileSize);
            down1 = setup("player/hero_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("player/hero_down_2", gp.tileSize, gp.tileSize);
            right1 = setup("player/hero_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("player/hero_right_2", gp.tileSize, gp.tileSize);
            left1 = setup("player/hero_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("player/hero_left_2", gp.tileSize, gp.tileSize);
        } catch (Exception e){
            logger.severe("NO IMAGES OF HERO");
        }
    }

    /**
     * weapon with player attack images
     */
    public void getAttackImage(){
        if(currentWeapon.type == type_sword){
            try {
                attackUp1 = setup("player/hero_attack_up_1", gp.tileSize, gp.tileSize*2);
                attackUp2 = setup("player/hero_attack_up_2", gp.tileSize, gp.tileSize*2);
                attackDown1 = setup("player/hero_attack_down_1", gp.tileSize, gp.tileSize*2);
                attackDown2 = setup("player/hero_attack_down_2", gp.tileSize, gp.tileSize*2);
                attackRight1 = setup("player/hero_attack_right_1", gp.tileSize*2, gp.tileSize);
                attackRight2 = setup("player/hero_attack_right_2", gp.tileSize*2, gp.tileSize);
                attackLeft1 = setup("player/hero_attack_left_1", gp.tileSize*2, gp.tileSize);
                attackLeft2 = setup("player/hero_attack_left_2", gp.tileSize*2, gp.tileSize);
            } catch (Exception e){
                logger.warning("NO IMAGES OF ATTACKING HERO");
            }
        }
        if(currentWeapon.type == type_axe){
            try {
                attackUp1 = setup("player/hero_axe_up_1", gp.tileSize, gp.tileSize*2);
                attackUp2 = setup("player/hero_axe_up_2", gp.tileSize, gp.tileSize*2);
                attackDown1 = setup("player/hero_axe_down_1", gp.tileSize, gp.tileSize*2);
                attackDown2 = setup("player/hero_axe_down_2", gp.tileSize, gp.tileSize*2);
                attackRight1 = setup("player/hero_axe_right_1", gp.tileSize*2, gp.tileSize);
                attackRight2 = setup("player/hero_axe_right_2", gp.tileSize*2, gp.tileSize);
                attackLeft1 = setup("player/hero_axe_left_1", gp.tileSize*2, gp.tileSize);
                attackLeft2 = setup("player/hero_axe_left_2", gp.tileSize*2, gp.tileSize);
            } catch (Exception e){
                logger.warning("NO IMAGES OF ATTACKING HERO");
            }
        }
        if(currentWeapon.type == type_spear){
            try {
                attackUp1 = setup("player/hero_spear_up_1", gp.tileSize, gp.tileSize*2);
                attackUp2 = setup("player/hero_spear_up_2", gp.tileSize, gp.tileSize*2);
                attackDown1 = setup("player/hero_spear_down_1", gp.tileSize, gp.tileSize*2);
                attackDown2 = setup("player/hero_spear_down_2", gp.tileSize, gp.tileSize*2);
                attackRight1 = setup("player/hero_spear_right_1", gp.tileSize*2, gp.tileSize);
                attackRight2 = setup("player/hero_spear_right_2", gp.tileSize*2, gp.tileSize);
                attackLeft1 = setup("player/hero_spear_left_1", gp.tileSize*2, gp.tileSize);
                attackLeft2 = setup("player/hero_spear_left_2", gp.tileSize*2, gp.tileSize);
            } catch (Exception e){
                logger.warning("NO IMAGES OF ATTACKING HERO");
            }
        }
    }
    private void getGuardImage(){
        try {
            guardUp = setup("player/hero_guard_up", gp.tileSize, gp.tileSize);
            guardDown = setup("player/hero_guard_down", gp.tileSize, gp.tileSize);
            guardLeft = setup("player/hero_guard_left", gp.tileSize, gp.tileSize);
            guardRight = setup("player/hero_guard_right", gp.tileSize, gp.tileSize);
        } catch (Exception e){
            logger.warning("NO DEF IMAGES OF HERO");
        }

    }

    /**
     * update game(all player actions)
     */
    public void update(){
        if(knockBack == true){
            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this,gp.npc);
            gp.cChecker.checkEntity(this,gp.monster);


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
        else if(keyH.spacePressed == true && heroClass == 1){
            guarding = true;
            guardCounter++;
        }
        else if(keyH.upPressed == true || keyH.downPressed == true
                || keyH.rightPressed == true || keyH.leftPressed || keyH.enterPressed == true){
            if (keyH.upPressed == true){
                direction = "up";
            }
            else if (keyH.downPressed == true) {
                direction = "down";
            }
            else if (keyH.rightPressed == true){
                direction = "right";
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
            interactNPC(npcIndex);

            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            contactMonster(monsterIndex);

            gp.eHandler.checkEvent();



            if (collisionOn == false && keyH.enterPressed == false) {

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
            if(keyH.enterPressed == true && attackCanceled == false){
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
            gp.keyH.enterPressed = false;
            guarding = false;
            guardCounter = 0;

            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
      else {
            standCounter++;
            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
            guarding = false;
            guardCounter = 0;
        }
        if(gp.keyH.shotKeyPressed == true && projectile.alive == false
                && shotAvailableCounter == 30 && projectile.haveResource(this) == true){
            projectile.set(worldX, worldY, direction, true, this);
            projectile.subtractResource(this);

            for(int i = 0; i < gp.projectile[1].length; i++){
                if(gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }

        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        if(heroClass == 3){
            counterManaRegen++;
            if (counterManaRegen > manaRegenTime*60){
                counterManaRegen = 0;
                mana += 1;
                if(mana > maxMana){
                    mana = maxMana;
                }
            }
        }
        if(life <= 0){
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
        }
        logger.info(worldX/gp.tileSize + " " + worldY/gp.tileSize);
    }



    /**
     * Picks object.
     * If object is pickup only(f.e. coin) - use it and delete from list of objects
     * Else add object to inventory
     * @param i (if i != 999 - pak pickup some object, if i == 999 means that nothing pickup)
     */
    public void pickUpObject(int i){
        if(i != 999) {
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly){
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            else if(gp.obj[gp.currentMap][i].type == type_obstacle){
                if(keyH.enterPressed == true){
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            else {
                String text;
                if(canObtainItem(gp.obj[gp.currentMap][i]) == true){
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                }
                else {
                    text = "Your inventory is full!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }

    /**
     * speak with npc
     * @param i (if i != 999 - player stands near to npc - he can speak to npc, i = 999 - cant speak, cause is not near npc)
     */
    public void interactNPC(int i){
        if (gp.keyH.enterPressed == true){
            if(i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }

    /**
     * get harmed from monster
     * @param i (the same as interactNPC)
     */
    public void contactMonster(int i){
        if(i != 999){
            if(invincible == false && gp.monster[gp.currentMap][i].dying == false){
                int damage = gp.monster[gp.currentMap][i].attack - defence;
                if(damage < 1){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
//                logger.info("Player was damaged: - " + damage + "hp");
            }
        }
    }
    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower){
        if( i != 999){
            if(gp.monster[gp.currentMap][i].invincible == false){
                if(knockBackPower > 0){
                    setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
                }
                if(gp.monster[gp.currentMap][i].offBalance == true){
                    attack *= 3;
                }
                int damage = attack - gp.monster[gp.currentMap][i].defence;
                if(damage < 0){
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage("You caused " + damage + " damage!");
                logger.info("You caused " + damage + " damage");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();


                if(gp.monster[gp.currentMap][i].life <= 0){
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("You killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("You earned " + gp.monster[gp.currentMap][i].exp + " exp!");
                    logger.info("You killed the " + gp.monster[gp.currentMap][i].name + "!");
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }


    /**
     *
     * @param i (the same as interactNPC)
     */

    public void damageProjectile(int i){
        if(i != 999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    /**
     * checking if player has more exp that need for lvl up
     */
    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp+5;
            if(level%2 == 0){
                maxLife += 2;
            }
            else{
                strength++;
                if(heroClass == 3){
                    maxMana += 1;
                }
            }

            attack = getAttack();
            defence = getDefence();
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are " + level + " level!";
        }
    }

    /**
     * use items from inventory
     */
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defence = getDefence();
            }
            if(selectedItem.type == type_consumable){
                if(selectedItem.use(this) == true){
                    if(selectedItem.amount > 1){
                        selectedItem.amount--;
                    }
                    else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }
    public int searchItemInInventory(String itemName){
        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item){
        boolean canObtain = false;
        if(item.stackable == true){
            int index = searchItemInInventory(item.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else{
                if(inventory.size() != maxInventorySize){
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        else {
            if(inventory.size() != maxInventorySize){
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }

    /**
     * draw (change 2 picturse for realistic movings)
     * @param g2
     */
    public void draw(Graphics2D g2){

        BufferedImage image = null;
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
                if(guarding == true){
                    image = guardUp;
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
                if(guarding == true){
                    image = guardDown;
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
                if(guarding == true){
                    image = guardRight;
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
                if(guarding == true){
                    image = guardLeft;
                }
                break;
        }
        if(transparent == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
