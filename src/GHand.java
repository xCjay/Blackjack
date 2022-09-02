import acm.graphics.GCompound;

public class GHand extends GCompound {

    private Hand hand;
    private GCard[] gCards;



    public GHand(Hand hand){
        this.hand = hand;

        gCards = new GCard[7];

        for (int i = 0; i < hand.getCount(); i++) {
            Card card = hand.getCard(i);
            GCard gcard = new GCard(card);

            add(gcard, i * gcard.getWidth() + gcard.getWidth()/4, 0);
            gCards[i] = gcard;
        }
    }

    public int getTotal(){
        return hand.getTotal();
    }

    public void flipCard(int index){
        gCards[index].flip();
    }

    public void hit(){
        hand.hit();

        Card temp = hand.getCard(hand.getCount()-1);
        GCard gcard = new GCard(temp);
        gCards[hand.getCount()-1] = gcard;

        add(gcard, (hand.getCount()-1) * (gcard.getWidth()*1.25), 0);
    }
}
