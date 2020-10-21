import java.awt.Container;
import javax.swing.JFrame;

public class HakoGame extends JFrame {
    public HakoGame() {

        setTitle("HakoGameStudents");

        // create panel
        HakoPanel panel = new HakoPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        setResizable(false);

        
        pack();
    }

    public static void main(String[] args) {
        HakoGame frame = new HakoGame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}