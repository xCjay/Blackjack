import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.Color;

public class GCard extends GCompound {

    private Card card;
    private GRect back;


    public GCard(Card card){
        this.card = card;

        //make a String for the location of the card picture

        String imageFileName = "cardgifs/" + card.getSuit().toString().substring(0,1).toLowerCase() + (card.getFace().ordinal() + 2) + ".gif";
        //instantiate a GImage using that String

        GImage cardImage = new GImage(imageFileName);

        //add the gImage to the compound
        add(cardImage, 1, 1);

        //make a GRect for the border of the card
        GRect border = new GRect(109, 152);

        //add the border to the compound
        add(border);

        //make a GRect for the back of the card
        back = new GRect(107, 150);
        back.setFillColor(Color.red);
        back.setFilled(true);

        // add the back to the compound
        add(back,1 ,1);
        //make the visibility of the card back depend on faceUp
        back.setVisible(!card.isFaceUp());
        //scale down the card (too big)
        this.scale(.75);
    }

    public void setFaceUp(){
        card.setFaceUp();
    }

    public void flip(){
        card.flip();
        this.back.setVisible(!this.back.isVisible());
    }

    public boolean getFaceUp(){
        return card.isFaceUp();
    }

    public int getValue(){
        return card.getValue();
    }
}
