/*
CLASS:      SmartCPU
AUTHOR:     Eddie Butkaliuk
REMARKS:    A class representing the CPU that makes decent choices.
            It will not make a choice that destroys a good deck
*/

import java.util.Random;

public class CPUSmart extends Player
{
    public CPUSmart(Hand hand)
    {
        super(hand, "Smart CPU");
    }

    //selects which card to discard in a way that will not make the deck worse
    //makes use of the importantCards array in the hand object
    //selects only unimportant cards
    public void selectCards()
    {
        getHand().evaluateHand();
        int categoryNumber = getHand().getHandCategoryNumber();
        /*
        System.out.print("CPU hand: ");
        getHand().print();
        System.out.println("CPU category number: " + getHand().getHandCategoryNumber());
        System.out.println();
        */
         for(int i = 0; i < Hand.HAND_SIZE; i ++)
         {
             //select card only if its unimportant
             if(!getHand().isImportant(i))
             {
                 //If high card, remove unimportant cards that are less than king
                 //otherwise, remove unimportant cards that are less than 9
                 if((categoryNumber == 9 && ((Card)getHand().getCard(i)).getValue() < 13)
                 ||(categoryNumber != 9 &&  ((Card)getHand().getCard(i)).getValue() < 9))
                 getHand().getCard(i).switchSelectedState();
             }
         }
    }
}
