import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HalmaMenu extends JMenuBar {

    HalmaMenu(Halma halma) {
        //back to menu
        JMenu menu = new JMenu("Menu");
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int ans = JOptionPane.showConfirmDialog(null, "Are You Sure You Want to Return to Menu?", "", JOptionPane.YES_NO_OPTION);
                if (ans == 0)
                    halma.dispose();
            }
        });
        //restart
        JMenu restart = new JMenu("Restart");
        restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int ans = JOptionPane.showConfirmDialog(null, "Are You Sure You Want to Restart the Game?", "", JOptionPane.YES_NO_OPTION);
                if (ans == 0)
                    halma.restart();
            }
        });
        add(menu);
        add(restart);
    }
}
