import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String p1Name = JOptionPane.showInputDialog(null, "Enter Player X name:");
            if (p1Name == null || p1Name.isBlank()) p1Name = "Player X";

            String p2Name = JOptionPane.showInputDialog(null, "Enter Player O name:");
            if (p2Name == null || p2Name.isBlank()) p2Name = "Player O";

            String[] options = {"3x3", "4x4", "5x5"};
            String sizeChoice = (String) JOptionPane.showInputDialog(null,
                    "Select grid size:", "Grid Size",
                    JOptionPane.QUESTION_MESSAGE, null, options, "3x3");

            int gridSize = switch (sizeChoice) {
                case "4x4" -> 4;
                case "5x5" -> 5;
                default -> 3;
            };

            Player player1 = new Player(p1Name, 'X');
            Player player2 = new Player(p2Name, 'O');

            JFrame frame = new JFrame("Tic-Tac-Toe - " + player1.getName() + " vs " + player2.getName());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 800);
            frame.setLayout(new BorderLayout());

            ScoreboardPanel scoreboard = new ScoreboardPanel(player1, player2);
            JLabel timerLabel = new JLabel("Timer: 10s", SwingConstants.CENTER);
            timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel topPanel = new JPanel(new GridLayout(2, 1));
            topPanel.add(scoreboard);
            topPanel.add(timerLabel);

            GameBoard board = new GameBoard(gridSize, player1, player2, scoreboard, timerLabel);

            JMenuBar menuBar = new JMenuBar();
            JMenu themeMenu = new JMenu("Theme");
            JMenuItem light = new JMenuItem("Light");
            JMenuItem dark = new JMenuItem("Dark");

            light.addActionListener(e -> ThemeManager.applyLightTheme(frame));
            dark.addActionListener(e -> ThemeManager.applyDarkTheme(frame));

            themeMenu.add(light);
            themeMenu.add(dark);
            menuBar.add(themeMenu);

            frame.setJMenuBar(menuBar);
            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(board, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
