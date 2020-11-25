package Game;

import javax.swing.*;

public class Frame extends JFrame {
    public static final int WIDTH = 765;
    public static final int HEIGHT = 705;

    public Frame(){
        initFrame();
    }

    private void initFrame() {
        setTitle("Boom");
        setSize(WIDTH,HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new MyContainer());
    }
}
