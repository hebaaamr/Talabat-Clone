package userManagment;

import users.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Selection extends JFrame {

    private JButton customerButton;
    private JLabel backGround;
    private JButton restaurantButton;
    private Dimension screenSize;

    public Selection() {
        initComponents();
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        customerButton = new JButton();
        restaurantButton = new JButton();
        backGround = new JLabel();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 686));
        setMinimumSize(new Dimension(336, 601));
        setTitle("Talabat");
        setResizable(false);
        setSize(new Dimension(336, 601));
        getContentPane().setLayout(null);

        customerButton.setBackground(new Color(255, 90, 0));
        customerButton.setForeground(new Color(255, 255, 255));
        customerButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/selection/customer.png")));
        customerButton.setBorderPainted(false);
        customerButton.setMargin(new Insets(0, 0, 0, 0));
        customerButton.setMaximumSize(new Dimension(116, 31));
        customerButton.setMinimumSize(new Dimension(116, 31));
        customerButton.setPreferredSize(new Dimension(116, 31));

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                customerButtonActionPerformed(evt);
            }
        });
        getContentPane().add(customerButton);
        customerButton.setBounds(59, 317, 203, 31);

        restaurantButton.setBackground(new Color(255, 90, 0));
        restaurantButton.setForeground(new Color(255, 255, 255));
        restaurantButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/selection/restaurant.png")));
        restaurantButton.setBorderPainted(false);
        restaurantButton.setMargin(new Insets(0, 0, 0, 0));
        restaurantButton.setMaximumSize(new Dimension(116, 31));
        restaurantButton.setMinimumSize(new Dimension(116, 31));
        restaurantButton.setPreferredSize(new Dimension(116, 31));

        restaurantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                restaurantButtonActionPerformed(evt);
            }
        });
        getContentPane().add(restaurantButton);
        restaurantButton.setBounds(59, 375, 203, 31);

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/common/background.png")));
        backGround.setText("backGround");
        getContentPane().add(backGround);
        backGround.setBounds(0, 0, 359, 571);
        backGround.getAccessibleContext().setAccessibleName("background");

        pack();
    }

    private void customerButtonActionPerformed(java.awt.event.ActionEvent evt) {
        customerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/selection/customer_h.png")));
        Customer cus = new Customer();
        this.setVisible(false);
        new LoginPanel(cus).setVisible(true);
    }

    private void restaurantButtonActionPerformed(java.awt.event.ActionEvent evt) {
        restaurantButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/selection/restaurant_h.png")));
        Owner owner = new Owner();
        this.setVisible(false);
        new LoginPanel(owner).setVisible(true);
    }
}
