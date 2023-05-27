/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class combatUI extends Music {

    protected static int valuePercentage = 0;
    JPanel healthBarPanel, confirmButtonPanel, staminaBarPanel, enemyHealthBarPanel, enemyStaminaBarPanel;

    JPanel turnDisplay;

   // Container combatContainer;

  //  JProgressBar healthBar, staminaBar, enemyHealthBar, enemyStaminaBar;

   /* JButton confirmButton;

    damageHandler damageHandler = new damageHandler(); */

/*
    public void combatContainer() {
        // genericUI GenericUI = new genericUI();
        // combatContainer = GenericUI.window.getContentPane();
    }
    public static void drawCombat() {
        new combatUI();
    }
    public combatUI(){
        genericUI GenericUI = new genericUI();

        GenericUI.dayLoopContainer.hide();

        combatContainer = GenericUI.window.getContentPane();

        healthBarPanel = new JPanel();
        healthBarPanel.setBounds(25, 35, 300, 20);
        healthBarPanel.setForeground(Color.red);
        healthBarPanel.setBackground(Color.green);
        combatContainer.add(healthBarPanel);

        staminaBarPanel = new JPanel();
        staminaBarPanel.setBounds(25, 65, 150, 10);
        staminaBarPanel.setForeground(Color.blue);
        staminaBarPanel.setBackground(Color.cyan);
        combatContainer.add(staminaBarPanel);

        enemyHealthBarPanel = new JPanel();
        enemyHealthBarPanel.setBounds(460, 35, 300, 20);
        enemyHealthBarPanel.setForeground(Color.red);
        enemyHealthBarPanel.setBackground(Color.green);
        combatContainer.add(enemyHealthBarPanel);

        enemyStaminaBarPanel = new JPanel();
        enemyStaminaBarPanel.setBounds(610, 65, 150, 10);
        enemyStaminaBarPanel.setForeground(Color.blue);
        enemyStaminaBarPanel.setBackground(Color.cyan);
        combatContainer.add(enemyStaminaBarPanel);

        confirmButtonPanel = new JPanel();
        confirmButtonPanel.setBounds(250, 300, 300, 40);
        confirmButtonPanel.setBackground(Color.black);
        combatContainer.add(confirmButtonPanel);


        healthBar = new JProgressBar(0,100);
        healthBar.setPreferredSize(new Dimension(300, 10));
        tempVal = 200 + (level*50);
        valuePercentage = Math.toIntExact(Math.round(tempVal/playerHP));
        healthBar.setValue(valuePercentage);
        healthBar.setString(String.valueOf(playerHP));
        healthBar.setVisible(true);

        staminaBar = new JProgressBar(0, 100);
        staminaBar.setPreferredSize(new Dimension(100, 20));
        tempVal = 100;
        valuePercentage = Math.toIntExact(Math.round(tempVal/playerStamina));
        staminaBar.setValue(100);
        staminaBar.setString(String.valueOf(playerStamina));
        staminaBar.setVisible(true);

        enemyHealthBar = new JProgressBar(0, 100);
        enemyHealthBar.setPreferredSize(new Dimension(300, 10));
        try {
            valuePercentage = Math.toIntExact(Math.round(tempVal / Combat.enemyMaxHP));
        }
        catch (Exception ignored){
        }
        enemyHealthBar.setValue(valuePercentage);
        enemyHealthBar.setString(String.valueOf(enemyHP));
        enemyHealthBar.setVisible(true);

        enemyStaminaBar = new JProgressBar(0,100);
        staminaBar.setPreferredSize(new Dimension(100, 20));
        tempVal = 100;
        staminaBar.setValue(100);
        staminaBar.setString(String.valueOf(enemyStamina));
        enemyStaminaBar.setVisible(true);

        confirmButton = new JButton();
        confirmButton.setBackground(Color.black);
        confirmButton.setForeground(Color.white);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(damageHandler);
        combatContainer.add(confirmButton);
        confirmButton.setVisible(true);

        GenericUI.window.setVisible(true);
    }
  protected static class damageHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {

        }


  }

    protected JProgressBar barFill(String tempString) throws InterruptedException {
        double x;
        switch (tempString) {
            case "Health Bar" -> {
                x = (double) 100 / (200 + (50 * level));
                valuePercentage = (int) (100*(playerHP / (200 + (level * 50))));
                x = healthBar.getValue();
                while (x != valuePercentage) {
                    if (x > valuePercentage) {
                        x -= 1;
                        healthBar.setValue((int) (x));
                    }
                    else {
                        x += 1;
                        healthBar.setValue((int) (x));
                    }
                    Thread.sleep(10);
                }
                System.out.println(valuePercentage);
                healthBar.setValue(valuePercentage);
                healthBarPanel.revalidate();
                healthBarPanel.repaint();
                return healthBar;
            }
            case "Stamina Bar" -> {
                valuePercentage = (100* (playerStamina)/100);
                x = staminaBar.getValue();
                while (x != valuePercentage) {
                    if (x > valuePercentage) {
                        x -= 1;
                        staminaBar.setValue((int) (x));
                    }
                    else {
                        x += 1;
                        staminaBar.setValue((int) (x));
                    }
                    Thread.sleep(10);
                }
                System.out.println(valuePercentage);
                staminaBar.setValue(valuePercentage);
                staminaBarPanel.revalidate();
                staminaBarPanel.repaint();
                staminaBarPanel.revalidate();
                return staminaBar;
            }
            case "Enemy Health Bar" -> {
                x = 100 / Combat.enemyMaxHP;
                valuePercentage = (int) (100*(enemyHP / Combat.enemyMaxHP));
                x = enemyHealthBar.getValue();
                while (x != valuePercentage) {
                    if (x > valuePercentage) {
                        x -= 1;
                        enemyHealthBar.setValue((int) (x));
                    }
                    else {
                        x += 1;
                        enemyHealthBar.setValue((int) (x));
                    }
                    Thread.sleep(5);
                }
                enemyHealthBar.setValue(valuePercentage);
                enemyHealthBarPanel.revalidate();
                enemyHealthBarPanel.repaint();
                return enemyHealthBar;
            }
            case "Enemy Stamina Bar" -> {
                x =
                valuePercentage = 100 / enemyStamina;
                x = enemyStaminaBar.getValue();
                while (x != valuePercentage) {
                    if (x > valuePercentage) {
                        x -= 1;
                        enemyStaminaBar.setValue((int) (x));
                    }
                    else {
                        x += 1;
                        enemyStaminaBar.setValue((int) (x));
                    }
                    Thread.sleep(10);
                }
                System.out.println(valuePercentage);
                enemyStaminaBar.setValue(valuePercentage);
                return enemyStaminaBar;
            }
        }
        System.out.println("Nothing here, gonna return null.");
        return null;
    }

}
*/