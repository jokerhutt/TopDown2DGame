package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    GamePanel gp;

    public KeyHandler (GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        //TITLE STATE
        if (gp.gameState == gp.titleState) {

            if (gp.ui.titleScreenState == 0) {

                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    } else {
                        gp.ui.commandNum--;
                    }
                } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    if (gp.ui.commandNum >= 2) {
                        gp.ui.commandNum = 0;
                    } else {
                        gp.ui.commandNum++;
                    }
                }

                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                    } else if (gp.ui.commandNum == 1) {
                        //TODO
                    } else if (gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                }

            } else if (gp.ui.titleScreenState == 1) {

                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    } else {
                        gp.ui.commandNum--;
                    }
                } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    if (gp.ui.commandNum >= 3) {
                        gp.ui.commandNum = 0;
                    } else {
                        gp.ui.commandNum++;
                    }
                }

                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.gameState = gp.playState;
                    } else if (gp.ui.commandNum == 1) {
                        gp.gameState = gp.playState;
                    } else if (gp.ui.commandNum == 2) {
                        gp.gameState = gp.playState;
                    } else if (gp.ui.commandNum == 3) {
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0;
                    }
                }

            }

        }

        if (gp.gameState == gp.playState) {

            if (code == KeyEvent.VK_W) {

                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                    gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

        }

        else if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }

        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

    }
}
