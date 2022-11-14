/*
CLASS:      Player
AUTHOR:     Eddie Butkaliuk
REMARKS:    A class representing the player
            Holds fields all types of players use
*/

public  class Player
{
    private Hand hand;
    private int numWins;
    private String name;

    public Player(Hand hand, String name)
    {
        this.hand = hand;
        numWins = 0;
        this.name = name;
    }

    public Hand getHand() {return hand;}
    public void wins() { numWins++;}
    public int getNumWins() {return  numWins;}
    public String getName() {return name;}
    public void resetPoints()
    {
        hand.newGame();
    }

    public void selectCards(){}
}
