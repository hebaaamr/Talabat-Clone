package userManagment;

import orderManagment.*;
import browseOrder.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CusHome extends JFrame {

    private JLabel backGround;

    private JButton makeButton;
    private JButton browseOrderButton;
    private JButton menuButton;
    private JButton logoutButton;

    private Dimension screenSize;
    private String username;

    public CusHome(String username) {
        this.username = username;
        initComponents();
    }

    public CusHome() {
        initComponents();
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        makeButton = new JButton();
        browseOrderButton = new JButton();
        menuButton = new JButton();
        logoutButton = new JButton();

        backGround = new JLabel();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 686));
        setMinimumSize(new Dimension(336, 601));
        setTitle("Home");
        setResizable(false);
        setSize(new Dimension(336, 601));
        getContentPane().setLayout(null);

        makeButton.setBackground(new Color(255, 90, 0));
        makeButton.setForeground(new Color(255, 255, 255));
        makeButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/cus/mkord_btn.png")));
        makeButton.setBorderPainted(false);
        makeButton.setMargin(new Insets(0, 0, 0, 0));
        makeButton.setMaximumSize(new Dimension(116, 31));
        makeButton.setMinimumSize(new Dimension(116, 31));
        makeButton.setPreferredSize(new Dimension(116, 31));

        makeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                makeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(makeButton);
        makeButton.setBounds(92, 392, 137, 39);

        browseOrderButton.setBackground(new Color(255, 90, 0));
        browseOrderButton.setForeground(new Color(255, 255, 255));
        browseOrderButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/cus/browseord_btn.png")));
        browseOrderButton.setBorderPainted(false);
        browseOrderButton.setMargin(new Insets(0, 0, 0, 0));
        browseOrderButton.setMaximumSize(new Dimension(116, 31));
        browseOrderButton.setMinimumSize(new Dimension(116, 31));
        browseOrderButton.setPreferredSize(new Dimension(116, 31));

        browseOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                browseOrderButtonActionPerformed(evt);
            }
        });
        getContentPane().add(browseOrderButton);
        browseOrderButton.setBounds(92, 445, 137, 39);

        logoutButton.setBackground(new Color(255, 90, 0));
        logoutButton.setForeground(new Color(255, 255, 255));
        logoutButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/common/logout_btn.png")));
        logoutButton.setBorderPainted(false);
        logoutButton.setMargin(new Insets(0, 0, 0, 0));
        logoutButton.setMaximumSize(new Dimension(116, 31));
        logoutButton.setMinimumSize(new Dimension(116, 31));
        logoutButton.setPreferredSize(new Dimension(116, 31));

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        getContentPane().add(logoutButton);
        logoutButton.setBounds(92, 500, 137, 39);

        menuButton.setBackground(new Color(255, 90, 0));
        menuButton.setForeground(new Color(255, 255, 255));
        menuButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/cus/menu_btn.png")));
        menuButton.setBorderPainted(false);
        menuButton.setMargin(new Insets(0, 0, 0, 0));
        menuButton.setMaximumSize(new Dimension(116, 31));
        menuButton.setMinimumSize(new Dimension(116, 31));
        menuButton.setPreferredSize(new Dimension(116, 31));

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });
        getContentPane().add(menuButton);
        menuButton.setBounds(92, 339, 137, 39);

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/common/background.png")));
        backGround.setText("backGround");
        getContentPane().add(backGround);
        backGround.setBounds(0, 0, 359, 571);

        pack();
    }

    private void makeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        makeButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/cus/mkord_btn_h.png")));
        this.setVisible(false);
        new MakeOrder(username).setVisible(true);
    }

    private void browseOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {
        browseOrderButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/cus/browseord_btn_h.png")));
        this.setVisible(false);
        new CusOrderSelect(username).setVisible(true);
    }

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {
        menuButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/cus/menu_btn_h.png")));
        this.setVisible(false);
        new MealInfSelect(username).setVisible(true);
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new Selection().setVisible(true);
    }
}
