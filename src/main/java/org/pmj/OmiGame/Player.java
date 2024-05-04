package org.pmj.OmiGame;

import java.util.ArrayList;
import java.util.List;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Player {
    private String name;
    private List<Card> hand;
    private BufferedReader reader;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public boolean hasCard(Card card) {
        return hand.contains(card);
    }

    public Card playCard() {
        System.out.println(name + ", enter the rank of the card (ACE, KING, QUEEN, etc.):");
        Rank rank = null;
        Suit suit = null;
        try {
            String rankInput = reader.readLine().toUpperCase();
            rank = Rank.valueOf(rankInput);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Invalid input. Please enter a valid rank.");
            return playCard();
        }

        System.out.println("Enter the suit of the card (CLUBS, DIAMONDS, HEARTS, SPADES):");
        try {
            String suitInput = reader.readLine().toUpperCase();
            suit = Suit.valueOf(suitInput);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Invalid input. Please enter a valid suit.");
            return playCard();
        }

        Card selectedCard = new Card(rank, suit);
        if (!hand.contains(selectedCard)) {
            System.out.println("You don't have that card. Please select a card from your hand.");
            return playCard();
        }

        return selectedCard;
    }

    public String getName() {
        return name;
    }
}
