package Game;

import Game.PanelGame;

import javax.swing.*;
import java.awt.*;

public class MyContainer extends JPanel {
    public static final String PANEL_GAME= "PanelGame";
    private PanelGame panelGame;
    private CardLayout cardLayout;

    public MyContainer() {
        cardLayout = new CardLayout();
        panelGame = new PanelGame();
        setLayout(cardLayout);
        add(panelGame, PANEL_GAME);
        addKeyListener(panelGame);
        setFocusable(true);
        showCard(PANEL_GAME);
    }

    public void showCard(String name) {
        if (name == PANEL_GAME) {
            panelGame.initPanelGame();
        }
    }
}
