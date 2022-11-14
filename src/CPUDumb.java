/*
CLASS:      DumbCPU
AUTHOR:     Eddie Butkaliuk
REMARKS:    A class representing the CPU that makes random choices
            Child of player
*/

import java.util.Random;

public class CPUDumb extends Player
{
    public CPUDumb(Hand hand)
    {
        super(hand, "Dumb CPU");
    }

    public void selectCards()
    {
        Random r = new Random();
        int numCardsToSelect = r.nextInt(Handable.HAND_SIZE+1);
        int currentCardSelected;

        //select the cards
        //same card can be selected and then deselected, but that's okay
        for(int i = 0; i < numCardsToSelect; i ++)
        {
            currentCardSelected = r.nextInt(Handable.HAND_SIZE);
            getHand().getCard(currentCardSelected).switchSelectedState();
        }


    }
}
