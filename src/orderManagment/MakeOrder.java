package orderManagment;

import users.*;
import meal.Product;
import userManagment.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MakeOrder extends JFrame {

    private JLabel backGround;
    private JButton rstButton;
    private JButton totalPriceButton;
    private JButton submitOrderButton;
    private JButton backButton;
    private JComboBox restaurant;
    private JComboBox mealName;
    private JTextField quantity;
    private JTextArea notes;
    private Dimension screenSize;
    private String username;

    private Customer cus;

    public MakeOrder(String username) {
        this.username = username;
        initComponents();
        fillRESComboBox();
        cus = new Customer(username);
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        rstButton = new JButton();
        totalPriceButton = new JButton();
        submitOrderButton = new JButton();
        backButton = new JButton();
        mealName = new JComboBox();
        restaurant = new JComboBox<>();
        quantity = new JTextField();
        notes = new JTextArea();
        backGround = new JLabel();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 686));
        setMinimumSize(new Dimension(336, 601));
        setTitle("Make Order");
        setResizable(false);
        setSize(new Dimension(336, 601));
        getContentPane().setLayout(null);

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
        rstButton.setBounds(165, 495, 122, 29);

        getContentPane().setBackground(new Color(255, 255, 255));

        totalPriceButton.setBackground(new Color(255, 90, 0));
        totalPriceButton.setForeground(new Color(255, 255, 255));
        totalPriceButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/mkord/price_btn.png")));
        totalPriceButton.setBorderPainted(false);
        totalPriceButton.setMargin(new Insets(0, 0, 0, 0));

        totalPriceButton.addActionListener((ActionEvent evt) -> {
            try {
                totalPriceButtonActionPerformed(evt);
            } catch (SQLException ex) {
                Logger.getLogger(MakeOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        getContentPane().add(totalPriceButton);
        totalPriceButton.setBounds(37, 495, 122, 29);

        submitOrderButton.setBackground(new Color(255, 90, 0));
        submitOrderButton.setForeground(new Color(255, 255, 255));
        submitOrderButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/mkord/submit_btn.png")));
        submitOrderButton.setBorderPainted(false);
        submitOrderButton.setMargin(new Insets(0, 0, 0, 0));

        submitOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                submitOrderButtonActionPerformed(evt);
            }
        });

        getContentPane().add(submitOrderButton);
        submitOrderButton.setBounds(26, 532, 269, 29);

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

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/mkord/background.png")));
        getContentPane().add(backGround);
        backGround.setBounds(0, 0, 359, 571);

        quantity.setBackground(new Color(235, 235, 235));
        quantity.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(quantity);
        quantity.setBounds(234, 166, 54, 29);

        notes.setText("");
        notes.setBackground(new Color(235, 235, 235));
        notes.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(notes);
        notes.setBounds(42, 270, 235, 215);

        restaurant.setBounds(37, 80, 148, 32);
        restaurant.setFont(new java.awt.Font("Myraid Pro", 0, 18));
        getContentPane().add(restaurant);
        restaurant.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"None"}));

        mealName.setBounds(37, 170, 96, 32);
        mealName.setFont(new java.awt.Font("Myraid Pro", 0, 18));
        getContentPane().add(mealName);
        mealName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"None"}));

        quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_quantityKeyTyped(evt);
            }
        });

        pack();
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new CusHome(username).setVisible(true);
    }

    private void rstButtonActionPerformed(java.awt.event.ActionEvent evt) {
        mealName.removeAllItems();
        fillMEALComboBox();
    }

    private void submitOrderButtonActionPerformed(ActionEvent evt) {
        if ((String.valueOf(restaurant.getSelectedItem())).equals("None") || (String.valueOf(mealName.getSelectedItem())).equals("None") || quantity.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all information!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            cus.makeorder(String.valueOf(restaurant.getSelectedItem()), String.valueOf(mealName.getSelectedItem()), notes.getText(), Integer.valueOf(quantity.getText()));
            this.setVisible(false);
            new CusHome(username).setVisible(true);
        }
    }

    private void txt_quantityKeyTyped(java.awt.event.KeyEvent evt) {
        char quantity = evt.getKeyChar();
        if (!(Character.isDigit(quantity)) || (quantity == KeyEvent.VK_BACK_SPACE) || (quantity == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }

    private void fillRESComboBox() {
        Owner owner = new Owner();
        java.util.List<String> Items = new ArrayList<String>();

        Items = owner.rest_name();

        for (String items : Items) {
            restaurant.addItem(items);
        }
    }

    private void fillMEALComboBox() {

        Owner owner = new Owner();
        java.util.List<String> Items = new ArrayList<String>();
        Items = owner.rest_meal2((String) restaurant.getSelectedItem());

        for (String items : Items) {
            mealName.addItem(items);
        }
    }

    public void totalPriceButtonActionPerformed(ActionEvent evt) throws SQLException {
        Product product = new Product();
        float price = 0;
        price = product.productPrice((String) mealName.getSelectedItem());
        JOptionPane.showMessageDialog(new MakeOrder(username), price * Integer.valueOf(quantity.getText()), "total price", JOptionPane.INFORMATION_MESSAGE);
    }
}
