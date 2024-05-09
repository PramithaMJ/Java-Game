package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class OmiGameLogic {
    private Team team1;
    private Team team2;
    private Deck deck;
    private int roundNumber;
    private int dealerIndex;
    private List<Card> currentTrick;
    private Suit trumps;
    private Player winner;

    private CountDownLatch latch;

    // window
    int windowWidth = 1200;
    int windowHeight = 900;

    int cardWidth = 110; //ratio should 1/1.4
    int cardHeight = 154;

    JFrame frame = new JFrame("Omi Game");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                //draw hidden card
                Image hiddenCardImg = new ImageIcon(getClass().getResource("/cards/BACK.png")).getImage();
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                Image card2 = new ImageIcon(getClass().getResource("/cards/J-R.png")).getImage();
                g.drawImage(card2, cardWidth + 30, 20, cardWidth, cardHeight, null);

            } catch (Exception e) {
                e.printStackTrace(); // Print the exception details for debugging
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hintButton = new JButton("Hit");
    JButton exitButton = new JButton("Exit");


    public JPanel getGamePanel() {
        return gamePanel;
    }


    public OmiGameLogic() {
        initializeGame();
        frame.setVisible(true);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        hintButton.setFocusable(false);
        buttonPanel.add(hintButton);
        exitButton.setFocusable(false);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the frame when "Exit" button is clicked
            }
        });

    }


    private void initializeGame() {
        deck = new Deck();
        deck.shuffle();

        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");

        team1 = new Team("Team A", player1, player2);
        team2 = new Team("Team B", player3, player4);

        dealerIndex = 0; // Assume player1 is the dealer initially
        roundNumber = 1;
        currentTrick = new ArrayList<>();

        JButton playGameButton = new JButton("Play Game");
        playGameButton.setFocusable(false);
        playGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playGame(); // Call playGame() when the button is clicked
            }
        });
        buttonPanel.add(playGameButton);
        System.out.println("playGame.");

    }

    private void dealCards() {
        List<Card> cards = deck.getCards();
        int startIndex = dealerIndex == 0 ? 0 : 1; // Skip the dealer for cutting
        int index = startIndex;

        for (int i = 0; i < 4; i++) {
            team1.getPlayer1().addToDeck(cards.get(index++));
            team1.getPlayer2().addToDeck(cards.get(index++));
            team2.getPlayer1().addToDeck(cards.get(index++));
            team2.getPlayer2().addToDeck(cards.get(index++));
        }

        dealerIndex = (dealerIndex + 1) % 4;
        System.out.println("Dealer: " + getCurrentDealer().getName());
        System.out.println("Deck cut by: " + getCurrentLeftPlayer().getName());
        System.out.println("Cards dealt.");
    }

    private void nameTrumps() {
        //  Scanner scanner = new Scanner(System.in);

        getCurrentRightPlayer().printHand();

        //==================================================
         renderHand(getCurrentRightPlayer().getHand());
        System.out.println("Player to the right of the dealer, please name trumps (CLUBS, DIAMONDS, HEARTS, SPADES):");

        // Create GUI For console  log CLUBS, DIAMONDS, HEARTS, SPADES
        nameTrumpsGUI();


    }

    private void nameTrumpsGUI() {
        // Create a JFrame to hold the buttons
        String[] options = {"Clubs", "Diamonds", "Hearts", "Spades"};

        // Show the option dialog
        int choice = JOptionPane.showOptionDialog(null, "Select Trump Suit", "Trump Suit Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        // Check the selected option and set the trump suit accordingly
        switch (choice) {
            case 0:
                trumps = Suit.C;
                break;
            case 1:
                trumps = Suit.D;
                break;
            case 2:
                trumps = Suit.H;
                break;
            case 3:
                trumps = Suit.S;
                break;
            default:
                // If the user closes the dialog without selecting an option, handle it here
                System.out.println("No suit selected");
                // You might want to handle this case differently based on your requirements
                break;
        }

        // Print the selected trump suit to the console
        if (choice >= 0 && choice < options.length) {
            System.out.println("Trumps named: " + options[choice]);
        }
    }

    private Player getCurrentDealer() {
        switch (dealerIndex) {
            case 0:
                return team1.getPlayer1();
            case 1:
                return team1.getPlayer2();
            case 2:
                return team2.getPlayer1();
            default:
                return team2.getPlayer2();
        }
    }

    private Player getCurrentLeftPlayer() {
        int leftIndex = (dealerIndex + 1) % 4;
        switch (leftIndex) {
            case 0:
                return team1.getPlayer1();
            case 1:
                return team1.getPlayer2();
            case 2:
                return team2.getPlayer1();
            default:
                return team2.getPlayer2();
        }
    }

    private Player getCurrentRightPlayer() {
        int rightIndex = (dealerIndex - 1) % 4;
        switch (rightIndex) {
            case 0:
                return team1.getPlayer1();
            case 1:
                return team1.getPlayer2();
            case 2:
                return team2.getPlayer1();
            default:
                return team2.getPlayer2();
        }
    }

    private boolean isValidPlay(Player player, Card card) {
        if (currentTrick.isEmpty()) {
            return true; // First card in the trick, any card is valid
        }

        Suit leadSuit = currentTrick.get(0).getSuit();
        if (card.getSuit() == leadSuit) {
            return true; // Following suit
        }

        // Not following suit, check if the player has the lead suit
        for (Card c : player.getHand()) {
            if (c.getSuit() == leadSuit) {
                return false; // Player has a card of the lead suit, must play that card
            }
        }

        return true; // Player doesn't have the lead suit, any card is valid
    }

    private void playTrick() {
        currentTrick.clear(); // Clear the current trick
        Player currentPlayer;
        if (roundNumber == 1) {
            currentPlayer = getCurrentRightPlayer(); // Start with the player to the right of the dealer
        } else {
            currentPlayer = winner;
        }

        while (currentTrick.size() < 4) {
            System.out.println(currentPlayer.getName() + "'s turn.");
            System.out.println("Your hand:");
            currentPlayer.getHand().forEach(System.out::println);

            Card cardPlayed = currentPlayer.playCard();
            if (!isValidPlay(currentPlayer, cardPlayed)) {
                System.out.println("Invalid play. Please follow suit if possible.");
                continue;
            }

            currentTrick.add(cardPlayed);
            System.out.println(currentPlayer.getName() + " played: " + cardPlayed);
            currentPlayer = getNextPlayer(currentPlayer);
        }

        determineTrickWinner();
        removePlayedCards();
    }

    private Player getNextPlayer(Player currentPlayer) {
        if (currentPlayer == team1.getPlayer1()) {
            return team1.getPlayer2();
        } else if (currentPlayer == team1.getPlayer2()) {
            return team2.getPlayer1();
        } else if (currentPlayer == team2.getPlayer1()) {
            return team2.getPlayer2();
        } else {
            return team1.getPlayer1();
        }
    }

    private void determineTrickWinner() {
        Suit leadSuit = currentTrick.get(0).getSuit();
        Card highestTrump = null;
        Card highestOfLeadSuit = null;

        for (Card card : currentTrick) {
            if (card.getSuit() == trumps) {
                if (highestTrump == null || card.getRank().ordinal() > highestTrump.getRank().ordinal()) {
                    highestTrump = card;
                }
            } else if (card.getSuit() == leadSuit) {
                if (highestOfLeadSuit == null || card.getRank().ordinal() > highestOfLeadSuit.getRank().ordinal()) {
                    highestOfLeadSuit = card;
                }
            }
        }

        Player trickWinner = null;
        if (highestTrump != null) {
            trickWinner = getPlayerByCard(highestTrump);
        } else if (highestOfLeadSuit != null) {
            trickWinner = getPlayerByCard(highestOfLeadSuit);
        }

        if (trickWinner != null) {
            System.out.println("Trick won by " + trickWinner.getName());
            winner = trickWinner;
        } else {
            System.out.println("Trick tied. No winner.");
        }

    }

    private void removePlayedCards() {
        for (Card card : currentTrick) {
            team1.getPlayer1().removeFromDeck(card);
            team1.getPlayer2().removeFromDeck(card);
            team2.getPlayer1().removeFromDeck(card);
            team2.getPlayer2().removeFromDeck(card);
        }
        // Clear the current trick for the next round
        currentTrick.clear();
    }


    private Player getPlayerByCard(Card card) {
        if (team1.getPlayer1().hasCard(card)) {
            return team1.getPlayer1();
        } else if (team1.getPlayer2().hasCard(card)) {
            return team1.getPlayer2();
        } else if (team2.getPlayer1().hasCard(card)) {
            return team2.getPlayer1();
        } else if (team2.getPlayer2().hasCard(card)) {
            return team2.getPlayer2();
        }

        return null; // Card not found in any player's hand
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to OMI Game!");

        while (roundNumber <= 10) {  // Play 10 rounds
            System.out.println("\nRound " + roundNumber);

            if (roundNumber == 1) {
                deck.shuffle();
                dealCards();
                nameTrumps();
            }

            System.out.println("Press enter to play round " + roundNumber);
            playTrick();

            roundNumber++;
        }
    }


    // New method to process input
    public void processInput(String input) {
        playGame();
        if (input.equals("playGame")) {
            playGame();
        } else {
            System.out.println("Invalid input. Please enter 'playGame' to start the game.");
        }
    }
    
    private void renderHand(List<Card> hand) {

        // Clear the gamePanel before rendering
        gamePanel.removeAll();

        // Calculate spacing for card positioning
        int startX = 400;
        int startY = 620;
        int paddingX = 10;

        // Render Player Name
        String playerName = "Player One";
        Font playerNameFont = new Font("Arial", Font.BOLD, 20);
        FontMetrics fontMetrics = gamePanel.getFontMetrics(playerNameFont);
        int playerNameWidth = fontMetrics.stringWidth(playerName);
        int playerNameX = (gamePanel.getWidth() - playerNameWidth) / 2; // Center horizontally
        int playerNameY = startY - 30; // 30 pixels above the first card

        gamePanel.getGraphics().setFont(playerNameFont);
        gamePanel.getGraphics().drawString(playerName, playerNameX, playerNameY);

        // Render each card in the hand
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            String imagePath = card.getImagePath(); // Assuming you have image paths for cards

            // Load the card image and draw it on the gamePanel
            try {
                Image cardImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
                int x = startX + (i * (cardWidth + paddingX));
                int y = startY;
                gamePanel.getGraphics().drawImage(cardImage, x, y, cardWidth, cardHeight, null);

                // Add mouse listener to each card image
                final int index = i; // To make index effectively final
                gamePanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Check if the mouse click is within the bounds of the current card
                        if (e.getX() >= x && e.getX() <= x + cardWidth && e.getY() >= y && e.getY() <= y + cardHeight) {
                            // Handle the click event for this card
                            handleCardClick(hand.get(index));
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace(); // Handle image loading errors
            }
        }
    }
    private void handleCardClick(Card clickedCard) {
        // Log the rank and suit of the clicked card to the console
        System.out.println("Clicked Card: " + clickedCard.getRank() + " of " + clickedCard.getSuit());
    }

}