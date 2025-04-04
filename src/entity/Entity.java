package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {

    GamePanel gp;

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    boolean attacking = false;
    public boolean alive = true;

    public boolean dying = false;

    boolean hpBarOn = false;

    int hpBarCounter = 0;


    int dyingCounter = 0;

    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;

    public int  level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defence;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    //ITEM ATTRIBUTES
    public int attackValue;
    public int defenceValue;
    public String description = "";
    public int useCost;




    int dialogueIndex = 0;

    //CHARACTER STATUS
    public int maxLife;
    public int life;

    public int mana;
    public int maxMana;

    public int ammo;

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    //TYPE
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;


    String dialogues[] = new String[20];



    public Entity (GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {

    }

    public void damageReaction () {

    }

    public void speak () {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        } else {
            gp.ui.currentDialogue = dialogues[dialogueIndex];
            dialogueIndex++;
        }

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;


        }

    }

    public void use (Entity entity) {

    };

    public void update() {

        setAction();

        collisionOn = false;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer == true) {
            damagePlayer(attack);
        }

        if (collisionOn == false) {
            switch(direction) {
                case "up":
                    worldY = worldY - speed;
                    break;
                case "down":
                    worldY = worldY + speed;
                    break;
                case "left":
                    worldX = worldX - speed;
                    break;
                case "right":
                    worldX = worldX + speed;
                    break;
            }
        }

        spriteCounter++;

        if (spriteCounter > 10) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

    }

    public void damagePlayer (int attack) {
        if (gp.player.invincible == false) {
            gp.playSoundEffect(6);

            int damage = attack - gp.player.defence;
            if (damage < 0) {
                damage = 0;
            }
            gp.player.life = gp.player.life - damage;
            gp.player.invincible = true;
        }
    }


    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

    public void draw (Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
        }

        BufferedImage image = null;

        switch (direction) {
            case "up" :
                if (spriteNumber == 1) {
                    image = up1;
                }
                if (spriteNumber == 2) {
                    image = up2;
                }
                break;

            case "down" :
                if (spriteNumber == 1) {
                    image = down1;
                }
                if (spriteNumber == 2) {
                    image = down2;
                }
                break;

            case "left" :
                if (spriteNumber == 1) {
                    image = left1;
                }
                if (spriteNumber == 2) {
                    image = left2;
                }
                break;

            case "right" :
                if (spriteNumber == 1) {
                    image = right1;
                }
                if (spriteNumber == 2) {
                    image = right2;
                }
                break;
        }

        //Monster HP Bar
        if (type == 2 && hpBarOn == true) {

            double oneScale = (double)gp.tileSize / maxLife;
            double hpBarValue = oneScale*life;

            g2.setColor(new Color(35, 35, 35));
            g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

            hpBarCounter++;

            if (hpBarCounter > 600) {
                hpBarCounter = 0;
                hpBarOn = false;
            }

        }


        if (invincible == true) {
            hpBarOn = true;
            hpBarCounter = 0;
            changeAlpha(g2, 0.4f);
        }
        if (dying == true) {
            dyingAnimation(g2);
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        changeAlpha(g2, 1f);

    }

    public void dyingAnimation (Graphics2D g2) {

        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }

        if (dyingCounter > i && dyingCounter <= i*2) {
            changeAlpha(g2, 1f);
        }

        if (dyingCounter > i*2 && dyingCounter <= i*3) {
            changeAlpha(g2, 0f);
        }

        if (dyingCounter > i*3 && dyingCounter <= i*4) {
            changeAlpha(g2, 1f);
        }

        if (dyingCounter > i*4 && dyingCounter <= i*5) {
            changeAlpha(g2, 0f);        }

        if (dyingCounter > i*5 && dyingCounter <= i*6) {
            changeAlpha(g2, 1f);
        }

        if (dyingCounter > i*6 && dyingCounter <= i*7) {
            changeAlpha(g2, 0f);
        }

        if (dyingCounter > i*7 && dyingCounter <= i*8) {
            changeAlpha(g2, 1f);
        }

        if (dyingCounter > i*8) {
            alive = false;
        }
    }

    public void changeAlpha (Graphics2D g2, float alphaValue) {

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

    }



}
