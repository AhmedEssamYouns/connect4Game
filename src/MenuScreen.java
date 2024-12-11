import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;

public class MenuScreen extends JFrame {

    private JPanel panel;
    private JButton  quitButton, vsFriendButton, vsComputerButton;
    private Font buttonFont;

    public MenuScreen() {
        // Set title and full screen size
        setTitle("Connect 4 - Menu");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Make the window full-screen
        setUndecorated(true); // Remove window borders and title bar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false); // Disable resizing the window

        // Set background color
        getContentPane().setBackground(new Color(92, 70, 232));

        // Set a custom font for the buttons
        buttonFont = new Font("Arial", Font.BOLD, 30);

        // Use GridBagLayout to center buttons
        panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Layout manager for flexible placement
        panel.setOpaque(false); // Makes the panel transparent

        // Add buttons to the panel
        addMenuButtons();

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);

        // Set the frame to visible
        setVisible(true);
    }

    private void addMenuButtons() {
        // Create "Play vs Friend" Button
        vsFriendButton = createButton("Play vs Friend");
        vsFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playVsFriend();
            }
        });
        addToPanel(vsFriendButton, 0);

        // Create "Play vs Computer" Button
        vsComputerButton = createButton("Play vs Computer");
        vsComputerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playVsComputer();
            }
        });
        addToPanel(vsComputerButton, 1);

        // Create "Quit" Button
        quitButton = createButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitGame();
            }
        });
        addToPanel(quitButton, 2);
    }

    private void addToPanel(JButton button, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Center horizontally
        gbc.gridy = gridy; // Stack vertically (0 for first, 1 for second, etc.)
        gbc.insets = new Insets(10, 0, 10, 0); // Add vertical spacing between buttons
        panel.add(button, gbc);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setPreferredSize(new Dimension(300, 80)); // Set all buttons to same width and height
        button.setFocusPainted(false);
        button.setBackground(new Color(245, 245, 245)); // Off-white color
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);

        // Make the button rounded using custom Border with rounded corners
        button.setBorder(createRoundedBorder(30)); // Adjust the radius for roundness

        // Add hover effect for button color change
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(220, 220, 220)); // Slightly darker on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(245, 245, 245)); // Original color when not hovered
            }
        });

        // Make the button rounded (by custom border)
        button.setContentAreaFilled(false); // Remove default background fill
        button.setOpaque(true); // Make it opaque

        return button;
    }

    // Custom method to create a rounded border for the button
    private Border createRoundedBorder(int radius) {
        return new Border() {
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(5, 5, 5, 5); // Set padding for the rounded corners
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(3)); // Border thickness
                g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius); // Draw rounded rectangle
            }
        };
    }

    private void playVsFriend() {
        new Connect4(); // Start a new game with a friend
        dispose(); // Close the menu window
    }

    private void playVsComputer() {
        // Open a new window to select the difficulty level
        new LevelSelectionScreen();
        dispose(); // Close the menu window
    }

    private void quitGame() {
        System.exit(0); // Exit the program
    }

    public static void main(String[] args) {
        new MenuScreen(); // Show the menu screen
    }
}
