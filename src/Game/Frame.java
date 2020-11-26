package Game;

import javax.swing.*;
import java.net.URL;

public class Frame extends JFrame {
    public static final int WIDTH = 765;
    public static final int HEIGHT = 705;

    public Frame(){
        initFrame();
        URL iconURL = getClass().getResource("/images/icon.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
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
