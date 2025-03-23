package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;
    int value = 5;

    public OBJ_Potion_Red (GamePanel gp) {
        super(gp);

        type = type_consumable;
        name = "Red Potion";
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nHeals your life byl \ncan cut some trees.";

    }

    public void use (Entity entity) {

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "f\n"
            + "Your life has been recovered by " + value + ".";
        entity.life = entity.life + value;

        if (gp.player.life > gp.player.maxLife) {
            gp.player.life = gp.player.maxLife;
        }

        gp.playSoundEffect(2);



    }

}
