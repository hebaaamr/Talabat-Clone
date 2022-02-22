package browseOrder;

import userManagment.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class OrderSelect extends JFrame {

    private JButton backButton;
    private JButton detailsButton;
    protected JComboBox comboBox;
    private JLabel backGround;
    private Dimension screenSize;
    private String username;
    protected boolean flag;

    public OrderSelect(String username) {
        initComponents();
        this.username = username;
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        getContentPane().setBackground(new Color(255, 255, 255));

        detailsButton = new JButton();
        backButton = new JButton();
        backGround = new JLabel();
        comboBox = new JComboBox();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 686));
        setMinimumSize(new Dimension(336, 601));
        setTitle("Orders");
        setResizable(false);
        setSize(new Dimension(336, 601));
        getContentPane().setLayout(null);

        detailsButton.setBackground(new Color(255, 90, 0));
        detailsButton.setForeground(new Color(255, 255, 255));
        detailsButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/browseordr/dtls_btn.png")));
        detailsButton.setBorderPainted(false);
        detailsButton.setMargin(new Insets(0, 0, 0, 0));

        detailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                detailsButtonActionPerformed(evt);
            }
        });
        getContentPane().add(detailsButton);
        detailsButton.setBounds(96, 468, 129, 36);

        backButton.setBackground(new Color(255, 90, 0));
        backButton.setForeground(new Color(255, 255, 255));
        backButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/common/back_btn.png")));
        backButton.setBorderPainted(false);
        backButton.setMargin(new Insets(0, 0, 0, 0));
        backButton.setMaximumSize(new Dimension(116, 31));
        backButton.setMinimumSize(new Dimension(116, 31));
        backButton.setPreferredSize(new Dimension(116, 31));

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        getContentPane().add(backButton);
        backButton.setBounds(3, 5, 43, 24);

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/browseordr/background_mid.png")));
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
        if (flag) {
            new OwnerHome(username).setVisible(true);
        } else {
            new CusHome(username).setVisible(true);
        }
    }

    private void detailsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (!(String.valueOf(comboBox.getSelectedItem())).equals("None")) {
            this.setVisible(false);
            new BrowseOrder(username, String.valueOf(comboBox.getSelectedItem()), flag).setVisible(true);
        }
    }

    protected abstract void fillComboBox();

    public String getUsername() {
        return username;
    }
}
