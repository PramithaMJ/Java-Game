package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;
  //  private BufferedReader reader;
    private JFrame handFrame;
    private JPanel handPanel;


    public Player(String name){
        this.name = name;
        hand = new ArrayList<>();
        //reader = new BufferedReader(new InputStreamReader(System.in));

        handFrame = new JFrame(name + "'s Hand");
        handFrame.setSize(400, 200);
        handFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        handPanel = new JPanel();
        handFrame.add(handPanel);
        handPanel.setLayout(new FlowLayout());
    }

    public void addToDeck(Card card){
        hand.add(card);
        updateHandPanel();
    }

    public void removeFromDeck(Card card){
        hand.remove(card);
        updateHandPanel();
    }

    public boolean hasCard(Card card){
        return hand.contains(card);
    }

    public String getName(){
        return name;
    }

    public Card playCard() {
        /*System.out.println(name + ", Rank:");
        Rank rank = null;
        Suit suit = null;
        try {
            String rankInput = reader.readLine().toUpperCase();
            rank = Rank.valueOf(rankInput);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Invalid input. Please enter a valid rank.");
            return playCard();
        }

        System.out.println("Suit");
        try {
            String suitInput = reader.readLine().toUpperCase();
            suit = Suit.valueOf(suitInput);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Invalid input. Please enter a valid suit.");
            return playCard();
        }

        Card selectedCard = new Card(rank, suit);
        System.out.println(selectedCard);
        if (hand.contains(selectedCard)) {
            printHand();
            System.out.println("You don't have that card. Please select a card from your hand.");
            return playCard();
        }

        hand.remove(selectedCard);
        return selectedCard;*/
        return null;
    }

    public List<Card> getHand() {
        return hand;
    }
    public void displayHand() {
        handFrame.setVisible(true);
    }

    private void updateHandPanel() {
        handPanel.removeAll();
        for (Card card : hand) {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(card.getImagePath()));
            Image image = imageIcon.getImage();
            Image newImage = image.getScaledInstance(80, 120, Image.SCALE_SMOOTH);
            ImageIcon newImageIcon = new ImageIcon(newImage);
            JButton button = new JButton(newImageIcon);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle card selection
                    System.out.println("Selected card: " + card);
                }
            });
            handPanel.add(button);
        }
        handFrame.revalidate();
        handFrame.repaint();
    }

    public void printHand() {
        System.out.println(name+" Your hand:");
        for (Card card : hand) {
            System.out.println(card);
        }
    }
}
