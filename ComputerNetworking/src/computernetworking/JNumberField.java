/**
 *
 * @author NgoQuyen -- HUST -- K60
 */

package computernetworking;

import java.awt.event.KeyEvent;
import javax.swing.JFormattedTextField;

/**
 *
 * @author NgoQuyen
 */
public class JNumberField extends JFormattedTextField  {
    private static final long serialVersionUID = 1L;

    JNumberField() {
    }

    @Override
    public void processKeyEvent(KeyEvent ev) {
        if ((ev.getKeyChar()  == '\b') || Character.isDigit(ev.getKeyChar())) {
            super.processKeyEvent(ev);
        }
        ev.consume();
    }
    public int getNumber() {
        int result = 0;
        String text = getText();
        if (text != null && !"".equals(text)) {
            result = Integer.parseInt(text);
        }
        return result;
    }
}
