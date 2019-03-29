/**
 *
 * @author NgoQuyen -- HUST -- K60
 */

package computernetworking;


public class GraphicsNetworking extends Calculator{
    
    public GraphicsNetworking(){
        super();
        activeDraw = false;
        // Hiển thị Frame
        f.setVisible(true);
    }

    public static void main(String[] args) {
        GraphicsNetworking gnw = new GraphicsNetworking();
    }
}