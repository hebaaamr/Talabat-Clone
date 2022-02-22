package mealManagment;

import users.*;
import userManagment.*;
import meal.Product;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditMeal extends JFrame {

    private JLabel backGround;
    private JButton editButton;
    private JButton browseButton;
    private JButton backButton;
    private JTextField mealName;
    private JTextField price;
    private JTextArea description;
    private AddMeal.ImagePanel img;
    private Dimension screenSize;
    private JComboBox comboBox;
    private String slectedImagepath;

    private String username;

    public EditMeal(String username) {
        this.username = username;
        initComponents();
        fillComboBox();
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        getContentPane().setBackground(new Color(255, 255, 255));

        editButton = new JButton();
        browseButton = new JButton();
        backButton = new JButton();
        mealName = new JTextField();
        price = new JTextField();
        description = new JTextArea();
        backGround = new JLabel();
        comboBox = new JComboBox();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 686));
        setMinimumSize(new Dimension(336, 601));
        setTitle("EditMeal");
        setResizable(false);
        setSize(new Dimension(336, 601));
        getContentPane().setLayout(null);

        editButton.setBackground(new Color(255, 90, 0));
        editButton.setForeground(new Color(255, 255, 255));
        editButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/edtmeal/edtmeal_btn.png")));
        editButton.setBorderPainted(false);
        editButton.setMargin(new Insets(0, 0, 0, 0));

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        getContentPane().add(editButton);
        editButton.setBounds(109, 527, 103, 29);

        browseButton.setBackground(new Color(255, 90, 0));
        browseButton.setForeground(new Color(255, 255, 255));
        browseButton.setIcon(new ImageIcon(getClass().getResource("/assets/images/addmeal/browseimg_btn.png")));
        browseButton.setBorderPainted(false);
        browseButton.setMargin(new Insets(0, 0, 0, 0));

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                browseButtonActionPerformed(evt);
                img = new AddMeal.ImagePanel(slectedImagepath);
                add(img);

                img.setBounds(34, 342, 254, 155);
            }
        });
        getContentPane().add(browseButton);
        browseButton.setBounds(34, 305, 103, 29);

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

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/edtmeal/background.png")));
        getContentPane().add(backGround);
        backGround.setBounds(0, 0, 359, 571);

        mealName.setText("");
        mealName.setBackground(new Color(235, 235, 235));
        mealName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        mealName.setFont(new java.awt.Font("Myraid Pro", 0, 18));
        getContentPane().add(mealName);
        mealName.setBounds(34, 81, 254, 31);

        price.setText("");
        price.setBackground(new Color(235, 235, 235));
        price.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        price.setFont(new java.awt.Font("Myraid Pro", 0, 18));
        getContentPane().add(price);
        price.setBounds(34, 142, 64, 31);

        description.setText("");
        description.setBackground(new Color(235, 235, 235));
        description.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(description);
        description.setBounds(110, 142, 178, 155);

        comboBox.setBounds(140, 15, 148, 32);
        comboBox.setFont(new java.awt.Font("Myraid Pro", 0, 18));
        getContentPane().add(comboBox);
        comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"None"}));

        price.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_priceKeyTyped(evt);
            }
        });

        pack();
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new OwnerHome(username).setVisible(true);
    }

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (mealName.getText().isEmpty() || description.getText().isEmpty() || price.getText().isEmpty() || slectedImagepath == null || (String.valueOf(comboBox.getSelectedItem())).equals("None")) {
            JOptionPane.showMessageDialog(this, "Please fill in all information!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            Owner owner = new Owner();
            Product product = new Product(mealName.getText(), description.getText(), Integer.valueOf(price.getText()), slectedImagepath, username);
            owner.editMeal(product, String.valueOf(comboBox.getSelectedItem()));

            this.setVisible(false);
            new OwnerHome(username).setVisible(true);
        }
    }

    private void browseButtonActionPerformed(ActionEvent evt) {
        JFileChooser browseimagefile = new JFileChooser();

        FileNameExtensionFilter fnef = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
        browseimagefile.addChoosableFileFilter(fnef);
        int showOpenDialogue = browseimagefile.showOpenDialog(null);

        if (showOpenDialogue == JFileChooser.APPROVE_OPTION) {
            File selectedImageFile = browseimagefile.getSelectedFile();
            slectedImagepath = selectedImageFile.getAbsolutePath();
        }
    }

    private void txt_priceKeyTyped(java.awt.event.KeyEvent evt) {

        char p = evt.getKeyChar();
        if (!(Character.isDigit(p)) || (p == KeyEvent.VK_BACK_SPACE) || (p == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }

    public class ImagePanel extends JPanel {

        private BufferedImage image;
        private String path;

        public ImagePanel(String path) {
            this.path = path;
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public BufferedImage resize(BufferedImage img, int newW, int newH) {
            Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            return dimg;
        }

        ImageIcon img = new ImageIcon(path);

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.resize(image, 254, 155), 0, 0, this); // see javadoc for more info on the parameters
        }
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
