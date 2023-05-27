/* import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class genericUI {

    JFrame window;
    private JButton confirmActionButton;
    private JButton upgradeGearButton;
    private JButton explorationButton;
    private JButton skillTreeButton;
    private JButton loadoutButton;
    private JPanel question;
    private JPanel rootPanel;
    private JPanel panel1;

    int buttonSpacing = 10;

    int buttonWidth, buttonHeight, ButtonSpacing = 10;
    Container dayLoopContainer;


    public genericUI() {

        window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);

         buttonWidth = (window.getWidth() - 40) / 4; // Subtract 40 to account for spacing
         buttonHeight = 30;

        upgradeGearButton = new JButton("Upgrade Gear");
        upgradeGearButton.setSize(1, 1);
        buttonWidth = (window.getWidth() - 40)/4;
        buttonHeight = 30;
        buttonSpacing = 10;

        upgradeGearButton.setBounds(window.getWidth() - buttonWidth - 10, 10, buttonWidth, buttonHeight);
        upgradeGearButton.setLayout(null);
        upgradeGearButton.setVisible(true);
        upgradeGearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Primary.tempVal = 1;
            }
        });

        explorationButton = new JButton();
        explorationButton.setSize(100, 50);

        explorationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Primary.tempVal = 2;
            }
        });

        skillTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Primary.tempVal = 3;
            }
        });

        loadoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Primary.tempVal = 4;
            }
        });
        confirmActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Primary.answer = (int) Primary.tempVal;
                if (Primary.tempVal > 4 || Primary.tempVal < 0) {
                   confirmActionButton.setText("Invalid response.");
                }
            }
        });

        dayLoopContainer.add(rootPanel);
        dayLoopContainer.add(upgradeGearButton);
        dayLoopContainer.add(explorationButton);
        dayLoopContainer.add(skillTreeButton);
        dayLoopContainer.add(loadoutButton);
        dayLoopContainer.add(confirmActionButton);

        dayLoopContainer.isVisible();
    }
}
 */
