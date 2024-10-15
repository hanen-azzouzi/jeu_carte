/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package game;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class test2 extends JFrame implements ActionListener {
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
    int totalButtons = 32; // Total number of buttons
    // Immutable array of faces
    final String[] faces = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "J", "U", "Y", "P", "B"};

    Font font = new Font("Wide Latin", Font.PLAIN, 25);
    Font font1 = new Font("Snap ITC", Font.ITALIC, 15);

    JPanel panel1;
    JPanel panel2;

    public test2 () {
        super("HUNT GAME");
        setSize(716, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ajout des panels
        panel1 = new JPanel(new GridLayout(4, 8));
        panel1.setBackground(new Color(0,151,255));
        panel2 = new JPanel(new BorderLayout()); // Utilisation de BorderLayout pour panel2
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.SOUTH);
        panel1.setPreferredSize(new Dimension(716, 70)); // Changer la hauteur de panel1 ici
       panel2.setPreferredSize(new Dimension(716, 70));
      // Créer une étiquette avec le texte souhaité
   JLabel label = new JLabel("6*6", SwingConstants.CENTER); // Aligner le texte au centre
// Définir la police Wide Latin
   label.setFont(new Font("Wide Latin", Font.PLAIN, 36));
      label.setForeground(Color.WHITE);
GridLayout layout = new GridLayout(1, 1); // Une seule cellule pour l'étiquette
layout.setVgap((panel1.getHeight() - label.getHeight()) / 2); // Espace vertical pour centrer
layout.setHgap((panel1.getWidth() - label.getWidth()) / 2); // Espace horizontal pour centrer
panel1.setLayout(layout);

// Ajouter l'étiquette au panel1
      panel1.add(label);
        // Initialize buttons
        buttons = new JButton[totalButtons];
        faceMatched = new boolean[totalButtons];
        for (int i = 0; i < totalButtons; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(font);
            buttons[i].setName("btn" + (i + 1)); // Set button names btn1 to btn16
            buttons[i].addActionListener(this);
        }
        
        panel = new JPanel(new GridLayout(4, 8));
        // Mélanger les faces de manière aléatoire
      // Mélanger les faces de manière aléatoire
    java.util.List<String> facesList = new java.util.ArrayList<>(java.util.Arrays.asList(faces));
   java.util.Collections.shuffle(facesList);
    facesList.toArray(faces);

        for (JButton button : buttons) {
            panel.add(button);
        }

        // Initialize labels for player scores
        player1Score = new JLabel("Player 1: " + player1Points + " points");
        player1Score.setFont(font1);
        // Add labels to panels
        panel1.add(player1Score, BorderLayout.NORTH);

        // Add components to frame
        add(panel, BorderLayout.CENTER);

        // Timer label
        timerLabel = new JLabel("Time Left: " + timeLeft + " seconds");
        timerLabel.setFont(font1);
        panel2.add(timerLabel, BorderLayout.CENTER); // Ajout du timerLabel au centre de panel2

        // Créer un nouveau bouton pour "back"
        // Créer un nouveau bouton pour "back"
            JButton backButton = new JButton("Back");

        // Définir la taille du bouton
            backButton.setPreferredSize(new Dimension(90, 45));

         // Définir la couleur du bouton
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
        panel1.add(backButton, BorderLayout.NORTH); // Ajout du bouton "back" en haut de panel2

        setVisible(true);

        // Start the timer
        startTimer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String buttonName = clickedButton.getName();
        int buttonIndex = Integer.parseInt(buttonName.substring(3)) - 1; // Extract button index

        // Check if button already matched or same button clicked twice
        if (faceMatched[buttonIndex] || clickedButton == firstClickedButton) {
            return;
        }

        // Set face for clicked button (you can set it as per your game logic)
        int faceIndex = buttonIndex / 2;
        clickedButton.setText(faces[faceIndex]);

        if (firstClickedButton == null) {
            // First button clicked
            firstClickedButton = clickedButton;
            firstClickedIndex = buttonIndex;
        } else {
            // Second button clicked
            // Check if faces match
            int firstFaceIndex = firstClickedIndex / 2;
            if (faceIndex == firstFaceIndex) {
                // Faces match
                player1Points += 5;
                faceMatched[buttonIndex] = true;
                faceMatched[firstClickedIndex] = true;
                updateScore();
                // Reset firstClickedButton
                firstClickedButton = null;
                firstClickedIndex = -1;
            } else {
                // Faces don't match
                // Reset faces
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clickedButton.setText("");
                        firstClickedButton.setText("");
                        // Reset firstClickedButton
                        firstClickedButton = null;
                        firstClickedIndex = -1;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }

        // Check if all buttons are matched
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
        new test2();
    }
}