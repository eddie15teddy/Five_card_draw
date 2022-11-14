/*
CLASS:      Card
AUTHOR:     Eddie Butkaliuk
REMARKS:    A class representing a poker card. Implements Cardable
*/

public class Card implements Cardable
{
    //Static variables as info about card implementation
    public static final int MAX_SUIT = 4;
    public static final int MIN_VALUE = 2;
    public static final int MAX_VALUE = 14;

    private boolean selected;
    private boolean faceUp;
    private final Suit suit;
    private final int value; //11: Jack, 12: Queen, 13: King, 14:Ace

    //Constructor
    public Card(int value, Suit suit)
    {
        this.suit = suit;
        this.value = value;
        selected = false;       //default is unselected
        faceUp = false;         //default is face down
    }

    //Returns a string representation of the card
    public String toString()
    {
        StringBuilder output = new StringBuilder();

        output.append(thisValueToString());

        if(suit == Suit.CLUB)
            output.append("♣");
        else if(suit == Suit.DIAMOND)
            output.append("♦");
        else if(suit == Suit.HEART)
            output.append("♥");
        else if(suit == Suit.SPADE)
            output.append("♠");

        return output.toString();

    }//toString

    public String thisValueToString()
    {
        String output;

        if (value == 11)
            output = "J";
        else if(value == 12)
            output = "Q";
        else if(value == 13)
            output = "K";
        else if(value == 14)
            output = "A";
        else
            output = "" + value;

        return output;
    }

    //Implemented methods
    public void switchSelectedState() { selected = !selected; }
    public void resetSelected() { selected = false;}
    public void setFaceUp(boolean faceUp) {this.faceUp = faceUp;}

    //Implemented getters
    public boolean getSelected() { return  selected; }
    public boolean getFaceUp() { return faceUp; }
    public Suit getSuit() { return suit; }

    //Other getters
    public int getValue() { return  value; }

    //Static methods
    //converts the passed int to a card value
    public static String cardValueToString(int value)
    {
        String output;

        if (value == 11)
            output = "J";
        else if(value == 12)
            output = "Q";
        else if(value == 13)
            output = "K";
        else if(value == 14)
            output = "A";
        else
            output = "" + value;

        return output;
    }
}//Card

