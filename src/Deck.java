import acm.util.RandomGenerator;

import java.util.Random;

public class Deck {

    private Card[] cards;
    private int top;

    public Deck(){
        //initialize the array
        cards = new Card[52];

        // use enhanced for! to instantiate all the cards
        int pos = 0;
        for(Card.Suit suit : Card.Suit.values()){
            for (Card.Face face : Card.Face.values()){
                Card card = new Card(face, suit);
                cards[pos++] = card;
            }
        }

        // call shuffle

        shuffle();

    }

    public void shuffle(){
        for (int i = 0; i < cards.length; i++) {
            int random = RandomGenerator.getInstance().nextInt(0, cards.length-1);

            Card store = cards[i];
            cards[i] = cards[random];
            cards[random] = store;
        }
        top = 0;
    }

    public Card deal(){

        if (top == cards.length-1){
            shuffle();
        }
        return cards[top++];
    }
}
