package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Rock;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    GamePanel gp;


    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        type = type_monster;

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

    public void getImage() {

        down1 = setup("/monster/greenslime_down_1");
        down2 = setup("/monster/greenslime_down_2");
        up1 = setup("/monster/greenslime_down_1");
        up2 = setup("/monster/greenslime_down_2");
        left1 = setup("/monster/greenslime_down_1");
        left2 = setup("/monster/greenslime_down_2");
        right1 = setup("/monster/greenslime_down_1");
        right2 = setup("/monster/greenslime_down_2");

    }

    public void setAction() {

        actionLockCounter ++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100)+1;

            if (i <= 25) {
                direction = "up";
            } if (i > 25 && i <= 50) {
                direction = "down";
            } if (i > 50 && i <= 75) {
                direction = "left";
            } if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;

        }

        int i = new Random().nextInt(100)+1;
        if (i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }

    }

    public void damageReaction () {

        actionLockCounter = 0;
        direction = gp.player.direction;



    }

}
