/*
CLASS:      Deck
AUTHOR:     Eddie Butkaliuk
REMARKS:    A class representing a standard poker deck
            Un-shuffled when created
            Implements Deckable
*/

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Deck implements Deckable
{
    private ArrayList<Card> deck;

    public Deck()
    {
        deck = new ArrayList<>();
        createDeck(deck);
    }

    //creates a standard deck of cards and stores it in passed ArrayList
    private void createDeck(ArrayList<Card> deck)
    {
        //empty passed deck
        deck.clear();

        Cardable.Suit suit = Cardable.Suit.HEART;
        int value = Card.MIN_VALUE;
        for(int i = 0; i < NUM_CARDS; i++)
        {
            Card newCard = new Card(value, suit);
            deck.add(newCard);

            value++;                            //increment value
            value = value%(Card.MAX_VALUE+1);   //reset if value goes past MAX
            if(value == 0)
            {//value = means it was just reset. Also means all cards in given suit are made
                value = Card.MIN_VALUE;

                //set the next suit (made in a way that loops through all suits no matter where it started)
                if(suit == Cardable.Suit.HEART)
                    suit = Cardable.Suit.DIAMOND;
                else if(suit == Cardable.Suit.DIAMOND)
                    suit = Cardable.Suit.SPADE;
                else if(suit == Cardable.Suit.SPADE)
                    suit = Cardable.Suit.CLUB;
                else if(suit == Cardable.Suit.CLUB)
                    suit = Cardable.Suit.HEART;

            }
        }
    }//create deck

    //Implemented methods
    @Override
    //shuffles the cards in deck randomly.
    public void shuffle()
    {
        Collections.shuffle(deck);
    }
    @Override
    //returns the cards in passed list back to the end of the deck
    public void returnToDeck(LinkedList<Cardable> discarded)
    {
        Cardable curr;
        //keep looping while the discarded is not empty
        while (discarded.size() > 0)
        {
            curr = discarded.remove();
            deck.add((Card) curr);
        }
    }
    @Override
    //draws the top card from the deck
    //null if deck is empty
    public Cardable drawACard(boolean faceUp)
    {
        Cardable card = null;
        if(deck.size() > 0)
        {
            card = deck.remove(0);
            card.setFaceUp(faceUp);
        }
        return card;
    }

    //prints the deck in order of deck
    public void print()
    {
        System.out.println("Deck size: " + deck.size());
        for (Card card : deck)
            System.out.print(card.toString() + " ");

        System.out.println();
    }

}//Deck
