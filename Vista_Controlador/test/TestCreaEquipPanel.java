import Vista.CreaEquip_JPanel;
import javax.swing.*;

public class TestCreaEquipPanel {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Test del CreaEquip_JPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        CreaEquip_JPanel panel = new CreaEquip_JPanel();
        frame.add(panel);

        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
