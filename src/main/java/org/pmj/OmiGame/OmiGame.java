package org.pmj.OmiGame;


import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class OmiGame {
    private List<Card> cardsPlayed;
    private List<Player> players;

    public OmiGame() {
        cardsPlayed = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void playCard(Player player, Card card) {
        cardsPlayed.add(card);
        player.removeCard(card);
    }

    public Player determineWinner() {
        if (cardsPlayed.isEmpty()) return null;

        Card highestCard = cardsPlayed.get(0);
        for (int i = 1; i < cardsPlayed.size(); i++) {
            Card currentCard = cardsPlayed.get(i);
            if (currentCard.getRank().compareTo(highestCard.getRank()) > 0 ||
                    (currentCard.getRank() == highestCard.getRank() &&
                            currentCard.getSuit().compareTo(highestCard.getSuit()) > 0)) {
                highestCard = currentCard;
            }
        }

        for (Player player : players) {
            if (player.hasCard(highestCard)) {
                return player;
            }
        }

        return null; // No winner found
    }
}
