package orderManagment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import meal.Product;

public class MealInf extends JFrame {

    private JLabel backGround;
    private JButton backButton;
    private JLabel mealName;
    private JLabel price;
    private JLabel description;
    private ImagePanel img;
    private Dimension screenSize;
    private String slectedImagepath;
    private String username;

    public MealInf(String rst, String meal, String username) {
        this.username = username;
        initComponents();
        mealinfselect(rst, meal);
    }

    private void initComponents() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        backButton = new JButton();
        mealName = new JLabel();
        price = new JLabel();
        description = new JLabel();
        backGround = new JLabel();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle((int) width * 38 / 100, (int) height / 10, 406, 686));
        setMinimumSize(new Dimension(336, 601));
        setTitle("MealInf");
        setResizable(false);
        setSize(new Dimension(336, 601));
        getContentPane().setLayout(null);

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

        backGround.setIcon(new ImageIcon(getClass().getResource("/assets/images/addmeal/background.png")));
        getContentPane().add(backGround);
        backGround.setBounds(0, 0, 359, 571);

        mealName.setBackground(new Color(235, 235, 235));
        mealName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(mealName);
        mealName.setBounds(34, 81, 254, 31);

        price.setBackground(new Color(235, 235, 235));
        price.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(price);
        price.setBounds(34, 142, 64, 31);

        getContentPane().setBackground(new Color(255, 255, 255));

        description.setBackground(new Color(235, 235, 235));
        description.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getContentPane().add(description);
        description.setBounds(110, 142, 178, 155);

        pack();
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new MealInfSelect(username).setVisible(true);
    }

    public void mealinfselect(String rst, String meal) {
        java.util.List<Product> productList = new ArrayList<Product>();
        Product product = new Product();
        productList = product.productInfo(rst, meal);

        for (Product pro : productList) {
            mealName.setText(meal);
            price.setText(String.valueOf(pro.getProdutPrice()));
            description.setText(pro.getDescription());
            slectedImagepath = (pro.getPhotoPath());
            img = new ImagePanel(slectedImagepath);
            add(img);
            img.setBounds(34, 342, 254, 155);
        }

    }

    public static class ImagePanel extends JPanel {

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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.resize(image, 254, 155), 0, 0, this); // see javadoc for more info on the parameters
        }
    }
}
