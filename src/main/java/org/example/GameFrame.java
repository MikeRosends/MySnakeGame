package org.example;

import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame() {
        GamePanel panel1 = new GamePanel();
        this.add(panel1);

        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents
        this.setVisible(true);
        this.setLocationRelativeTo(null); // makes window appear in the middle of the screen

    }
}
