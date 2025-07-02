import javax.swing.*;
import java.awt.*;

public class ScoreboardPanel extends JPanel {
    private JLabel label;

    public ScoreboardPanel(Player p1, Player p2) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 16));
        add(label);
        updateScores(p1, p2, 0);
    }

    public void updateScores(Player p1, Player p2, int draws) {
        label.setText("🏆 " + p1.getName() + " (" + p1.getSymbol() + "): " + p1.getScore() +
                      " | " + p2.getName() + " (" + p2.getSymbol() + "): " + p2.getScore() +
                      " | 🤝 Draws: " + draws);
    }
}
