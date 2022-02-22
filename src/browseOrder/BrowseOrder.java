package browseOrder;

import users.*;
import meal.Order;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BrowseOrder extends JFrame {

    private JLabel backGround;
    private JButton backButton;
    private JLabel customer;
    private JLabel price;
    private JLabel orderdate;
    private JLabel mealName;
    private JLabel quantity;
    private JLabel notes;
    private Dimension screenSize;
    private List<Order> orderList = new ArrayList<Order>();
    private boolean flag;
    private String username;

    public BrowseOrder(String username, String ordername, boolean flag) {
        initComponents();
        this.flag = flag;
        this.username = username;
        if (flag) {
            Owner owner = new Owner();
            orderList = owner.browseOrder(username, ordername);
        } else {
            Customer cust = new Customer();
            orderList = cust.browseOrder(username, ordername);
        }
        display();
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        getContentPane().setBackground(new Color(255, 255, 255));

        backButton = new JButton();
        mealName = new JLabel();
        price = new JLabel();
        customer = new JLabel();
        backGround = new JLabel();
        orderdate = new JLabel();
        quantity = new JLabel();
        notes = new JLabel();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 686));
        setMinimumSize(new Dimension(336, 601));
        setTitle("BrowseOrder");
        setResizable(false);
        setSize(new Dimension(336, 601));
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 255));

        getContentPane().add(backButton);
        backButton.setBounds(3, 5, 43, 24);

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/browseordr/background.png")));
        getContentPane().add(backGround);
        backGround.setBounds(0, 0, 359, 571);

        mealName.setBackground(new Color(235, 235, 235));
        mealName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(mealName);
        mealName.setBounds(25, 290, 273, 21);

        customer.setBackground(new Color(235, 235, 235));
        customer.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(customer);
        customer.setBounds(25, 90, 273, 21);

        orderdate.setBackground(new Color(235, 235, 235));
        orderdate.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(orderdate);
        orderdate.setBounds(25, 160, 120, 21);

        quantity.setBackground(new Color(235, 235, 235));
        quantity.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(quantity);
        quantity.setBounds(25, 365, 273, 21);

        notes.setBackground(new Color(235, 235, 235));
        notes.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(notes);
        notes.setBounds(25, 445, 273, 80);

        price.setBackground(new Color(235, 235, 235));
        price.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(price);
        price.setBounds(25, 222, 140, 21);

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

        pack();
    }

    private void display() {
        for (Order order : orderList) {
            String uName;
            String[] splitter = order.getName().split("-æ-");
            uName = splitter[0];

            customer.setText(uName);
            orderdate.setText(String.valueOf(order.getDate()));
            notes.setText(order.getNotes());

            for (int i = 0; i < order.products.size(); i++) {
                int quan = order.products.get(i).getQuantity();
                quantity.setText(String.valueOf(quan));
                mealName.setText(order.products.get(i).getName());
                price.setText(String.valueOf(order.products.get(i).getProdutPrice() * quan));
            }
        }
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        if (flag) {
            new OwnOrderSelect(username).setVisible(true);
        } else {
            new CusOrderSelect(username).setVisible(true);
        }
    }
}
