package game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class test extends JFrame implements ActionListener {
    JButton[] buttons;
    JPanel panel;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JLabel player1Score, player2Score;
   
    int[] pairs; // Array to store pairs of faces
    int currentPlayer = 1; // Current player (1 or 2)
    int player1Points = 0, player2Points = 0;
    JButton firstClickedButton = null;
    int firstClickedIndex = -1;
    boolean[] faceMatched; // Array to track whether face matched
    Font font = new Font("Wide Latin",Font.PLAIN,36);	  
    Font font1 = new Font("Snap ITC",Font.ITALIC,20);
    // Immutable array of faces
    final String[] faces = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "S", "X", "U", "y", "D"};
    

/**
 *
 * @author gabsi
 */
    /**
     * Creates new form test
     */
     public test() {
       super("HUNT GAME");
        setSize(720, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize buttons
        buttons = new JButton[32];
        pairs = new int[32];
        faceMatched = new boolean[32];
        for (int i = 0; i < 32; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Wide Latin", Font.PLAIN, 25)); 
            buttons[i].setName("btn" + (i + 1)); // Set button names btn1 to btn32
            buttons[i].addActionListener(this);
        }
        panel = new JPanel(new GridLayout(4, 8));
        panel1 = new JPanel(new BorderLayout());
        panel1.setBackground(new Color(0,151,255));
        panel2 = new JPanel(new BorderLayout());
        panel2.setBackground(new Color(0,151,255));
        panel3 = new JPanel(new GridLayout(8, 4));
        panel1 = new JPanel(new GridLayout(1, 1)); // Initialisation de panel1
       panel2 = new JPanel(new GridLayout(1, 1)); // Initialisation de panel2

      // Changer la taille des panels
       panel1.setPreferredSize(new Dimension(716, 70)); // Changer la hauteur de panel1 ici
       panel2.setPreferredSize(new Dimension(716, 70)); // Changer la hauteur de panel2 ici

        for (JButton button : buttons) {
            panel.add(button);
        }
         
         //setResizable(false);
        
        // Initialize labels for player scores
        player1Score = new JLabel("   ****Player 1: " + player1Points + " points");
        player2Score = new JLabel("   ****Player 2: " + player2Points + " points");
        player1Score.setFont(font1);
        player2Score.setFont(font1);
        
        // Add components to frame
        add(panel, BorderLayout.CENTER);
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.SOUTH);
        
        // Créez un bouton "Back"
        JButton backButton = new JButton("Back");

       // Ajustez la taille du bouton
            backButton.setPreferredSize(new Dimension(4, 2));
       // Ajustez la marge du bouton pour le rendre plus petit
        backButton.setMargin(new Insets(2, 2, 3, 2));

        // Ajoutez le bouton "Back" au panel2
      panel2.add(backButton, BorderLayout.CENTER);

        backButton.setBackground(new Color(169, 109, 157));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Rediriger vers l'interface Game_1
                Game_11 game1 = new Game_11();
                game1.setVisible(true);
                // Fermer l'interface actuelle
                dispose();
            }
        });
        
        JLabel centerLabel1 = new JLabel("6*6", SwingConstants.CENTER);
        centerLabel1.setFont(new Font("Wide Latin", Font.PLAIN, 36));
        centerLabel1.setForeground(Color.WHITE);
        JLabel centerLabel2 = new JLabel("", SwingConstants.CENTER);
        centerLabel2.setFont(new Font("Wide Latin", Font.PLAIN, 24));
        centerLabel2.setForeground(Color.WHITE);
        panel1.add(backButton, BorderLayout.WEST);
        panel1.add(centerLabel1, BorderLayout.CENTER);
        panel1.add(player1Score, BorderLayout.EAST);
        
        panel2.add(backButton, BorderLayout.CENTER);
        panel2.add(player2Score, BorderLayout.EAST);
        
        // Initialize pairs of faces randomly
        initializePairs();
        setVisible(true);
        changeColor();
        
    }
     

    // Function to initialize pairs of faces randomly
    private void initializePairs() {
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            int index;
            
            do {
                index = random.nextInt(32);
            } while (pairs[index] != 0); // Ensure each face appears only once
            pairs[index] = i / 2;
        }
       
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents
     
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
        int faceIndex = pairs[buttonIndex];
        
        clickedButton.setText(faces[faceIndex]);
        
        if (firstClickedButton == null) {
            
            // First button clicked
            firstClickedButton = clickedButton;
            firstClickedIndex = buttonIndex;
        } else {
            // Second button clicked
            // Check if faces match
            
            int firstFaceIndex = pairs[firstClickedIndex];
            
            if (faceIndex == firstFaceIndex) {
                
                // Faces match
                if (currentPlayer == 1) {
                    
                    player1Points += 5;
                } else {
                    
                    player2Points += 5;
                }
                faceMatched[buttonIndex] = true;
                faceMatched[firstClickedIndex] = true;
                updateScores();
                // Reset firstClickedButton
                firstClickedButton = null;
                firstClickedIndex = -1;
                
            } else {
                // Faces don't match
                // Change currentPlayer
                
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
                
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
            changeColor();
        }
      
    }

    public void updateScores() {
        player1Score.setText("   ****Player 1: " + player1Points + " points");
        player2Score.setText("   ****Player 2: " + player2Points + " points");
        
        if (allButtonsClicked()) {
            result();
           
            new test();
        }
    }
    public void changeColor(){
         if(currentPlayer == 1){
             panel1.setBackground(new Color(0,151,255));
             panel2.setBackground(new Color(0,151,255));
         }else{
            panel2.setBackground(new Color(212,211,255));
            panel1.setBackground(new Color(212,211,255));}
     }
    public boolean allButtonsClicked() {
        for (boolean matched : faceMatched) {
            if (!matched) {
                return false;
            }
        }
        return true;
    }

    public void result(){
        if (player1Points > player2Points){
            JOptionPane.showMessageDialog(this,"Player 1 wins");
        }else if(player1Points < player2Points){
            JOptionPane.showMessageDialog(this,"Player 2 wins");
        }else{
            JOptionPane.showMessageDialog(this,"Draw");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new test();
        
        
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
