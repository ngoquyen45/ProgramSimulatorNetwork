/**
 *
 * @author NgoQuyen -- HUST -- K60
 */

package computernetworking;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author NgoQuyen
 */
public class Calculator  implements MouseListener{
    JLabel imageMessage1;
    JLabel imageMessage2;
    JLabel imageMessage3;
    
    
    int w_p = 600, h_p = 600;
    int w_f = 900, h_f = 600;
    
    
    
    JPanel pControl;    int w_pControl = 280,   h_pControl = 130; // Sẽ sửa
    JLabel lbSpeed;     int w_lbSpeed = 150,    h_lbSpeed = 25;
    JTextField tfSpeed; int w_tfSpeed = 30,     h_tfSpeed = 25;
    
    JButton bPlayPause; ImageIcon imgiconPlayPause;
    JButton bNext;      ImageIcon imgiconNext;
    JButton bBack;      ImageIcon imgiconBack;
    
    int state = 0;// Trạng thái pause của bPlayPause nhưng được xét ảnh play
    String[] tableStatePlayPauseDark = {
        "play_dark.png",
        "pause_dark.png"
    };
    String[] tableStatePlayPauseLight = {
        "play_light.png",
        "pause_light.png"
    };
    String[] tableStateToolTip = {
        "Play",
        "Pause"
    };
    
    
    
    boolean activeDraw = false; // Kích hoạt vẽ
    Point[] point; // Tọa độ máy tính
    Random random;
    // Bảng String
    String[] stringTable = {
        "laptop",
        "pc",
        "server",
        "iphone",
        "galaxy",
        "database",
        "printer",
        "macbook",
        "TVsamsung"
    };
    
    // Label
    JLabel[] labelText;
    // Số lượng peer
    int numPeer = 20; // Khởi tạo số peer Maximum.
    int oldNumPeer = 0; // Biến để lưu lại peer cũ
    
    //space: Khoảng cách giữa các thành phần
    int space = 10;
    
    // String html để đặt tên cho dự án
    String html = "<html>"
                + "<body style=\"font-weight:bold; text-align: center;\">"
                + "PHẦN MỀM MÔ PHỎNG <br> TÊN DỰ ÁN CỦA BẠN Ở ĐÂY"
                + "</body>"
                + "</html>";
    // Khai báo
    JFrame f; // Cửa sổ ứng dụng của chúng ta
    JPanel p; // Vùng Chạy đồ họa
    
    JButton closeButton = new JButton(new ImageIcon("img/tool/close_30.png"));; // Nút close
    
    // Khai báo cho cụm nhập số liệu
    JPanel pNumPeer;
    JLabel label_pNP;
    JNumberField numberField_pNP = null;
    JButton button_pNP;
    
    JLabel nameSoftware = new JLabel(html, JLabel.CENTER);
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int wScreen = screenSize.width;
    int hScreen = screenSize.height;

    public Calculator() {
        initialFrame();
        
        // Khởi tạo ramdom thneiết bị
        random = new Random();
        
        // Khởi tạo tọa độ điểm để vẽ các Peer
        point = new Point[numPeer];
        for (int i = 0; i < numPeer; i++) {
            point[i] = new Point();
        }
        
        initialPanel();
        
        // Khởi tạo nhãn để đưa ảnh vào các tọa độ điểm cho trước.
        labelText = new JLabel[numPeer];
        for (int i = 0; i < numPeer; i++) {
            labelText[i] = new JLabel();
            p.add(labelText[i]);
        }
        
        // Naming Software
        setNameProgram ();
        
        // Create close
        setCloseButton();
        p.add(closeButton);
        
        /** Thiết lập panel nhập số peer*/
        setPanelForInputPeer ();
        setPanelForInputControler();
        
    }
    
    public void initialFrame() {
        //Setting for Frame
        f = new JFrame();
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.darkGray);
        f.setLocation((wScreen - w_f) / 2, (hScreen - h_f) / 2);
        f.setSize(w_f, h_f);
        f.setResizable(false);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void initialPanel() {
        //Setting for Panel
        //Khởi tạo Panel
        p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (activeDraw) {
                    System.out.println("Testing ...");
                    for (int i = 0; i < (numPeer - 1); i++) {
                        for (int j = (i + 1); j < numPeer; j++) {
                            g.setColor(Color.BLUE);
                            g.drawLine(point[i].x, point[i].y, point[j].x, point[j].y);
                        }
                    }
                }
            }
        };
        p.setToolTipText("Đồ họa sẽ hiện ở đây");
        p.setLayout(null);
        p.setBackground(Color.GRAY);
        p.setSize(w_p, h_p);
        f.add(p);
    }
    
    public void setPanelForInputPeer () {
        /** Thiết lập panel nhập số peer*/
        pNumPeer = new JPanel();
        pNumPeer.setLayout(null);
        pNumPeer.setBackground(new Color(227, 155, 155));
        pNumPeer.setBounds(w_p + space, 
                nameSoftware.getHeight() + space, (w_f -  w_p - space - space), 80);
        f.add(pNumPeer);
        // Các thành phần con của Panel nhập số Peer
        label_pNP = new JLabel("Số lượng Peer:", JLabel.LEFT);
        label_pNP.setFont(new Font("Arial", Font.BOLD, 16));
        label_pNP.setForeground(Color.WHITE);
        label_pNP.setBounds(20, 10, 140, 25);
        pNumPeer.add(label_pNP);
        
        
        numberField_pNP = new JNumberField();
        //numberField_pNP.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        numberField_pNP.setText("2");
        numberField_pNP.setFont(new Font("Arial", Font.PLAIN, 16));
        numberField_pNP.setBounds(260 - 30, 10, 30, 25);
        pNumPeer.add(numberField_pNP);
        
        
        button_pNP = new JButton("Create Peers");
        button_pNP.setOpaque(true);
        button_pNP.setBackground(new Color (240, 230, 230));
        button_pNP.setFont(new Font("Arial", Font.BOLD, 16));
        button_pNP.setBounds((pNumPeer.getWidth() - 135) / 2,
                pNumPeer.getHeight()- space - 25, 135, 25);
        button_pNP.addMouseListener(this);

        pNumPeer.add(button_pNP);
    }
    
    
    public void setPanelForInputControler() {
        /*--------------------------------------------------------------------*/
        // Chia panel làm hai
        int x_temp = 0, y_temp = 0;
        int w_temp = 0, h_temp = 0;
        
        // Setting pControl cho phần điều khiển
        pControl = new JPanel();
        pControl.setSize(w_pControl, h_pControl);
        pControl.setLayout(null);
        pControl.setBackground(new Color(227, 155, 155));
        pControl.setBounds(610, 200, w_pControl, h_pControl);
        f.add(pControl);
        
        // Setting lbSpeed cho phần điều khiển
        lbSpeed = new JLabel("Tốc độ mô phỏng:");
        lbSpeed.setFont(new Font("Arial", Font.BOLD, 16));
        lbSpeed.setBounds(20, 10, w_lbSpeed, h_lbSpeed);
        lbSpeed.setForeground(Color.WHITE);
        pControl.add(lbSpeed);
        
        // Setting tfSpeed cho phần điều khiển
        tfSpeed = new JTextField("1.0");
        tfSpeed.setFont(new Font("Arial", Font.PLAIN, 16));
        tfSpeed.setBounds(w_pControl - 20 - w_tfSpeed, 10, w_tfSpeed, h_tfSpeed);
        pControl.add(tfSpeed);
//        
//        
//        
//        
        /***************************/
        
        
        // Setting bPlayPause cho phần điều khiển
        imgiconPlayPause = new ImageIcon("img/tool/play_dark.png");
        bPlayPause = new JButton(imgiconPlayPause);
        bPlayPause.setOpaque(false);
        bPlayPause.setContentAreaFilled(false);
        bPlayPause.setBorderPainted(false);
        w_temp = imgiconPlayPause.getIconWidth();
        h_temp = imgiconPlayPause.getIconHeight();
        x_temp = (w_pControl - w_temp) / 2;
        y_temp = 3*h_pControl/4 - h_temp/2 - 15;
        
        bPlayPause.setBounds(x_temp, y_temp, w_temp, h_temp);
        pControl.add(bPlayPause);
        // Sự kiện
        bPlayPause.addMouseListener(this);
        
        
        // Setting bNext cho phần điều khiển
        imgiconNext = new ImageIcon("img/tool/next_dark.png");
        bNext = new JButton(imgiconNext);
        bNext.setToolTipText("Next");
        bNext.setOpaque(false);
        bNext.setContentAreaFilled(false);
        bNext.setBorderPainted(false);
        w_temp = imgiconNext.getIconWidth();
        h_temp = imgiconNext.getIconHeight();
        x_temp = 3*w_pControl/4 - w_temp/2;
        y_temp = 3*h_pControl/4 - h_temp/2 - 15;
        bNext.setBounds(x_temp, y_temp, w_temp, h_temp);
        pControl.add(bNext);
        // Sự kiện
        bNext.addMouseListener(this);
        
        
        // Setting bBack cho phần điều khiển
        imgiconBack = new ImageIcon("img/tool/back_dark.png");
        bBack = new JButton(imgiconBack);
        bBack.setToolTipText("Back");
        bBack.setOpaque(false);
        bBack.setContentAreaFilled(false);
        bBack.setBorderPainted(false);
        w_temp = imgiconBack.getIconWidth();
        h_temp = imgiconBack.getIconHeight();
        x_temp = (w_pControl / 2 - w_temp) / 2;
        y_temp = 3*h_pControl/4 - h_temp/2 - 15;
        bBack.setBounds(x_temp, y_temp, w_temp, h_temp);
        pControl.add(bBack);
        // Sự kiện
        bBack.addMouseListener(this);
    }
    
    public void setNameProgram () {
        nameSoftware.setOpaque(true);
        nameSoftware.setBackground(new Color(104, 124, 210));
        nameSoftware.setForeground(Color.WHITE);
        nameSoftware.setFont(new Font("Times New Roman", Font.BOLD, 20));
        nameSoftware.setBounds(w_p, 0, (w_f -  w_p), 100);
        f.add(nameSoftware);
    }

    public void setCloseButton() {
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setBounds(0, 0, 30, 30);
        
        closeButton.setToolTipText("close");
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public  void mousePressed(MouseEvent e) {
                closeButton.setIcon(new ImageIcon("img/tool/close_30_faded.png"));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
                f.dispose();
            }
        });
    }
    
    public Point[] calCoordinates(int numPoint,
            int w_p, int h_p,
            int w_f, int h_f) { // Loction for image
        Point[] point = new Point[numPoint];
        int sizeImg = imgSize(numPoint);
        int R = (h_f - sizeImg) / 2; // Bán kính hình tròn
        double alpha = 2*Math.PI / numPoint;
        for (int i = 0; i < numPoint; i++) {
            point[i] = new Point ();
            point[i].x = (int) Math.round(w_p / 2 + R * Math.cos(i*alpha));
            point[i].y = (int) Math.round(h_p / 2 + R * Math.sin(i*alpha));
        }
        return point;
        
    }
    
        // Hàm convert point-to-point => góc (radian)
    
    public double convertToAngle (Point source, Point dest) {

        double x = dest.x - source.x;
        double y = dest.y - source.y;
        // Tính góc giữa vector(vertor.x, vector.y) và véc tơ i(1, 0)
        double cos_angle = x / (x * x + y * y);
        
        return Math.acos(cos_angle);
    }
    
    
    // Hàm trả về kích thước ảnh
    public int imgSize (int numPoint) {
        int sizeImg = 0;
        if (2 <= numPoint && numPoint <= 5) {// Ảnh 128
            sizeImg = 128;
        }
        else if (5 < numPoint && numPoint <= 10) {// Ảnh 96
            sizeImg = 96;
        }
        else if (10 < numPoint && numPoint <= 15) {// Ảnh 72
            sizeImg = 72;
        }
        else if (15 < numPoint && numPoint <= 20) {// Ảnh 64
            sizeImg = 64;
        }
        return sizeImg;
    }
    
    
    // Hàm giúp quay ảnh. Chiều quay theo chiều kim đồng hồ.
    public BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    // Tác dụng để gán ảnh tới các point tính được
    @Override
    public void mousePressed(MouseEvent e) {
        JButton b = (JButton)e.getSource();
        if (b == button_pNP) {
            // Khi có sự kiện, Hàm khởi tạo của class được load lại
            activeDraw = false; // Tắt vẽ
            b.setMultiClickThreshhold(1000);
            oldNumPeer = numPeer;
            for (int i = 0; i < oldNumPeer; i++) {
                labelText[i].setIcon(null); // Quang trong de xoa bo dem
                // Khi numPeer giam xuong
            }

            // Không thể khởi tạo đối tượng trong đây
            numPeer = numberField_pNP.getNumber();
            

            if (2 <= numPeer && numPeer <= 20) {
                System.out.println("OK");
                // Hàm để tính toán tọa độ
                point = calCoordinates(numPeer, w_p, h_p, w_f, h_f);

                int tempSize = imgSize(numPeer);
                for (int i = 0; i < numPeer; i++) {
                    System.out.println(point[i].x + "  " + point[i].y);
                }

                String tempString;
                for (int i = 0; i < numPeer; i++) {
                    labelText[i].setBounds(point[i].x - tempSize/2, point[i].y - tempSize/2, tempSize, tempSize);
                    tempString = stringTable[random.nextInt(stringTable.length)] + "/";
                    labelText[i].setIcon(new ImageIcon("img/" + tempString + imgSize(numPeer) + ".png"));
                }
            }

            activeDraw = true; // Kích hoạt lại vẽ
            p.repaint(); // Vẽ đường dây ethernet (Bug thứ nhất fix)
        }
        else if (b == bPlayPause ) {
            state = (++state) % 2;
            b.setToolTipText(tableStateToolTip[state]);
            b.setIcon(new ImageIcon("img/tool/" + tableStatePlayPauseLight[state]));
            imageMessage1 = new JLabel();
            imageMessage2 = new JLabel();
            imageMessage3 = new JLabel();
            TransmisionMessage X = new TransmisionMessage(imageMessage1, p, point[0], point[1], numPeer);
            Thread t1 = new Thread(X);

            TransmisionMessage Y = new TransmisionMessage(imageMessage2, p, point[1], point[2], numPeer);
            Thread t2 = new Thread(Y);
            

            TransmisionMessage Z = new TransmisionMessage(imageMessage3, p, point[2], point[0], numPeer);
            Thread t3 = new Thread(Z);
            
            
            if (state == 1) {
                t1.start();
                t2.start();
                t3.start();
            }
            
            
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        JButton b = (JButton)e.getSource();
        if (b == bPlayPause) {
            b.setToolTipText(tableStateToolTip[state]);
            b.setIcon(new ImageIcon("img/tool/" + tableStatePlayPauseLight[state]));
        }
        else if (b == bBack) {
            b.setIcon(new ImageIcon("img/tool/back_light.png"));
        }
        else if (b == bNext) {
            b.setIcon(new ImageIcon("img/tool/next_light.png"));
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        JButton b = (JButton)e.getSource();
        if (b == bPlayPause) {
            b.setIcon(new ImageIcon("img/tool/" + tableStatePlayPauseDark[state]));
        }
        else if (b == bBack) {
            b.setIcon(new ImageIcon("img/tool/back_dark.png"));
        }
        else if (b == bNext) {
            b.setIcon(new ImageIcon("img/tool/next_dark.png"));
        }
    }
}
