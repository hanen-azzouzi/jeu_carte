package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class test211 extends JFrame implements ActionListener {
    JButton[] buttons;
    JPanel panel;
    JLabel player1Score;
    int currentPlayer = 1; // Current player (1 or 2)
    int player1Points = 0;
    JButton firstClickedButton = null;
    int firstClickedIndex = -1;
    boolean[] faceMatched; // Array to track whether face matched
    Timer timer;
    JLabel timerLabel;
    int timeLeft = 60; // 60 seconds
    int totalButtons = 16; // Total number of buttons
    final String[] faces = {"5","J","3","1","K","10","Q","JS", "UX"};

    Font font = new Font("Wide Latin", Font.PLAIN, 36);
    Font font1 = new Font("Snap ITC", Font.ITALIC, 20);

    JPanel panel1;
    JPanel panel2;

    public test211() {
        super("HUNT GAME");
        setSize(720, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel1 = new JPanel(new GridLayout(4, 8));
        panel1.setBackground(new Color(0,151,255));
        panel2 = new JPanel(new BorderLayout());
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.SOUTH);
        panel1.setPreferredSize(new Dimension(716, 70)); // Changer la hauteur de panel1 ici
       panel2.setPreferredSize(new Dimension(716, 70));

        JLabel label = new JLabel("4*4", SwingConstants.CENTER);
        label.setFont(new Font("Wide Latin", Font.PLAIN, 36));
        label.setForeground(Color.WHITE);
        GridLayout layout = new GridLayout(1, 1);
        layout.setVgap((panel1.getHeight() - label.getHeight()) / 2);
        layout.setHgap((panel1.getWidth() - label.getWidth()) / 2);
        panel1.setLayout(layout);
        panel1.add(label);

        buttons = new JButton[totalButtons];
        faceMatched = new boolean[totalButtons];
        for (int i = 0; i < totalButtons; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(font);
            buttons[i].setName("btn" + (i + 1));
            buttons[i].addActionListener(this);
        }

        panel = new JPanel(new GridLayout(4, 8));
        java.util.List<String> facesList = new java.util.ArrayList<>(java.util.Arrays.asList(faces));
        java.util.Collections.shuffle(facesList);
        facesList.toArray(faces);

        for (JButton button : buttons) {
            panel.add(button);
        }

        player1Score = new JLabel("Player 1: " + player1Points + " points");
        player1Score.setFont(font1);
        panel1.add(player1Score, BorderLayout.NORTH);

        add(panel, BorderLayout.CENTER);

        timerLabel = new JLabel("Time Left: " + timeLeft + " seconds");
        timerLabel.setFont(font1);
        panel2.add(timerLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(90, 45));
        backButton.setBackground(new Color(169, 109, 157));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Rediriger vers l'interface Game_1
                Game_1 game1 = new Game_1();
                game1.setVisible(true);
                // Fermer l'interface actuelle
                dispose();
            }
        });
        panel1.add(backButton, BorderLayout.NORTH);

        setVisible(true);

        startTimer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String buttonName = clickedButton.getName();
        int buttonIndex = Integer.parseInt(buttonName.substring(3)) - 1;

        if (faceMatched[buttonIndex] || clickedButton == firstClickedButton) {
            return;
        }

        int faceIndex = buttonIndex / 2;
        clickedButton.setText(faces[faceIndex]);

        if (firstClickedButton == null) {
            firstClickedButton = clickedButton;
            firstClickedIndex = buttonIndex;
        } else {
            int firstFaceIndex = firstClickedIndex / 2;
            if (faceIndex == firstFaceIndex) {
                player1Points += 5;
                faceMatched[buttonIndex] = true;
                faceMatched[firstClickedIndex] = true;
                updateScore();
                firstClickedButton = null;
                firstClickedIndex = -1;
            } else {
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clickedButton.setText("");
                        firstClickedButton.setText("");
                        firstClickedButton = null;
                        firstClickedIndex = -1;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            
        }

        boolean allMatched = true;
        for (boolean matched : faceMatched) {
            if (!matched) {
                allMatched = false;
                break;
            }
        }
        if (allMatched) {
            timer.stop();
            result();
        }
    }

    public void updateScore() {
        player1Score.setText("Score: " + player1Points);
    }

    public void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("**Time Left: " + timeLeft + " seconds");
                if (timeLeft <= 0) {
                    timer.stop();
                    result();
                }
            }
        });
        timer.start();
    }

    public void result() {
        JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + player1Points + " with timer " + (60 - timeLeft) + " seconds");
    }

    public static void main(String[] args) {
        new test211();
    }
}
