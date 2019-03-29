/**
 *
 * @author NgoQuyen -- HUST -- K60
 */

package computernetworking;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


// class này có nhiệm vụ chạy tiến trình di chuyển thư
public class TransmisionMessage implements Runnable{
    int split = 50; // dộ mịn di chuyển
    JLabel mesLabel;
    JPanel panel;
    Point A;
    Point B;
    int numPeer;
    ImageIcon icon;
    
    boolean isFinished = false;
    public TransmisionMessage(JLabel mesLabel, JPanel panel,
            Point source, Point dest, int numPeer) {
        
        this.mesLabel = mesLabel;
        this.panel = panel;
        this.A = source;
        this.B = dest;
        this.numPeer = numPeer;
        panel.add(mesLabel); // Đặt mày đúng chỗ (Bugs thứ 2 được fix)
        
        try {
            BufferedImage buff = ImageIO.read(new File("img/message/96.png"));
            //buff = rotate(buff, Math.PI/3);
            icon = new ImageIcon(buff.getScaledInstance(buff.getWidth(), buff.getHeight(), BufferedImage.SCALE_SMOOTH));
            mesLabel.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(GraphicsNetworking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        int tempSize = 96/2;
        int couter = 0;
        int temp_x = A.x, temp_y = A.y;
        int dx = (B.x - A.x) / split, dy = (B.y - A.y) / split; 
        
        // Điều kiện di chuyển.
        while (couter < (split)) { // Số lần lặp
            mesLabel.setBounds(temp_x - tempSize,
                               temp_y - tempSize, 96, 96);
            
            
            temp_x += dx;
            temp_y += dy;
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(TransmisionMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
            couter++;
        }
        mesLabel.setBounds(B.x - tempSize, B.y - tempSize, 96, 96);
        //mesLabel.setIcon(new ImageIcon("img/message/96.png"));
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                isFinished = true;
            }
         });
    }
}