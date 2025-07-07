import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.BufferedImage;

public class XOGame {
    private static String playerXName = "Crewmate";
    private static String playerOName = "Impostor";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(XOGame::showWelcomeScreen);
    }

    private static void showWelcomeScreen() {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.show();
    }

    private static void startGame(String playerX, String playerO) {
        GameScreen gameScreen = new GameScreen(playerX, playerO);
        gameScreen.show();
    }

    // Welcome Screen Class
    static class WelcomeScreen {
        private JFrame frame;

        public void show() {
            frame = new JFrame("Among Us Tic Tac Toe - Welcome");
            frame.setSize(500, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel backgroundPanel = createAmongUsBackgroundPanel();
            backgroundPanel.setLayout(new GridBagLayout());

            JPanel welcomePanel = createWelcomePanel(frame);
            
            backgroundPanel.add(welcomePanel);
            frame.add(backgroundPanel, BorderLayout.CENTER);
            
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        private JPanel createAmongUsBackgroundPanel() {
            return new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    drawAmongUsBackground(g, getWidth(), getHeight());
                }
            };
        }

        private JPanel createWelcomePanel(JFrame frame) {
            JPanel welcomePanel = new JPanel(new GridLayout(4, 1, 10, 20));
            welcomePanel.setOpaque(false);
            welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

            JLabel titleLabel = new JLabel("Among Us Tic Tac Toe", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
            titleLabel.setForeground(Color.WHITE);

            JLabel subtitleLabel = new JLabel("Crewmate vs Impostor", SwingConstants.CENTER);
            subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
            subtitleLabel.setForeground(Color.WHITE);

            JPanel namePanel = createNamePanel();
            
            JButton continueButton = new JButton("Start Game");
            continueButton.setBackground(new Color(255, 50, 50));
            continueButton.setForeground(Color.WHITE);
            continueButton.setFont(new Font("Arial", Font.BOLD, 16));
            
            continueButton.addActionListener(e -> {
                playerXName = ((JTextField)namePanel.getComponent(1)).getText().trim();
                playerOName = ((JTextField)namePanel.getComponent(3)).getText().trim();
                
                if (playerXName.isEmpty()) playerXName = "Crewmate";
                if (playerOName.isEmpty()) playerOName = "Impostor";
                
                frame.dispose();
                startGame(playerXName, playerOName);
            });

            welcomePanel.add(titleLabel);
            welcomePanel.add(subtitleLabel);
            welcomePanel.add(namePanel);
            welcomePanel.add(continueButton);

            return welcomePanel;
        }

        private JPanel createNamePanel() {
            JPanel namePanel = new JPanel(new GridLayout(2, 2, 10, 10));
            namePanel.setOpaque(false);

            JTextField playerXField = new JTextField("Crewmate");
            JTextField playerOField = new JTextField("Impostor");

            namePanel.add(new JLabel("Crewmate Name:"));
            namePanel.add(playerXField);
            namePanel.add(new JLabel("Impostor Name:"));
            namePanel.add(playerOField);

            return namePanel;
        }
    }

    // Game Screen Class
    static class GameScreen {
        private JFrame frame;
        private String playerXName;
        private String playerOName;
        private boolean xTurn = true;
        private JButton[] buttons = new JButton[9];
        private ImageIcon[] amongUsIcons = new ImageIcon[2];

        public GameScreen(String playerX, String playerO) {
            this.playerXName = playerX;
            this.playerOName = playerO;
        }

        public void show() {
            frame = new JFrame("Among Us Tic Tac Toe - Crewmate vs Impostor");
            frame.setSize(600, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Game panel with Among Us background
            JPanel gamePanel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    drawAmongUsBackground(g, getWidth(), getHeight());
                }
            };
            
            // Game grid
            JPanel gridPanel = new JPanel(new GridLayout(3, 3));
            gridPanel.setOpaque(false);
            gridPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
            
            // Initialize buttons
            for (int i = 0; i < 9; i++) {
                buttons[i] = new JButton();
                buttons[i].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i].setFocusPainted(false);
                buttons[i].setContentAreaFilled(false);
                buttons[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
                buttons[i].setForeground(Color.WHITE);
                gridPanel.add(buttons[i]);
            }
            
            // Status label
            JLabel statusLabel = new JLabel(playerXName + "'s Turn", SwingConstants.CENTER);
            statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
            statusLabel.setForeground(Color.WHITE);
            statusLabel.setOpaque(true);
            statusLabel.setBackground(new Color(0, 0, 0, 150));
            
            gamePanel.add(gridPanel, BorderLayout.CENTER);
            gamePanel.add(statusLabel, BorderLayout.SOUTH);
            
            frame.add(gamePanel, BorderLayout.CENTER);
            
            // Initialize Among Us icons
            initializeAmongUsIcons();
            
            // Add button actions
            for (JButton button : buttons) {
                button.addActionListener(e -> handleButtonClick(button, statusLabel));
            }
            
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        private void initializeAmongUsIcons() {
            // Create Crewmate icon (X)
            amongUsIcons[0] = createAmongUsIcon("#00FF00", "Crewmate");
            
            // Create Impostor icon (O)
            amongUsIcons[1] = createAmongUsIcon("#FF0000", "Impostor");
        }

        private void handleButtonClick(JButton button, JLabel statusLabel) {
            if (button.getIcon() == null) {
                if (xTurn) {
                    button.setIcon(amongUsIcons[0]);
                } else {
                    button.setIcon(amongUsIcons[1]);
                }
                
                if (checkWinner()) {
                    String winner = xTurn ? playerXName : playerOName;
                    String winnerType = xTurn ? "Crewmate" : "Impostor";
                    JOptionPane.showMessageDialog(frame, 
                        "Game Over!\n" + winner + " (" + winnerType + ") wins!", 
                        "Victory", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    showWelcomeScreen();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(frame, 
                        "Game Over!\nIt's a draw!", 
                        "Draw", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    showWelcomeScreen();
                } else {
                    xTurn = !xTurn;
                    statusLabel.setText((xTurn ? playerXName : playerOName) + "'s Turn");
                }
            }
        }

        private boolean checkWinner() {
            // Check rows
            for (int i = 0; i < 9; i += 3) {
                if (buttons[i].getIcon() != null && 
                    buttons[i].getIcon().equals(buttons[i+1].getIcon()) && 
                    buttons[i].getIcon().equals(buttons[i+2].getIcon())) {
                    return true;
                }
            }
            
            // Check columns
            for (int i = 0; i < 3; i++) {
                if (buttons[i].getIcon() != null && 
                    buttons[i].getIcon().equals(buttons[i+3].getIcon()) && 
                    buttons[i].getIcon().equals(buttons[i+6].getIcon())) {
                    return true;
                }
            }
            
            // Check diagonals
            if (buttons[0].getIcon() != null && 
                buttons[0].getIcon().equals(buttons[4].getIcon()) && 
                buttons[0].getIcon().equals(buttons[8].getIcon())) {
                return true;
            }
            
            if (buttons[2].getIcon() != null && 
                buttons[2].getIcon().equals(buttons[4].getIcon()) && 
                buttons[2].getIcon().equals(buttons[6].getIcon())) {
                return true;
            }
            
            return false;
        }

        private boolean isBoardFull() {
            for (JButton button : buttons) {
                if (button.getIcon() == null) {
                    return false;
                }
            }
            return true;
        }
    }

    // Background and Icon Methods
    private static void drawAmongUsBackground(Graphics g, int width, int height) {
        // Space background
        g.setColor(new Color(10, 10, 40));
        g.fillRect(0, 0, width, height);
        
        // Stars
        g.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            int size = (int) (Math.random() * 3) + 1;
            g.fillOval(x, y, size, size);
        }
        
        // Among Us logo-like elements
        g.setColor(new Color(255, 50, 50, 100));
        g.fillOval(width/2 - 200, height/2 - 200, 400, 400);
        
        g.setColor(new Color(0, 255, 0, 50));
        g.fillOval(width/2 - 150, height/2 - 150, 300, 300);
    }

    private static ImageIcon createAmongUsIcon(String colorHex, String type) {
        int size = 80;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        // Draw Among Us character
        g2d.setColor(Color.decode(colorHex));
        g2d.fillOval(5, 5, size-10, size-10);
        
        // Draw visor
        g2d.setColor(new Color(200, 200, 255));
        g2d.fillOval(25, 20, 30, 20);
        
        // Draw backpack
        g2d.setColor(new Color(150, 150, 150));
        g2d.fillRect(10, 30, 15, 25);
        
        g2d.dispose();
        
        // Add a border to distinguish X and O
        if (type.equals("Crewmate")) {
            // Add X mark
            g2d = img.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(20, 20, 60, 60);
            g2d.drawLine(60, 20, 20, 60);
            g2d.dispose();
        } else {
            // Add O mark
            g2d = img.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(20, 20, 40, 40);
            g2d.dispose();
        }
        
        return new ImageIcon(img);
    }
}
