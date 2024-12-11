import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;

public class LevelSelectionScreen extends JFrame {

    private JPanel panel;
    private JButton easyButton, mediumButton, hardButton, backToMenuButton;
    private Font buttonFont;

    public LevelSelectionScreen() {
        setTitle("Select Difficulty");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setUndecorated(true); // Remove window borders and title bar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false); // Prevent resizing of the window

        getContentPane().setBackground(new Color(92, 70, 232)); // Background color

        buttonFont = new Font("Arial", Font.BOLD, 30); // Set button font

        panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Layout manager for difficulty buttons
        panel.setOpaque(false);

        addLevelButtons(); // Add difficulty buttons
        addBackButton(); // Add back button

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addLevelButtons() {
        easyButton = createButton("Easy");
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDifficulty("Easy");
            }
        });
        addToPanel(easyButton, 0);

        mediumButton = createButton("Medium");
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDifficulty("Medium");
            }
        });
        addToPanel(mediumButton, 1);

        hardButton = createButton("Hard");
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDifficulty("Hard");
            }
        });
        addToPanel(hardButton, 2);
    }

    private void addBackButton() {
        backToMenuButton = createBackButton("Back to Menu");
        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMenu();
            }
        });

        // Use a separate layout for the back button
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20)); // Position at top-left with padding
        backButtonPanel.setOpaque(false); // Make the panel transparent
        backButtonPanel.add(backToMenuButton);

        // Add the back button panel to the top of the window
        add(backButtonPanel, BorderLayout.NORTH);
    }

    private void addToPanel(JButton button, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.insets = new Insets(10, 0, 10, 0);
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

        button.setBorder(createRoundedBorder(30));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(220, 220, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(245, 245, 245));
            }
        });

        button.setContentAreaFilled(false);
        button.setOpaque(true);

        return button;
    }

    private JButton createBackButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 25));
        button.setPreferredSize(new Dimension(180, 50));
        button.setFocusPainted(false);
        button.setBackground(new Color(245, 245, 245));
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);

        button.setBorder(createRoundedBorder(20));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(220, 220, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(245, 245, 245));
            }
        });

        button.setContentAreaFilled(false);
        button.setOpaque(true);

        return button;
    }

    private Border createRoundedBorder(int radius) {
        return new Border() {
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(5, 5, 5, 5);
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
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            }
        };
    }

    private void selectDifficulty(String difficulty) {
        System.out.println("Difficulty selected: " + difficulty);
        dispose();
    }

    private void backToMenu() {
        dispose();
        new MenuScreen();
    }
}
