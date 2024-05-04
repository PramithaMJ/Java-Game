package org.pmj.OmiGame;

import java.util.ArrayList;
import java.util.List;

class Trick {
    private List<Card> cards;

    public Trick() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

}