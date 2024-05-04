package org.pmj.OmiGame;


import java.util.ArrayList;
import java.util.List;

public class OmiGame {
    private Deck deck;
    private List<Player> players;
    private int dealerIndex;
    private int currentPlayerIndex;
    private Suit trumpSuit;
    private List<Trick> tricks;

    public OmiGame() {
        deck = new Deck();
        players = new ArrayList<>();
        tricks = new ArrayList<>();
        dealerIndex = 0; // Start with the first player as dealer
        currentPlayerIndex = dealerIndex;
        // Initialize players (North, East, South, West)
        for (int i = 0; i < 4; i++) {
            players.add(new Player("Player " + i));
        }
    }

    public void start() {
        deck.shuffle();
        dealCards();
        determineTrumpSuit();
        play();
    }

    private void dealCards() {
        for (int i = 0; i < 8; i++) {
            for (Player player : players) {
                player.addCard(deck.drawCard());
            }
        }
    }

    private void determineTrumpSuit() {
        // Implement logic to determine trump suit
        // For example, let's assume the dealer chooses the trump suit
        Player dealer = players.get(dealerIndex);
        Card trumpCard = dealer.chooseTrump();
        trumpSuit = trumpCard.getSuit();
    }

    private void play() {
        for (int i = 0; i < 8; i++) {
            Trick trick = new Trick();
            for (int j = 0; j < 4; j++) {
                Player player = players.get(currentPlayerIndex);
                Card card = player.playCard();
                trick.addCard(card);
                currentPlayerIndex = (currentPlayerIndex + 1) % 4;
            }
            tricks.add(trick);
            // Determine winner of the trick and update scores
            // Implement logic to handle trick-taking and scoring
        }
        // Determine the winner of the game based on scores
    }
}
