package userManagment;

import users.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterOwner extends JFrame {

    private JLabel backGround;
    private JButton signupButton;
    private JButton backButton;
    private JTextField username;
    private JTextField rstName;
    private JPasswordField password;
    private Dimension screenSize;
    private Owner owner;

    public RegisterOwner(Owner o) {
        owner = o;
        initComponents();
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        signupButton = new JButton();
        backButton = new JButton();
        backGround = new JLabel();
        username = new JTextField();
        password = new JPasswordField();
        rstName = new JTextField();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 656));
        setMinimumSize(new Dimension(336, 571));
        setTitle("Register");
        setResizable(false);
        setSize(new Dimension(336, 571));
        getContentPane().setLayout(null);

        signupButton.setBackground(new Color(255, 90, 0));
        signupButton.setFont(new Font("Microsoft Sans Serif", 1, 14));
        signupButton.setForeground(new Color(255, 255, 255));
        signupButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/signup/signup_btn.png")));
        signupButton.setToolTipText("");
        signupButton.setBorderPainted(false);
        signupButton.setMargin(new Insets(0, 0, 0, 0));
        signupButton.setMaximumSize(new Dimension(116, 31));
        signupButton.setMinimumSize(new Dimension(116, 31));
        signupButton.setPreferredSize(new Dimension(116, 31));

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    signupButtonActionPerformed(evt);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        getContentPane().add(signupButton);
        signupButton.setBounds(103, 480, 116, 31);

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

        username.setBackground(new Color(242, 242, 242));
        getContentPane().add(username);
        username.setBounds(43, 232, 237, 26);

        password.setText("");
        password.setBackground(new Color(242, 242, 242));
        getContentPane().add(password);
        password.setBounds(43, 287, 237, 26);

        rstName.setText("");
        rstName.setBackground(new Color(242, 242, 242));
        getContentPane().add(rstName);
        rstName.setBounds(43, 342, 237, 26);

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/signup/background_owner.png")));
        backGround.setText("backGround");
        getContentPane().add(backGround);
        backGround.setBounds(0, 0, 359, 571);
        backGround.getAccessibleContext().setAccessibleName("background");

        pack();
    }

    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {

        if ((username.getText().isEmpty()) || ((String.valueOf(password.getPassword())).isEmpty()) || (rstName.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Please fill in all information!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            Owner owner = new Owner(username.getText(), String.valueOf(password.getPassword()), rstName.getText());
            owner.registerValidation(); //Check uniqueness of Username and Restaurant's name

            if (owner.getIsUnique() == 1) { //Unique info
                owner.register();
                JOptionPane.showMessageDialog(this, "Successfully Sign Up", "Welcome", JOptionPane.INFORMATION_MESSAGE);

                this.setVisible(false);
                new OwnerHome(owner.getUserName()).setVisible(true);
            } else if (owner.getIsUnique() == 2) { //User Name is used
                JOptionPane.showMessageDialog(this, "User Name is used!!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (owner.getIsUnique() == 3) { //Restaurant Name is used
                JOptionPane.showMessageDialog(this, "Resturant Name is used!!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new LoginPanel(owner).setVisible(true);
    }
}
