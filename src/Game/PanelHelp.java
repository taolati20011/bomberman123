package Game;

import Sound.Sound;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Game.MyContainer.PANEL_MENU;

public class PanelHelp extends JPanel implements ActionListener {
    private JButton jbBack;
    private MyContainer myContainer;
    public static final String BACK = "back";
    public final Icon[] icons={
            new ImageIcon(getClass().getResource("/images/skipButton1.png")),
            new ImageIcon(getClass().getResource("/images/skipButton2.png")),
    };
    public final Image image =
            new ImageIcon(getClass().getResource("/images/backgroundHelp.png")).getImage();

    public PanelHelp(MyContainer myContainer) {
        setBackground(Color.green);
        setLayout(null);
        initComs();
        initListener();
        this.myContainer = myContainer;
    }

    private void initListener() {
        jbBack.addActionListener(this);
        jbBack.setActionCommand(BACK);
    }

    public void initComs() {
        jbBack= new JButton(icons[0]);
        jbBack.setRolloverIcon(icons[1]);
        jbBack.setSize(icons[0].getIconWidth(),icons[0].getIconHeight());
        jbBack.setLocation(450,Frame.HEIGHT-icons[0].getIconHeight()-33);
        add(jbBack);
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Graphics2D g2d= (Graphics2D) g;
        g2d.drawImage(image,0,0,Frame.WIDTH,Frame.HEIGHT,null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String run = e.getActionCommand();
        switch (run){
            case BACK:{
                Clip click = Sound.getSound(getClass().getResource("/Sound/click.wav"));
                click.start();
                myContainer.showCard(PANEL_MENU);
                break;
            }
            default:
                break;
        }
    }
}
