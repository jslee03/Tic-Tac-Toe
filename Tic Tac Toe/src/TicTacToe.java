import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class TicTacToe implements ActionListener {
    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel textField = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1Turn;

    /**
     * Constructs a new Tic Tac Toe game board.
     */
    TicTacToe() {
        // create JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // text settings
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setFont(new Font("Arial", Font.BOLD, 75));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic-Tac-Toe");
        textField.setOpaque(true);

        // title panel
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        // button panel
        buttonPanel.setLayout(new GridLayout(3, 3));

        // create buttons
        for (int i=0; i<9; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);

            buttons[i].setBackground(Color.WHITE);
            buttons[i].setOpaque(true);
            buttons[i].setBorder(getCustomBorder());
        }

        titlePanel.add(textField);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);

        // assign the first turn, either X or O
        firstTurn();
    }

    /**
     * Creates a custom border to go around each button.
     */
    public Border getCustomBorder() {
        int borderThickness = 3;
        Color borderColor = Color.DARK_GRAY;
        return BorderFactory.createCompoundBorder(
                new LineBorder(borderColor, borderThickness),
                BorderFactory.createEmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness)
        );
    }

    /**
     * Checks the status of each player's turn.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i=0; i<9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1Turn) {
                    // only assign text for X if button is blank
                    if (buttons[i].getText() == "") {
                        buttons[i].setForeground(Color.RED);
                        buttons[i].setText("X");
                        player1Turn = false;
                        textField.setText("O turn");
                        checkWin();
                    }
                } else {
                    // only assign text for O if button is blank
                    if (buttons[i].getText() == "") {
                        buttons[i].setForeground(Color.BLUE);
                        buttons[i].setText("O");
                        player1Turn = true;
                        textField.setText("X turn");
                        checkWin();
                    }
                }
            }
        }
    }

    /**
     * Randomly chooses the first turn, either player X or O.
     */
    public void firstTurn() {
        // disable buttons until a turn is chosen
        for (int i=0; i<9; i++) {
            buttons[i].setEnabled(false);
        }

        // display title for 2000ms before choosing a turn
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // randomly choose a turn
        if (random.nextInt(2) == 0) {
            player1Turn = true;
            textField.setText("X turn");
        } else {
            player1Turn = false;
            textField.setText("O turn");
        }

        for (int i=0; i<9; i++) {
            buttons[i].setEnabled(true);
        }
    }

    /**
     * Checks all winning conditions of each X and O.
     */
    public void checkWin() {
        // vertical check
        for (int i=0; i<3; i++) {
            if (buttons[i].getText().equals(buttons[i+3].getText()) &&
                    buttons[i+3].getText().equals(buttons[i+6].getText()) && !buttons[i].getText().isEmpty()) {
                if (buttons[i].getText().equals("X")) {
                    xWins(i, i+3, i+6);
                } else {
                    oWins(i, i+3, i+6);
                }
            }
        }

        // horizontal check
        for (int i=0; i<=6; i+=3) {
            if (buttons[i].getText().equals(buttons[i+1].getText()) &&
                    buttons[i+1].getText().equals(buttons[i+2].getText()) && !buttons[i].getText().isEmpty()) {
                if (buttons[i].getText().equals("X")) {
                    xWins(i, i+1, i+2);
                } else {
                    oWins(i, i+1, i+2);
                }
            }
        }

        // diagonal check (top left to bottom right)
        if (buttons[0].getText().equals(buttons[4].getText()) &&
                buttons[4].getText().equals(buttons[8].getText()) && !buttons[0].getText().isEmpty()) {
            if (buttons[0].getText().equals("X")) {
                xWins(0, 4, 8);
            } else {
                oWins(0, 4, 8);
            }
        }

        // diagonal check (top right to bottom left)
        if (buttons[2].getText().equals(buttons[4].getText()) &&
                buttons[4].getText().equals(buttons[6].getText()) && !buttons[2].getText().isEmpty()) {
            if (buttons[2].getText().equals("X")) {
                xWins(2, 4, 6);
            } else {
                oWins(2, 4, 6);
            }
        }

        checkTie();
    }

    public void checkTie() {
        for (int i=0; i<9; i++) {
            // if an empty button exists, there cannot be a tie
            if (buttons[i].getText().isEmpty()) {
                break;
            }
            // if all the buttons are filled without a winner, the game is tied
            if (i == 8) {
                // disables all buttons to prevent further play
                for (int j=0; j<9; j++) {
                    buttons[j].setEnabled(false);
                }
                textField.setText("Tie!");
                replayPanel();
            }
        }
    }

    /**
     * Changes winning combination colours of X to green.
     * Ends the game.
     */
    public void xWins(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        // disables all buttons to prevent further play
        for (int i=0; i<9; i++) {
            buttons[i].setEnabled(false);
        }

        textField.setText("X wins!");
        replayPanel();
    }

    /**
     * Changes winning combination colours of O to green.
     * Ends the game.
     */
    public void oWins(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        // disables all buttons to prevent further play
        for (int i=0; i<9; i++) {
            buttons[i].setEnabled(false);
        }

        textField.setText("O wins!");
        replayPanel();
    }

    /**
     * Method that displays a pop-up message.
     * Asks if user would like to replay the game.
     */
    public void replayPanel() {
        // display message
        int option = JOptionPane.showOptionDialog(frame, "Replay?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Yes", "Exit"},
                "Yes");

        if (option == JOptionPane.YES_OPTION) {
            restartGame();
        } else if (option == JOptionPane.CLOSED_OPTION || option == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Restarts the game.
     */
    public void restartGame() {
        // clear game board
        for (int i=0; i<9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(Color.WHITE);
        }

        firstTurn();
    }
}
