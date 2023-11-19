package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int ITEM_SIZE = UNIT_SIZE - 10;
    final int SPACER = 5;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/ UNIT_SIZE;
    static final int DELAY = 100;
    final int x[] = new int[GAME_UNITS]; // Holds all the x coordinates of the sake's body and head
    final int y[] = new int[GAME_UNITS]; // Holds all the y coordinates of the sake's body and head
    int bodyParts = 6;
    int applesEaten;

    int randomX;
    int randomY;
        // Coordinates for the apples - random
    int appleGraphX;
    int appleGraphY;
    int appleX;
    int appleY;
    String direction = "D"; // Starting direction - Directions: U-up R-right L-left R-right
    boolean running = false;
    Timer timer;
    Random random;


    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        randomLocation();
        newAppleGraph();
        newAppleHitBox();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // Grid Lines
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // Draw apple
            g.setColor(Color.RED);
            g.fillOval(appleGraphX, appleGraphY, ITEM_SIZE, ITEM_SIZE);


            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GRAY);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }

            }

            // Draw text that displays score
            g.setColor(Color.RED);
            g.setFont( new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }

    public void randomLocation() {
        randomX = (random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE);
        randomY = (random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE);
    }
    public void newAppleHitBox() {
        appleX = randomX;
        appleY = randomY;
    }

   public void newAppleGraph() {
        appleGraphX = randomX + SPACER;
        appleGraphY = randomY + SPACER;
    }

    public void move() {
        // Shifting the snake's bodyparts around
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            // x[0] and y[0] are the coordinates of the head for the snake
            case "U":
                y[0] = y[0] - UNIT_SIZE;
                break;
            case "D":
                y[0] = y[0] + UNIT_SIZE;
                break;
            case "L":
                x[0] = x[0] - UNIT_SIZE;
                break;
            case "R":
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            randomLocation();
            newAppleHitBox();
            newAppleGraph();
            newAppleHitBox();
        }

    }

    public void checkCollisions() {
        // Checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && y[0] == y[i]) {
                running = false;
            }
        }

        // Check if head touches left border
        if (x[0] < 0) {
            x[0] = SCREEN_WIDTH;
        }

        // Check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            x[0] = 0;
        }

        // Check if head touches top border
        if (y[0] < 0) {
            y[0] = SCREEN_WIDTH;
        }

        // Check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            y[0] = 0;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        // Game Over Score
        g.setColor(Color.RED);
        g.setFont( new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());


        // Game Over Text
        g.setColor(Color.RED);
        g.setFont( new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT/2);
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != "R") {
                        direction = "L";
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != "L") {
                        direction = "R";
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != "D") {
                        direction = "U";
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != "U") {
                        direction = "D";
                    }
                    break;


                /*case KeyEvent.VK_R:
                    if (!running) {
                        running = true;
                    }*/
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         if (running) {
             move();
             checkApple();
             checkCollisions();
         }
         repaint();
    }
}
