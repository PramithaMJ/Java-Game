package org.pmj.OmiGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public Card chooseTrump() {
        // Implement logic for choosing trump suit
        // For simplicity, let's assume the player chooses a random card as trump
        Random random = new Random();
        int index = random.nextInt(hand.size());
        return hand.get(index);
    }

    public Card playCard() {
        // Implement logic for playing a card
        // For simplicity, let's assume the player plays the first card in hand
        return hand.remove(0);
    }
}
