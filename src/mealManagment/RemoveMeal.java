package mealManagment;

import users.*;
import userManagment.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RemoveMeal extends JFrame {

    private JButton backButton;
    private JButton removeButton;
    private JComboBox comboBox;
    private JLabel backGround;
    private Dimension screenSize;

    private String username;

    public RemoveMeal(String username) {
        this.username = username;
        initComponents();
        fillComboBox();
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        getContentPane().setBackground(new Color(255, 255, 255));

        removeButton = new JButton();
        backButton = new JButton();
        backGround = new JLabel();
        comboBox = new JComboBox();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 686));
        setMinimumSize(new Dimension(336, 601));
        setTitle("RemoveMeal");
        setResizable(false);
        setSize(new Dimension(336, 601));
        getContentPane().setLayout(null);

        removeButton.setBackground(new Color(255, 90, 0));
        removeButton.setForeground(new Color(255, 255, 255));
        removeButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/rmvmeal/rmvmeal_btn.png")));
        removeButton.setBorderPainted(false);
        removeButton.setMargin(new Insets(0, 0, 0, 0));

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(removeButton);
        removeButton.setBounds(96, 468, 129, 36);

        backButton.setBackground(new Color(255, 90, 0));
        backButton.setForeground(new Color(255, 255, 255));
        backButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/common/back_btn.png")));
        backButton.setBorderPainted(false);
        backButton.setMargin(new Insets(0, 0, 0, 0));
        backButton.setMaximumSize(new Dimension(116, 31));
        backButton.setMinimumSize(new Dimension(116, 31));
        backButton.setPreferredSize(new Dimension(116, 31));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        getContentPane().add(backButton);
        backButton.setBounds(3, 5, 43, 24);

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/rmvmeal/background.png")));
        getContentPane().add(backGround);
        backGround.setBounds(0, 0, 359, 115);

        comboBox.setBounds(58, 274, 206, 39);
        comboBox.setFont(new java.awt.Font("Myraid Pro", 0, 18));
        getContentPane().add(comboBox);
        comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"None"}));

        pack();
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new OwnerHome(username).setVisible(true);
    }

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {

        Owner owner = new Owner();
        if (!(String.valueOf(comboBox.getSelectedItem())).equals("None")) {
            owner.deleteMeal(String.valueOf(comboBox.getSelectedItem()));
            JOptionPane.showMessageDialog(this, "Successfully removed", "Done", JOptionPane.INFORMATION_MESSAGE);
            comboBox.removeItem(comboBox.getSelectedItem());
        }

        this.setVisible(false);
        new OwnerHome(username).setVisible(true);
    }

    private void fillComboBox() {
        Owner owner = new Owner();
        java.util.List<String> Items = new ArrayList<String>();

        Items = owner.rest_meal(username);

        for (String items : Items) {
            comboBox.addItem(items);
        }
    }
}
