package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    public final int originalTileSize = 16;

    //Scales tile to 48x48 so it looks bigger
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; //760 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;


    //SYSTEM
    public KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    Sound music = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    //GAME STATE
    public int gameState;

    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;



    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH);

    public Entity obj[] = new Entity[10];

    public Entity npc[] = new Entity[10];

    public Entity monster[] = new Entity[20];

    ArrayList<Entity> entityList = new ArrayList<>();

    public ArrayList<Entity> projectileList = new ArrayList<>();


    int FPS = 60;

    public TileManager tileM = new TileManager(this);



    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        System.out.println(this.isFocusable());
        System.out.println("Focus Owner: " + this.isFocusOwner());

    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        playMusic(0);
        gameState = titleState;

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta = delta + (currentTime - lastTime) / drawInterval;
            timer = timer + (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update () {

        if (gameState == playState) {
            //PLAYER
            player.update();

            //NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {

                    if (monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                    }

                    if (monster[i].alive == false) {
                        monster[i] = null;
                    }
                }
            }

            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {

                    if (projectileList.get(i).alive == true) {
                        projectileList.get(i).update();
                    }

                    if (projectileList.get(i).alive == false) {
                        projectileList.remove(i);
                    }
                }
            }

        }
        if (gameState == pauseState) {
            //nothing
        }

    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        //OTHERS
        else {
            //TILE
            tileM.draw(g2);

            //ADD ENTITIES TO ARRAY LIST
            entityList.add(player);

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }

            //SORY
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            //EMPTY ENTITY LIST
            entityList.clear();

            //UI
            ui.draw(g2);
        }

        //DEBUG
        if (keyH.showDebugText == true) {
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX " + player.worldX, x, y);
            y = y + lineHeight;

            g2.drawString("WorldX " + player.worldY, x, y);
            y = y + lineHeight;

            g2.drawString("Col " + (player.worldX + player.solidArea.x)/tileSize, x, y);
            y = y + lineHeight;

            g2.drawString("Row " + (player.worldX + player.solidArea.y)/tileSize, x, y);
            y = y + lineHeight;

        }



        g2.dispose();

    }

    public void playMusic (int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }



    public void playSoundEffect(int i) {
        sound.setFile(i);
        sound.play();
    }

}
