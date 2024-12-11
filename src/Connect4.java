import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings("unused")
class Connect4 extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    private static final int WIDTH, HEIGHT, widthUnit, heightUnit, boardLength, boardHeight;
    private static JFrame frame;
    private static Connect4 instance;
    private static Point p1, p2;
    private JPanel buttonPanel;
    private JButton restartButton, menuButton;

    public static boolean gameInProgress = false; // Flag to track if the game is in progress

    public static void main(String[] args) {
        instance = new Connect4();
    }

    public Connect4() {
        gameInProgress = true; // Game has started
        setLayout(new BorderLayout());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(buttonPanel, BorderLayout.NORTH);

        menuButton = new JButton("Menu");
        menuButton.addActionListener(e -> openMenu());
        buttonPanel.add(menuButton);

        restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame());
        buttonPanel.add(restartButton);

        frame = new JFrame("Connect 4");
        frame.setBounds(50, 50, WIDTH, HEIGHT);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        javax.swing.Timer timer = new javax.swing.Timer(10, this);
        timer.start();

        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
    }

    static {
        int initialWidth = 1800;
        int initialHeight = 1000;
        boardLength = 6;
        boardHeight = 5;
        widthUnit = initialWidth / (boardLength + 2);
        WIDTH = widthUnit * (boardLength + 2);
        heightUnit = initialHeight / (boardHeight + 2);
        HEIGHT = heightUnit * (boardHeight + 2);
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Board.draw(g);
    }

    public void mouseMoved(MouseEvent e) {
        Board.hover(e.getX());
    }

    public void mousePressed(MouseEvent e) {
        Board.drop();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    private void restartGame() {
        Board.reset();
        p1 = p2 = null;
        frame.repaint();
        frame.addMouseListener(this); // Re-add mouse listener after restart
        frame.addMouseMotionListener(this); // Re-add motion listener after restart
    }

    private void openMenu() {
        gameInProgress = false; // Set the flag to false when going to the menu
        frame.dispose(); // Close the current game window
        new MenuScreen(); // Open the menu screen
    }

    static class PointPair {
        public Point p1, p2;

        PointPair(int x1, int y1, int x2, int y2) {
            p1 = new Point(x1, y1);
            p2 = new Point(x2, y2);
        }
    }

    static class Board {
        static Color[][] board;
        static Color[] players;
        static int turn;
        static int hoverX, hoverY;
        static boolean gameDone;

        static {
            board = new Color[boardLength][boardHeight];
            for (int i = 0; i < boardLength; i++) {
                for (int j = 0; j < boardHeight; j++) {
                    board[i][j] = Color.WHITE;
                }
            }
            players = new Color[] { Color.YELLOW, Color.RED };
            turn = 0;
        }

        public static void draw(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(92, 70, 232));
            g2.fillRect(0, 0, WIDTH, HEIGHT);

            int bottomPadding = 1;

            int shadowOffset = 10; 
            int arcWidth = 30;
            int arcHeight = 30; 
            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRoundRect(
                    widthUnit + shadowOffset,
                    heightUnit + shadowOffset,
                    WIDTH - 2 * widthUnit - 2 * shadowOffset,
                    HEIGHT - 2 * heightUnit - bottomPadding - 2 * shadowOffset,
                    arcWidth,
                    arcHeight);

            g2.setColor(new Color(245, 245, 245));
            g2.fillRoundRect(
                    widthUnit,
                    heightUnit,
                    WIDTH - 2 * widthUnit,
                    HEIGHT - 2 * heightUnit - bottomPadding,
                    arcWidth,
                    arcHeight);

            g2.setColor(new Color(0, 0, 0));
            int bottomBorderHeight = 10;
            g2.fillRoundRect(
                    widthUnit,
                    HEIGHT,
                    WIDTH - 2 * widthUnit,
                    bottomBorderHeight,
                    arcWidth,
                    arcHeight);

            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRoundRect(
                    widthUnit + shadowOffset,
                    HEIGHT - heightUnit - bottomPadding + shadowOffset,
                    WIDTH - 2 * widthUnit,
                    bottomBorderHeight,
                    arcWidth,
                    arcHeight);

            for (int i = widthUnit; i <= WIDTH - widthUnit; i += widthUnit) {
                if (i == WIDTH - widthUnit)
                    continue;

                for (int j = heightUnit; j < HEIGHT - heightUnit - bottomPadding; j += heightUnit) {
                    Color circleColor = board[i / widthUnit - 1][j / heightUnit - 1];

                    if (circleColor == Color.white) {
                        circleColor = new Color(92, 70, 232);
                    }

                    g2.setColor(circleColor);

                    int circleWidth = widthUnit - 50;
                    int circleHeight = heightUnit - 50;
                    int offsetX = (widthUnit - circleWidth) / 2;
                    int offsetY = (heightUnit - circleHeight) / 2;

                    g2.setColor(new Color(0, 0, 0, 80));
                    g2.fillOval(i + offsetX + 5, j + offsetY + 5, circleWidth, circleHeight);

                    g2.setColor(circleColor);
                    g2.fillOval(i + offsetX, j + offsetY, circleWidth, circleHeight);

                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawOval(i + offsetX, j + offsetY, circleWidth, circleHeight);
                }
            }

            g2.setColor(gameDone ? Color.GREEN : players[turn]);
            int hoverCircleWidth = widthUnit - 50;
            int hoverCircleHeight = heightUnit - 50;
            int hoverOffsetX = (widthUnit - hoverCircleWidth) / 2;
            int hoverOffsetY = (heightUnit - hoverCircleHeight) / 2;

            for (int glow = 10; glow > 0; glow--) {
                g2.setColor(new Color(players[turn].getRed(), players[turn].getGreen(), players[turn].getBlue(),
                        20 * glow));
                g2.drawOval(
                        hoverX + hoverOffsetX - glow,
                        hoverY + hoverOffsetY - glow,
                        hoverCircleWidth + glow * 2,
                        hoverCircleHeight + glow * 2);
            }

            g2.fillOval(hoverX + hoverOffsetX, hoverY + hoverOffsetY, hoverCircleWidth, hoverCircleHeight);
            g2.setColor(Color.ORANGE);
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawOval(hoverX + hoverOffsetX, hoverY + hoverOffsetY, hoverCircleWidth, hoverCircleHeight);

            g2.setColor(Color.BLACK);
            if (p1 != null && p2 != null) {
                g2.setStroke(new BasicStroke(10.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        public static void hover(int x) {
            x -= x % widthUnit;
            if (x < widthUnit)
                x = widthUnit;
            if (x >= WIDTH - widthUnit)
                x = WIDTH - 2 * widthUnit;
            hoverX = x;
            hoverY = 0;
        }

        @SuppressWarnings("static-access")
        public static void drop() {
            if (board[hoverX / widthUnit - 1][0] != Color.WHITE)
                return;
            new Thread(() -> {
                Color color = players[turn];
                int x = hoverX;
                int i;
                for (i = 0; i < board[x / widthUnit - 1].length && board[x / widthUnit - 1][i] == Color.WHITE; i++) {
                    board[x / widthUnit - 1][i] = color;
                    try {
                        Thread.currentThread().sleep(200);
                    } catch (Exception ignored) {
                    }
                    board[x / widthUnit - 1][i] = Color.WHITE;
                    if (gameDone)
                        return;
                }
                if (gameDone)
                    return;
                board[x / widthUnit - 1][i - 1] = color;
                checkConnect(x / widthUnit - 1, i - 1);
                SoundGenerator.generateDropSound(); 
            }).start();
            try {
                Thread.currentThread().sleep(100);
            } catch (Exception ignored) {
            }
            if (gameDone)
                return;
            turn = (turn + 1) % players.length;
        }

        public static void checkConnect(int x, int y) {
            if (gameDone)
                return;

            PointPair pair = search(board, x, y);

            if (pair != null) {
                p1 = new Point((pair.p1.x + 1) * widthUnit + widthUnit / 2,
                        (pair.p1.y + 1) * heightUnit + heightUnit / 2);
                p2 = new Point((pair.p2.x + 1) * widthUnit + widthUnit / 2,
                        (pair.p2.y + 1) * heightUnit + heightUnit / 2);
                frame.removeMouseListener(instance);
                gameDone = true;
            }
        }

        public static PointPair search(Color[][] arr, int i, int j) {
            Color color = arr[i][j];
            int left, right, up, down;

            left = right = i;
            while (left >= 0 && arr[left][j] == color)
                left--;
            left++;
            while (right < arr.length && arr[right][j] == color)
                right++;
            right--;
            if (right - left >= 3) {
                return new PointPair(left, j, right, j);
            }

            down = j;
            while (down < arr[i].length && arr[i][down] == color)
                down++;
            down--;
            if (down - j >= 3) {
                return new PointPair(i, j, i, down);
            }

            left = right = i;
            up = down = j;
            while (left >= 0 && up >= 0 && arr[left][up] == color) {
                left--;
                up--;
            }
            left++;
            up++;
            while (right < arr.length && down < arr[right].length && arr[right][down] == color) {
                right++;
                down++;
            }
            right--;
            down--;
            if (right - left >= 3 && down - up >= 3) {
                return new PointPair(left, up, right, down);
            }

            left = right = i;
            up = down = j;
            while (left >= 0 && down < arr[left].length && arr[left][down] == color) {
                left--;
                down++;
            }
            left++;
            down--;
            while (right < arr.length && up >= 0 && arr[right][up] == color) {
                right++;
                up--;
            }
            right--;
            up++;
            if (right - left >= 3 && down - up >= 3) {
                return new PointPair(left, down, right, up);
            }

            return null;
        }

        public static void reset() {
            for (int i = 0; i < boardLength; i++) {
                for (int j = 0; j < boardHeight; j++) {
                    board[i][j] = Color.WHITE;
                }
            }
            p1 = p2 = null;
            gameDone = false;
            turn = 0;
        }
    }

}
