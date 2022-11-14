import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

/*
CLASS:      Hand
AUTHOR:     Eddie Butkaliuk
REMARKS:    A class representing a poker hand. Implements Handable
*/
public class Hand implements TestableHand
{
    private ArrayList<Card> hand = new ArrayList<>();
    int currHandSize = 0;

    //lower category number is better
    //1:Straight flush
    //2:Four of a kind
    //3:Full house
    //4:Flush
    //5:Straight
    //6:Three of a kind
    //7:Two pairs
    //8:Pair
    //9:Nothing
    //100:Unknown
    private int handCategoryNumber = 100;
    //Holds the score of the hand
    //-1 if no score
    //only meaningful for comparisons when the 2 handCategoryNumbers are the same
    private int handScore = -1;
    //Holds the message of the score to display at the end of the game
    private String scoreMsg = "";

    //holds the indices of cards that are important and should not be discarded by the cpu
    private ArrayList<Integer> importantCards = new ArrayList<>();
    //constructor
    public Hand()
    {

    }

    //Implements methods
    //returns null if no card i
    public Cardable getCard(int i)
    {
        if(i <= HAND_SIZE)
            return hand.get(i);
        else
            return null;
    }
    public void draw(Deckable deck, boolean faceUp)
    {
        //remove all null cards
        hand.removeIf(Objects::isNull);

        //draw cards from deck
        for(int i = currHandSize; i < HAND_SIZE; i ++)
            hand.add((Card)deck.drawACard(faceUp));

        currHandSize = HAND_SIZE;
    }
    public void showAllCards()
    {
        for(Card c : hand)
            c.setFaceUp(true);
    }
    public LinkedList<Cardable> discard()
    {
        LinkedList<Cardable> discarded = new LinkedList<>();

        for(int i = 0; i < HAND_SIZE; i++) //go through all cards in hand
        {
            Card c = hand.get(i);
            if (c.getSelected())         //check if current card is selected
            {
                discarded.add(c);       //add it to discarded list
                c.resetSelected();      //make sure c is deselected before it is removed
                setCardToNull(i);              //setting it to null makes it invisible in GUI
                currHandSize--;         //update hand size
            }
        }

        return discarded;
    }
    public LinkedList<Cardable> returnCards()
    {
        LinkedList<Cardable> discarded = new LinkedList<>();

        for(int i = 0; i < HAND_SIZE; i++) //go through all cards in hand
        {
            hand.get(i).resetSelected();      //make sure card is deselected before it is removed
            discarded.add(hand.get(i));       //add it to discarded list
            setCardToNull(i);              //setting it to null makes it invisible in GUI
        }

        currHandSize = 0;
        return discarded;
    }

    public String evaluateHand()
    {
        setHandCategoryNumber();
        StringBuilder returnMessage = new StringBuilder();

        if (handCategoryNumber == 1)
            returnMessage.append("Straight flush");
        else if (handCategoryNumber == 2)
            returnMessage.append("Four of a kind");
        else if (handCategoryNumber == 3)
            returnMessage.append("Full house");
        else if (handCategoryNumber == 4)
            returnMessage.append("Flush");
        else if (handCategoryNumber == 5)
            returnMessage.append("Straight");
        else if (handCategoryNumber == 6)
            returnMessage.append("Three of a kind");
        else if (handCategoryNumber == 7)
            returnMessage.append("Two Pairs");
        else if (handCategoryNumber == 8)
            returnMessage.append("One Pair");
        else
            returnMessage.append("High card");

        returnMessage.append(", ").append(getScoreMsg());
        return  returnMessage.toString();
    }
    //returns -1 if this hand is weaker
    //returns 0 if both hands are equal
    //returns 1 if this hand is stronger
    public int compareTo(Handable hand2)
    {
        //evaluate the 2 hands
        evaluateHand();
        hand2.evaluateHand();

        if(getHandCategoryNumber() > ((Hand)hand2).getHandCategoryNumber())
            return -1;
        else if(getHandCategoryNumber() < ((Hand) hand2).getHandCategoryNumber())
            return 1;
        else//both hand are same
            return Integer.compare(getHandScore(), ((Hand) hand2).getHandScore());
    }

    //Creates a new hand using the cards passed
    //Assumes the right amount of cards are passed
    public void addCards(Cardable[] cards)
    {
        hand = new ArrayList<>();

        for(int i = 0; i < HAND_SIZE; i++)
        {
            hand.add((Card)cards[i]);
        }
    }

    //sets the category number of the hand to the highest possible category
    //sets the score of the hand
    private void setHandCategoryNumber()
    {
        int categoryScore;

        if((categoryScore = hasStraightFlush(false)) != 0 || (categoryScore = hasStraightFlush(true)) != 0) //hand has straightFlush
            handCategoryNumber = 1;
        else if((categoryScore = hasFourOfaKind()) != 0)
            handCategoryNumber = 2;
        else if((categoryScore = hasFullHouse()) != 0)
            handCategoryNumber = 3;
        else if((categoryScore = hasFlush()) != 0)
            handCategoryNumber = 4;
        else if((categoryScore = hasStraight(false)) != 0 || (categoryScore = hasStraight(true)) != 0)
        //looked for high straight first because its more points
            handCategoryNumber = 5;
        else if((categoryScore = hasThreeOfaKind()) != 0)
            handCategoryNumber = 6;
        else if((categoryScore = hasTwoPairs()) != 0)
            handCategoryNumber = 7;
        else if((categoryScore = hasOnePair()) != 0)
            handCategoryNumber = 8;
        else
        {
            categoryScore = setNoPairScore();
            handCategoryNumber = 9;
        }
       handScore = categoryScore;
    }

    //sets the score of the cards in hand
    private int setNoPairScore()
    {
        int score  = calculateNoPairScore();

        scoreMsg = getTopCard(false).toString();

        return score;
    }

    //returns the score of the cards in hand
    private int calculateNoPairScore()
    {
        int score = 0;
        StringBuilder scoreStr = new StringBuilder();

        int[] cardValueArray = new int[HAND_SIZE];
        for(int i = 0; i < HAND_SIZE; i++)
            cardValueArray[i] = hand.get(i).getValue();

        //sort from low to high
        Arrays.sort(cardValueArray);

        for(int i = HAND_SIZE - 1; i >= 0; i-- )
            scoreStr.append(cardValueArray[i]);

        score = Integer.parseInt(scoreStr.toString());

        return score;
    }

    //returns the score of the hand if the hand has a pair
    //0 otherwise
    private int hasOnePair()
    {
        int score = 0;

        //go through each card in hand and see if one makes a pair
        for(Card c : hand)
        {
            if(hasSameValue(c.getValue(), 2))
            {//found a pair

                score = c.getValue() * 100; //*100 to make pair weigh most

                //set message
                scoreMsg = "two " + c.thisValueToString() + "s";

                //calculate the score from the rest of the cards
                for(Card c2 : hand)
                    if(c2.getValue() != c.getValue())       //ignore cards that from a pair
                        score += c2.getValue();

                //set important cards
                for(int i = 0; i < HAND_SIZE; i ++)
                {
                    if(hand.get(i).getValue() == c.getValue())
                        importantCards.add(i);
                }

                return score;
            }
        }

        //if this is reached, pair does not exist.
        return 0;
    }

    //if hand has 2 pairs, return the score
    //otherwise return 0
    private int hasTwoPairs()
    {
        int score = 0;
        //find first pair
        for(Card c : hand)
        {
            //look through the hand, and see if any value is seen twice
            if(hasSameValue(c.getValue(), 2))
            {
                score = c.getValue() * 100;     //*100 to make first pair have most weight

                //find another pair
                for(Card c2 : hand)
                {
                    if(c2.getValue() != c.getValue() && hasSameValue(c2.getValue(), 2))     //skip cards with same value as first pair
                    {//found second pair
                        score += c.getValue() * 50;  //*50 to make second pair have more weight than last card

                        scoreMsg = "two " + c.thisValueToString() + "s two " + c2.thisValueToString() + "s";

                        //find the value of the remaining card
                        for(Card c3 : hand)
                            if(c3.getValue() != c2.getValue() && c3.getValue() != c.getValue())
                                score+= c3.getValue();

                        //set important cards
                        for(int i = 0; i < HAND_SIZE; i ++)
                        {
                            if(hand.get(i).getValue() == c.getValue() || hand.get(i).getValue() == c2.getValue())
                                importantCards.add(i);
                        }

                        return score;

                    }

                }
            }
        }

        //if here, two pairs do not exist in given hand
        return  0;
    }

    //if 3 of a kind exists, return its score
    //return 0 otherwise
    private int hasThreeOfaKind()
    {
        int score = 0;

        //go through every card in hand
        for(Card c : hand)
        {
            //check if 3 cards with same value as current exist
            if(hasSameValue(c.getValue(), 3))
            {//3 cards of current value exist
                score = c.getValue()*100;   //*100 to make value of 3 of a kind cards have more weight

                scoreMsg = "three " + c.thisValueToString() + "s";

                //find the value of the other 2 cards
                for(Card c2 : hand)
                {
                    if(c2.getValue() != c.getValue())   //ignore cards from 3 of a kind
                        score += c2.getValue();
                }

                //set important cards
                for(int i = 0; i < HAND_SIZE; i ++)
                {
                    if(hand.get(i).getValue() == c.getValue())
                        importantCards.add(i);
                }

                return score;
            }
        }

        return 0;
    }
    //if hand has straight returns the score
    //0 otherwise
    //boolean value passed determines if we are looking for a low straight or a high straight
    private int hasStraight(boolean aceLow)
    {
        Card topCard = getTopCard(aceLow);

        //if the top card has less value than the size of the hand, straight is not possible
        //if top card is an ace, low straight not possible when ace is low
        if(topCard.getValue() < HAND_SIZE || (topCard.getValue() == 14 && aceLow))
            return 0;
        else if(topCard.getValue() < HAND_SIZE  && !aceLow)
            return 0;


        //if here, straight still possible
        int currValue = topCard.getValue()-1;   //current value to look for in the hand
        boolean straightPossible = true;
        int score = topCard.getValue();

        //go through all cards in the hand for as long as a straight is still possible
        for(int i = 1; i < HAND_SIZE && straightPossible; i++)
        {

            //if ace is considered low and currently looking for card 1, set currentValue to 14 because that's the real value of ace
            if(aceLow && currValue == 1)
                currValue = 14;
            //current value does not need to be changed back because when ace is low it is the last card needed. currValue won't be used anymore

            if(!contains(currValue))     //if the hand does not have a card with current value of given suit
                straightPossible = false;                      //straight is not possible
            else
            {
                //if here the needed card was found.
                if (aceLow && currValue == 14)
                    score += 1;
                else
                    score += currValue;
            }



            currValue--;
        }


        if(straightPossible)               //if straightPossible after loop, all needed cards were found
        {
             //create message
             int topValue = getTopCard(aceLow).getValue();
             StringBuilder msg = new StringBuilder();

             //start at i = 0 and save the value of TopCard - i
             for(int i = 0; i < HAND_SIZE; i ++)
             {
                 msg.append(Card.cardValueToString(topValue-i)).append(" ");
             }

             //save the message
             scoreMsg = msg.toString();

            setAllCardsImportant();

             return score;
        }
        else
            return 0;
    }

    //if hand has flush, returns its score
    //otherwise return 0
    private int hasFlush()
    {

        //save the suit of the first card
        Cardable.Suit suit = hand.get(0).getSuit();

        //go through the hand and check if every card has the same suit as first.
        for(int i = 0; i < HAND_SIZE; i++)
        {
            if(hand.get(i).getSuit() != suit)
                return 0;
        }

        //if here, flush was found
        scoreMsg = "highest card " + getTopCard(false);
        setAllCardsImportant();
        return calculateNoPairScore();
    }


    //If hand has full house, return its score
    //Otherwise return 0
    private int hasFullHouse()
    {
        //go through all the values
        for(int i = 0; i < HAND_SIZE; i++)
        {
            if(hasSameValue(hand.get(i).getValue(), 3))
            {//3 of a kind found
                int score = hand.get(i).getValue()*100;      //multiply by 100 so that the kind of the three-of-a-kind cards has more weight on the score than the pair


                //find a pair
                for(int j = 0; j < HAND_SIZE; j++)
                {
                    if(i != j)  //pair cannot be same as three of a kind
                    {
                        if(hasSameValue(hand.get(j).getValue(), 2))
                        {//pair found
                            score += hand.get(j).getValue();

                            scoreMsg = "three " + hand.get(i).thisValueToString() + " two " + hand.get(j).thisValueToString();

                            //all cards are important in a full house
                            setAllCardsImportant();

                            return score;
                        }
                    }
                }

            }
        }

        //if this is reached, no full house found
        return  0;
    }

    //If hand has four of a kind, return its score
    //Otherwise return 0
    private int hasFourOfaKind()
    {
        //go through all the cards
        for(int i = 0; i < HAND_SIZE; i++)
        {
            if(hasSameValue(hand.get(i).getValue(), 4))
            {//four of a kind found
                int score = hand.get(i).getValue()*100;      //multiply by 100 so that the kind of the four-of-a-kind cards has more weight on the score than the kicker

                //go through the hand again to
                //find which card is the kicker and which cards are important
                for(int j = 0; j < HAND_SIZE; j++)
                {
                    if(hand.get(j).getValue() != hand.get(i).getValue())
                    {//kicker
                        score += hand.get(j).getValue();

                        scoreMsg = "four " + hand.get(i).thisValueToString();
                    }
                    else
                    {//important
                        importantCards.add(j);
                    }
                }

                return score;
            }
        }

        //four of a kind not found
        return 0;
    }

    //returns the value of straight flush if one exists in hand
    //0 if it does not
    private int hasStraightFlush(boolean aceLow)
    {
        Card topCard = getTopCard(aceLow);

        //if the top card has less value than the size of the hand, straight flush is not possible
        //if top card is an ace, straight flush not possible
        if(topCard.getValue() < HAND_SIZE || (topCard.getValue() == 14 && aceLow))
            return 0;
        else if(topCard.getValue() < HAND_SIZE  && !aceLow)
            return 0;

        //if here, flush still possible
        int currValue = topCard.getValue()-1;   //current value to look for in the hand
        boolean flushPossible = true;
        int score = topCard.getValue();

        //go through all cards in the hand for as long as a flush is still possible
        for(int i = 1; i < HAND_SIZE && flushPossible; i++)
        {

            //if ace is considered low and currently looking for card 1, set currentValue to 14 because that's the real value of ace
            if(aceLow && currValue == 1)
                currValue = 14;
            //current value does not need to be changed back because when ace is low it is the last card needed. currValue won't be used anymore

            if(!contains(currValue, topCard.getSuit()))     //if the hand does not have a card with current value of given suit
                flushPossible = false;                      //flush is not possible
            else
                //if here the needed card was found.
                if (aceLow && currValue == 14)
                    score += 1;
                else
                    score += currValue;

            currValue--;
        }


        if(flushPossible)               //if flushPossible after loop, all needed cards were found
        {
            scoreMsg = "top card " + topCard;

            setAllCardsImportant();

            return score;
        }
        else
            return 0;
    }


    //returns the highest value card
    //ace can be considered low or high
    private Card getTopCard(boolean aceLow)
    {
        Card topCard = hand.get(0);
        int topCardValue = topCard.getValue();

        int currValue;
        for(Card curr : hand)
        {
            currValue = curr.getValue();

            if(aceLow && topCardValue == Card.MAX_VALUE)
                    topCardValue = 1;

            if(aceLow && currValue == Card.MAX_VALUE)
                currValue = 1;

            if(currValue > topCardValue)
            {
                topCard = curr;
                topCardValue = curr.getValue();
            }

        }

        return topCard;
    }

    //returns true if hand has at exactly amount cards of passed value
    private boolean hasSameValue(int value, int amount)
    {
        int count = 0;

        //go through the hand
        for(Card c : hand)
            if(c.getValue() == value)       //if current card has needed value
                count++;

        return (count == amount);
    }

    //returns true if this hand contains card with passed value and suit
    //false otherwise
    private boolean contains(int value, Cardable.Suit suit)
    {

        for(Card c : hand)
            if (c.getValue() == value && c.getSuit() == suit)
                return true;


        return false;
    }

    //returns true if this hand contains card with passed value and any suit
    //false otherwise
    private boolean contains(int value)
    {
        for(Card c : hand)
            if (c.getValue() == value)
                return true;

        return false;
    }

    public int getHandCategoryNumber() {return handCategoryNumber;}
    public int getHandScore(){return handScore;}

    public void print()
    {
        for (Card card : hand)
            System.out.print(card.toString() + " ");

        System.out.println();
    }

    //set cards in hand at passed index to null
    private void setCardToNull(int index)
    {
        hand.set(index, null);
    }

    public void newGame()
    {
        handCategoryNumber = 100;
        handScore = 0;
        scoreMsg = "";
        importantCards = new ArrayList<>();
    }
    public String getScoreMsg()
    {
        return scoreMsg;
    }

    public boolean isImportant(int x)
    {
        if(importantCards.contains(x))
            return true;

        return false;
    }

    //sets that all cards should not be removed by the CPU
    private void setAllCardsImportant()
    {
        //set all cards to be important
        for(int i = 0; i < HAND_SIZE; i++)
            importantCards.add(i);
    }

}//Hand
