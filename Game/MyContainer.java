package Game;

import Game.PanelGame;
import Sound.Sound;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class MyContainer extends JPanel {
    public static final String PANEL_GAME= "PanelGame";
    public static final String PANEL_MENU= "PanelMenu";
    public static final String PANEL_HELP= "PanelHelp";
    private PanelGame panelGame;
    private PanelMenu panelMenu;
    private PanelHelp panelHelp;
    private CardLayout cardLayout;
    public Clip soundMenu;

    public MyContainer() {
        cardLayout = new CardLayout();
        panelGame = new PanelGame();;
        panelHelp=new PanelHelp(this);
        panelMenu=new PanelMenu(this);
        setLayout(cardLayout);
        add(panelGame, PANEL_GAME);
        add(panelMenu,PANEL_MENU);
        add(panelHelp,PANEL_HELP);
        soundMenu = Sound.getSound(getClass().getResource("/Sound/soundMenu.wav"));
        soundMenu.start();
        soundMenu.loop(100);
        cardLayout.show(this,PANEL_MENU);
        addKeyListener(panelGame);
        setFocusable(true);
    }

    public void showCard(String name) {
        if (name == PANEL_GAME) {
            cardLayout.show(this,name);
            panelGame.initPanelGame();
            soundMenu.stop();
        } else  if (name== PANEL_HELP){
            cardLayout.show(this,name);
        } else if (name == PANEL_MENU){
            cardLayout.show(this,PANEL_MENU);
        }
    }
}
