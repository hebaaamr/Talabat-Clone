package userManagment;

import users.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginPanel extends JFrame {

    private JButton loginButton;
    private JLabel backGround;
    private JButton signupButton;
    private JButton backButton;
    private JTextField username;
    private JPasswordField password;
    private boolean customer;
    private Customer cus;
    private Owner owner;
    private Dimension screenSize;

    public LoginPanel(Customer c) {
        this.cus = c;
        initComponents();
        customer = true;
    }

    public LoginPanel(Owner o) {
        this.owner = o;
        initComponents();
        customer = false;
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        loginButton = new JButton();
        signupButton = new JButton();
        backButton = new JButton();
        backGround = new JLabel();
        username = new JTextField();
        password = new JPasswordField();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 686));
        setMinimumSize(new Dimension(336, 601));
        setTitle("Login");
        setResizable(false);
        setSize(new Dimension(336, 601));
        getContentPane().setLayout(null);

        loginButton.setBackground(new Color(255, 90, 0));
        loginButton.setFont(new Font("Microsoft Sans Serif", 1, 14));
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/login/login_btn.png")));
        loginButton.setToolTipText("");
        loginButton.setBorderPainted(false);
        loginButton.setMargin(new Insets(0, 0, 0, 0));
        loginButton.setMaximumSize(new Dimension(116, 31));
        loginButton.setMinimumSize(new Dimension(116, 31));
        loginButton.setPreferredSize(new Dimension(116, 31));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    loginButtonActionPerformed(evt);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        getContentPane().add(loginButton);
        loginButton.setBounds(100, 463, 122, 31);

        signupButton.setBackground(new Color(255, 90, 0));
        signupButton.setFont(new Font("Microsoft Sans Serif", 1, 14));
        signupButton.setForeground(new Color(255, 255, 255));
        signupButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/login/signup_btn.png")));
        signupButton.setToolTipText("");
        signupButton.setBorderPainted(false);
        signupButton.setMargin(new Insets(0, 0, 0, 0));
        signupButton.setMaximumSize(new Dimension(116, 31));
        signupButton.setMinimumSize(new Dimension(116, 31));
        signupButton.setPreferredSize(new Dimension(116, 31));

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                signupButtonActionPerformed(evt);
            }
        });
        getContentPane().add(signupButton);
        signupButton.setBounds(100, 511, 122, 31);

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

        username.setText("");
        username.setBackground(new Color(255, 255, 255));
        username.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(username);
        username.setBounds(34, 290, 254, 26);

        password.setText("");
        password.setBackground(new Color(255, 255, 255));
        password.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(password);
        password.setBounds(34, 348, 254, 26);

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/login/background.png")));
        backGround.setText("backGround");
        getContentPane().add(backGround);
        backGround.setBounds(0, 0, 359, 571);
        backGround.getAccessibleContext().setAccessibleName("background");

        pack();
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        loginButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/login/login_btn_h.png")));
        String uname = username.getText();
        String pword = new String(password.getPassword());

        int check;

        if (customer) {

            check = cus.login(uname, pword);

        } else {

            check = owner.login(uname, pword);
        }

        switch (check) {
            case 1:
                JOptionPane.showMessageDialog(this, "Successfully login", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                if (customer) {
                    new CusHome(uname).setVisible(true);
                } else {
                    new OwnerHome(uname).setVisible(true);
                }
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "Wrong Password!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(this, "User not found!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new Selection().setVisible(true);
    }

    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);

        if (customer) {
            new RegisterCustomer(cus).setVisible(true);
        } else {
            new RegisterOwner(owner).setVisible(true);
        }
    }
}
