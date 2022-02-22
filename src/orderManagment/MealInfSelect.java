package orderManagment;

import users.*;
import userManagment.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MealInfSelect extends JFrame {

    private JButton backButton;
    private JButton viewButton;
    private JComboBox rstName;
    private JComboBox mealName;
    private JLabel backGround;
    private JButton rstButton;
    private Dimension screenSize;
    private String username;

    public MealInfSelect(String username) {
        this.username = username;
        initComponents();
        fillRESComboBox();
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        getContentPane().setBackground(new Color(255, 255, 255));

        rstButton = new JButton();

        viewButton = new JButton();
        backButton = new JButton();
        backGround = new JLabel();
        rstName = new JComboBox();
        mealName = new JComboBox();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 686));
        setMinimumSize(new Dimension(336, 601));
        setTitle("MealInfSelect");
        setResizable(false);
        setSize(new Dimension(336, 601));
        getContentPane().setLayout(null);

        viewButton.setBackground(new Color(255, 90, 0));
        viewButton.setForeground(new Color(255, 255, 255));
        viewButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/mealinf/inf_button.png")));
        viewButton.setBorderPainted(false);
        viewButton.setMargin(new Insets(0, 0, 0, 0));

        getContentPane().add(backButton);
        backButton.setBounds(3, 5, 43, 24);

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/mealinf/background_select.png")));
        getContentPane().add(backGround);
        backGround.setBounds(0, 0, 359, 571);

        rstName.setBounds(58, 176, 206, 39);
        rstName.setFont(new java.awt.Font("Myraid Pro", 0, 18));
        getContentPane().add(rstName);
        rstName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"None"}));

        mealName.setBounds(58, 281, 206, 39);
        mealName.setFont(new java.awt.Font("Myraid Pro", 0, 18));
        getContentPane().add(mealName);
        mealName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"None"}));

        rstButton.setBackground(new Color(255, 90, 0));
        rstButton.setForeground(new Color(255, 255, 255));
        rstButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/mkord/rst_btn.png")));
        rstButton.setBorderPainted(false);
        rstButton.setMargin(new Insets(0, 0, 0, 0));

        rstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                rstButtonActionPerformed(evt);
            }
        });

        getContentPane().add(rstButton);

        pack();
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                viewButtonActionPerformed(evt);
            }
        });

        getContentPane().add(viewButton);
        rstButton.setBounds(96, 520, 129, 36);

        viewButton.setBounds(96, 468, 129, 36);

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
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new CusHome(username).setVisible(true);
    }

    private void rstButtonActionPerformed(java.awt.event.ActionEvent evt) {
        mealName.removeAllItems();
        fillMEALComboBox();
    }

    private void viewButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new MealInf(String.valueOf(rstName.getSelectedItem()), String.valueOf(mealName.getSelectedItem()), username).setVisible(true);
    }

    private void fillRESComboBox() {

        Owner owner = new Owner();
        java.util.List<String> Items = new ArrayList<String>();

        Items = owner.rest_name();

        for (String items : Items) {
            rstName.addItem(items);
        }
    }

    private void fillMEALComboBox() {

        Owner owner = new Owner();
        java.util.List<String> Items = new ArrayList<String>();
        Items = owner.rest_meal2((String) rstName.getSelectedItem());

        for (String items : Items) {
            mealName.addItem(items);
        }
    }
}
