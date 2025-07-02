import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
boardPanel = new JPanel(new GridLayout(4, 4)); // or 5x5
boardPanel.setBackground(new Color(48, 25, 52)); // Dark purple

for (int i = 0; i < buttons.length; i++) {
    for (int j = 0; j < buttons[i].length; j++) {
        buttons[i][j] = new JButton();
        buttons[i][j].setFont(new Font("Verdana", Font.BOLD, 32));
        buttons[i][j].setBackground(new Color(132, 94, 194)); // Light purple
        buttons[i][j].setForeground(Color.WHITE);
        buttons[i][j].setFocusPainted(false);
        boardPanel.add(buttons[i][j]);
    }
}

public class GameBoard extends JPanel {
    private JButton[][] buttons;
    private Player player1, player2, currentPlayer;
    private int gridSize, moveCount, drawCount;
    private ScoreboardPanel scoreboard;
    private Timer turnTimer;
    private JLabel timerLabel;

    public GameBoard(int size, Player p1, Player p2, ScoreboardPanel sb, JLabel timerLabel) {
        this.gridSize = size;
        this.player1 = p1;
        this.player2 = p2;
        this.scoreboard = sb;
        this.timerLabel = timerLabel;
        this.currentPlayer = p1;
        this.moveCount = 0;
        this.drawCount = 0;

        setLayout(new GridLayout(gridSize, gridSize));
        buttons = new JButton[gridSize][gridSize];
        initializeBoard();
    }

    private void initializeBoard() {
        removeAll();
        moveCount = 0;
        currentPlayer = player1;

        Font font = new Font("Arial", Font.BOLD, 30);

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JButton btn = new JButton("");
                btn.setFont(font);
                int finalI = i, finalJ = j;
                btn.addActionListener(e -> handleClick(btn, finalI, finalJ));
                buttons[i][j] = btn;
                add(btn);
            }
        }

        startTimer();
        revalidate();
        repaint();
    }

    private void handleClick(JButton btn, int i, int j) {
        if (!btn.getText().equals("")) return;

        SoundPlayer.play("click.wav");
        btn.setText(String.valueOf(currentPlayer.getSymbol()));
        btn.setEnabled(false);
        moveCount++;

        if (checkWin()) {
            SoundPlayer.play("win.wav");
            JOptionPane.showMessageDialog(this,
                    "🎉 " + currentPlayer.getName() + " wins!");
            currentPlayer.addScore();
            scoreboard.updateScores(player1, player2, drawCount);
            ScoreSaver.saveScores(player1, player2, drawCount);
            resetBoard();
        } else if (moveCount == gridSize * gridSize) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            drawCount++;
            scoreboard.updateScores(player1, player2, drawCount);
            ScoreSaver.saveScores(player1, player2, drawCount);
            resetBoard();
        } else {
            switchPlayer();
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        startTimer();
    }

    private boolean checkWin() {
        String sym = String.valueOf(currentPlayer.getSymbol());

        for (int i = 0; i < gridSize; i++) {
            boolean row = true, col = true;
            for (int j = 0; j < gridSize; j++) {
                if (!buttons[i][j].getText().equals(sym)) row = false;
                if (!buttons[j][i].getText().equals(sym)) col = false;
            }
            if (row || col) return true;
        }

        boolean diag1 = true, diag2 = true;
        for (int i = 0; i < gridSize; i++) {
            if (!buttons[i][i].getText().equals(sym)) diag1 = false;
            if (!buttons[i][gridSize - 1 - i].getText().equals(sym)) diag2 = false;
        }

        return diag1 || diag2;
    }

    private void startTimer() {
        if (turnTimer != null) turnTimer.stop();

        int[] timeLeft = {10}; // 10 seconds per move
        timerLabel.setText("Timer: " + timeLeft[0] + "s");

        turnTimer = new Timer(1000, e -> {
            timeLeft[0]--;
            timerLabel.setText("Timer: " + timeLeft[0] + "s");

            if (timeLeft[0] == 0) {
                JOptionPane.showMessageDialog(this, currentPlayer.getName() + " ran out of time! Turn skipped.");
                switchPlayer();
                turnTimer.stop();
            }
        });
        turnTimer.start();
    }

    private void resetBoard() {
        if (turnTimer != null) turnTimer.stop();

        int choice = JOptionPane.showConfirmDialog(this, "Play again?", "Replay", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            initializeBoard();
        } else {
            SwingUtilities.getWindowAncestor(this).dispose();
        }
    }
}
